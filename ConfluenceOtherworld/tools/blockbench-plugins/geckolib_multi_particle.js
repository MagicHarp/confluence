/**
 * GeckoLib Multi-Particle Keyframes
 * ---------------------------------------------------------------------------
 * Blockbench plugin for the "GeckoLib Animated Model" format.
 *
 * 让 GeckoLib 动画编辑器可以像基岩版实体动画那样，在“一个关键帧”上放置多个粒子效果
 * （以及多个音效），而不是一个关键帧只能有一个效果。
 *
 * 原理 / How it works
 *  - Blockbench 核心本身支持 effect 通道（particle / sound）的关键帧拥有多个 data point
 *    （`EffectAnimator.channels.particle.max_data_points = 1000`，
 *     `Keyframe#compileBedrockKeyframe()` 在 data_points > 1 时会返回数组）。
 *  - 但 GeckoLib 官方插件在每一帧都会隐藏关键帧面板里的 “+” (add data point) 按钮，
 *    于是编辑器里无法为一个关键帧添加第二个粒子效果。本插件只在“效果通道”的关键帧上
 *    恢复该按钮，不动骨骼（rotation/position/scale）关键帧的行为。
 *  - GeckoLib 运行时（4.x 与 5.x 的 `particle_effects` / `sound_effects` 都是
 *    `Map<时间, 效果对象>`）只接受“每个时间点一个效果对象”，因此导出时默认把同一关键帧上
 *    的额外效果拆成“亚刻（sub-tick）时间偏移”的独立条目 —— 这些条目间隔只有 1e-5 秒
 *    （0.0002 游戏刻），GeckoLib 的 `adjustedTick >= keyframeTick` 判定会让它们在同一个
 *    游戏刻/同一渲染帧内全部触发，视觉上等于同时发射。
 *  - 导入时再把这种亚刻偏移的条目合并回一个关键帧的多个 data point，保证往返编辑不丢信息。
 *
 * @author  DSH
 * @version 1.0.0
 */

(function () {
	'use strict';

	const PLUGIN_ID = 'geckolib_multi_particle';
	const GECKOLIB_FORMAT_ID = 'geckolib_model';
	/** 采用“一个关键帧可以有多个效果”的动画通道 */
	const EFFECT_CHANNELS = ['particle', 'sound'];
	/** 通道 -> 动画 JSON 中的字段名 */
	const EFFECT_JSON_KEYS = { particle: 'particle_effects', sound: 'sound_effects' };
	const CATEGORY_ID = PLUGIN_ID;

	/** 亚刻偏移允许的范围（秒） */
	const MIN_OFFSET = 1e-9;
	const MAX_OFFSET = 0.05;
	/** 合并阈值允许的范围（秒） */
	const MIN_EPSILON = 1e-9;
	const MAX_EPSILON = 0.05;

	const DEFAULT_OFFSET = 0.00001;   // 1e-5 s = 0.0002 游戏刻
	const DEFAULT_EPSILON = 0.0001;   // 1e-4 s = 0.002 游戏刻

	const state = {
		/** 被 patch 的动画编解码器（GeckoLib 复用 Bedrock 的 animation codec） */
		codec: null,
		originalCompileAnimation: null,
		originalLoadFile: null,
		patchedCompile: null,
		patchedLoad: null,
		observer: null,
		observerTarget: null,
		toolbarItem: null,
		menuItem: null,
		/** 已注册的事件监听：[event, handler]，卸载时逐个移除 */
		listeners: [],
		warnedArrayMode: false,
	};

	/** Setting 实例，键为短名 */
	const config = {};

	/* ====================================================================== *
	 * 小工具
	 * ====================================================================== */

	function isGeckoLibProject() {
		try {
			return typeof Format !== 'undefined' && !!Format && Format.id === GECKOLIB_FORMAT_ID;
		} catch (error) {
			return false;
		}
	}

	function setting(name, fallback) {
		const item = config[name];
		return item && item.value !== undefined ? item.value : fallback;
	}

	/** 插件是否应当生效（设置已启用 + 当前工程是 GeckoLib 格式） */
	function isActive() {
		return !!setting('enabled', true) && isGeckoLibProject();
	}

	function clampNumber(value, min, max, fallback) {
		const number = typeof value === 'number' ? value : parseFloat(value);
		if (!isFinite(number) || number <= 0) return fallback;
		return Math.min(max, Math.max(min, number));
	}

	function subTickOffset() {
		return clampNumber(setting('sub_tick_offset', DEFAULT_OFFSET), MIN_OFFSET, MAX_OFFSET, DEFAULT_OFFSET);
	}

	function mergeEpsilon() {
		return clampNumber(setting('merge_epsilon', DEFAULT_EPSILON), MIN_EPSILON, MAX_EPSILON, DEFAULT_EPSILON);
	}

	/** 'array' = 基岩版风格数组（默认，需 mixin 支持）；'split' = 亚刻偏移（任何 GeckoLib 都能读） */
	function exportMode() {
		return setting('export_mode', 'array') === 'split' ? 'split' : 'array';
	}

	/** 关键帧是否属于“效果”通道（粒子 / 音效），而不是骨骼变换通道 */
	function isEffectKeyframe(keyframe) {
		if (!keyframe || EFFECT_CHANNELS.indexOf(keyframe.channel) === -1) return false;
		const channels = keyframe.animator && keyframe.animator.channels;
		const channel = channels && channels[keyframe.channel];
		if (channel && channel.transform) return false;
		return true;
	}

	function canAddDataPoint(keyframe) {
		if (!isEffectKeyframe(keyframe)) return false;
		const channels = keyframe.animator && keyframe.animator.channels;
		const channel = channels && channels[keyframe.channel];
		const max = channel && channel.max_data_points ? channel.max_data_points : 1;
		return !keyframe.data_points || keyframe.data_points.length < max;
	}

	function selectedKeyframes() {
		try {
			return (typeof Timeline !== 'undefined' && Timeline.selected) ? Timeline.selected : [];
		} catch (error) {
			return [];
		}
	}

	function selectedEffectKeyframes() {
		return selectedKeyframes().filter(isEffectKeyframe);
	}

	function notify(message) {
		try {
			if (typeof Blockbench !== 'undefined' && Blockbench.showQuickMessage) {
				Blockbench.showQuickMessage(message);
				return;
			}
		} catch (error) { /* ignore */ }
		console.log('[GeckoLib Multi-Particle] ' + message);
	}

	/* ====================================================================== *
	 * 时间码（timecode）处理
	 * ====================================================================== */

	/**
	 * 把秒数格式化成 Bedrock / GeckoLib 的时间码字符串。
	 * 始终保留小数点（Blockbench 的 `getTimecodeString()` 也是这个风格），并且不会产生
	 * 浮点噪声（0.1 + 1e-5 -> "0.10001"）。
	 */
	function formatTimecode(time) {
		let text = time.toFixed(9).replace(/0+$/, '').replace(/\.$/, '');
		if (text === '' || text === '-0') text = '0';
		if (text.indexOf('.') === -1) text += '.0';
		return text;
	}

	/** 在时间码上加一个偏移；偏移为 0 时原样返回，避免改写原本的写法 */
	function timecodeWithOffset(timecode, delta) {
		if (!delta) return timecode;
		const base = parseFloat(timecode);
		if (!isFinite(base)) return timecode;
		return formatTimecode(base + delta);
	}

	/* ====================================================================== *
	 * 导出：把“一个关键帧上的多个效果”转换成 GeckoLib 能读的形式
	 * ====================================================================== */

	/**
	 * 处理一个 `xxx_effects` 对象。
	 *
	 * 核心编译出来的结构是：
	 *   { "0.5": { effect, locator, ... } }                       只有一个效果
	 *   { "0.5": [ { effect, ... }, { effect, ... } ] }            同一关键帧有多个效果
	 *
	 * - split 模式：第二个及以后的效果写成 "0.50001"、"0.50002" … 的独立条目（运行时兼容）
	 * - array 模式：保持数组（基岩版风格，但官方 GeckoLib 运行时会抛 "Not a JSON Object"）
	 */
	function transformEffectsObject(effects, mode, offset) {
		if (!effects || typeof effects !== 'object') return effects;

		const result = {};
		for (const timecode in effects) {
			if (!Object.prototype.hasOwnProperty.call(effects, timecode)) continue;
			const value = effects[timecode];

			if (Array.isArray(value) && value.length > 1) {
				const points = value.filter(point => point !== undefined && point !== null && point !== '');
				if (points.length === 0) continue;

				if (mode === 'array') {
					result[timecode] = points.length === 1 ? points[0] : points;
				} else {
					points.forEach((point, index) => {
						result[timecodeWithOffset(timecode, index * offset)] = point;
					});
				}
			} else {
				result[timecode] = value;
			}
		}
		return result;
	}

	/** 处理单个动画的编译结果（`animation_codec.compileAnimation()` 的返回值） */
	function transformCompiledAnimation(animationTag) {
		if (!animationTag || typeof animationTag !== 'object') return animationTag;

		const mode = exportMode();
		const offset = subTickOffset();
		let usedArrayMode = false;

		for (const channel of EFFECT_CHANNELS) {
			const key = EFFECT_JSON_KEYS[channel];
			if (!animationTag[key]) continue;
			animationTag[key] = transformEffectsObject(animationTag[key], mode, offset);
		}

		if (mode === 'array') {
			// array 模式下如果确实输出了数组，提醒一次（官方 GeckoLib 4.x/5.x 不支持）
			for (const channel of EFFECT_CHANNELS) {
				const value = animationTag[EFFECT_JSON_KEYS[channel]];
				if (!value) continue;
				for (const timecode in value) {
					if (Array.isArray(value[timecode])) { usedArrayMode = true; break; }
				}
			}
		}

		if (usedArrayMode && !state.warnedArrayMode && !setting('array_runtime_ready', false)) {
			state.warnedArrayMode = true;
			notify('提示：粒子/音效以数组形式导出 —— 官方 GeckoLib 运行时无法解析（Not a JSON Object），需要 mixin 补丁；'
				+ '若已打好补丁，可在插件设置里关闭本提示。');
		}

		return animationTag;
	}

	/* ====================================================================== *
	 * 导入：把亚刻偏移的效果条目合并回同一个关键帧
	 * ====================================================================== */

	/** 把落在 mergeEpsilon 之内的 `xxx_effects` 条目合并成一个数组条目 */
	function mergeEffectsObject(effects, epsilon) {
		if (!effects || typeof effects !== 'object') return effects;

		const entries = [];
		for (const timecode in effects) {
			if (!Object.prototype.hasOwnProperty.call(effects, timecode)) continue;
			const time = parseFloat(timecode);
			if (!isFinite(time)) return effects; // 出现异常时间码就不动它
			entries.push({ timecode, time, value: effects[timecode] });
		}
		if (entries.length < 2) return effects;

		entries.sort((a, b) => a.time - b.time);

		const clusters = [];
		let current = null;
		for (const entry of entries) {
			if (current && (entry.time - current.lastTime) <= epsilon) {
				current.items.push(entry.value);
				current.lastTime = entry.time;
			} else {
				current = { timecode: entry.timecode, lastTime: entry.time, items: [entry.value] };
				clusters.push(current);
			}
		}

		const result = {};
		for (const cluster of clusters) {
			if (cluster.items.length === 1) {
				result[cluster.timecode] = cluster.items[0];
				continue;
			}
			const points = [];
			cluster.items.forEach(item => {
				if (Array.isArray(item)) {
					item.forEach(point => { if (point) points.push(point); });
				} else if (item) {
					points.push(item);
				}
			});
			result[cluster.timecode] = points.length === 1 ? points[0] : points;
		}
		return result;
	}

	/**
	 * 在解析动画文件之前处理 JSON —— GeckoLib 插件的解析器本身已经支持数组
	 * （`data_points: particles`），所以只要在这里合并好，导入后就是一个关键帧带多个效果。
	 */
	function prepareImportFile(file) {
		if (!file || typeof file !== 'object') return;
		let json = file.json;
		if (!json) {
			if (typeof file.content !== 'string' || !file.content.trim()) return;
			try {
				json = (typeof autoParseJSON === 'function')
					? autoParseJSON(file.content, { file_path: file.path })
					: JSON.parse(file.content);
			} catch (error) {
				return;
			}
		}
		if (!json || typeof json !== 'object') return;

		let changed = false;
		const animations = json.animations;
		if (animations && typeof animations === 'object') {
			const epsilon = mergeEpsilon();
			for (const animationName in animations) {
				const animationTag = animations[animationName];
				if (!animationTag || typeof animationTag !== 'object') continue;
				for (const channel of EFFECT_CHANNELS) {
					const key = EFFECT_JSON_KEYS[channel];
					if (!animationTag[key]) continue;
					const merged = mergeEffectsObject(animationTag[key], epsilon);
					if (merged !== animationTag[key]) {
						animationTag[key] = merged;
						changed = true;
					}
				}
			}
		}

		// 让后续解析使用我们处理过的对象（GeckoLib 的解析器优先读取 file.json）
		if (changed) file.json = json;
	}

	function copyDataPoint(keyframe, source) {
		const point = new KeyframeDataPoint(keyframe);
		const properties = KeyframeDataPoint.properties || {};
		for (const name in properties) {
			if (Object.prototype.hasOwnProperty.call(properties, name) && source[name] !== undefined) {
				point[name] = source[name];
			}
		}
		return point;
	}

	/**
	 * 兜底合并：在“已经解析完成”的动画模型上，把彼此距离小于 mergeEpsilon 的效果关键帧
	 * 合并为一个带多个 data point 的关键帧（用于插件加载顺序导致 JSON 预处理没赶上的情况）。
	 */
	function mergeEffectKeyframesOfAnimation(animation) {
		if (!animation || !animation.animators) return;
		const effects = animation.animators.effects;
		if (!effects) return;
		const epsilon = mergeEpsilon();

		for (const channel of EFFECT_CHANNELS) {
			const keyframes = effects[channel];
			if (!Array.isArray(keyframes) || keyframes.length < 2) continue;

			const sorted = keyframes.slice().sort((a, b) => a.time - b.time);
			let anchor = null;
			for (const keyframe of sorted) {
				if (anchor && (keyframe.time - anchor.time) <= epsilon) {
					(keyframe.data_points || []).forEach(point => {
						anchor.data_points.push(copyDataPoint(anchor, point));
					});
					keyframe.remove();
				} else {
					anchor = keyframe;
				}
			}
		}
	}

	function mergeEffectKeyframesOf(animations) {
		if (!Array.isArray(animations)) return;
		animations.forEach(animation => {
			try {
				mergeEffectKeyframesOfAnimation(animation);
			} catch (error) {
				console.error('[GeckoLib Multi-Particle] merge failed', error);
			}
		});
	}

	/* ====================================================================== *
	 * 编辑器 UI：恢复“+”按钮 + 效果关键帧保持线性插值
	 * ====================================================================== */

	/**
	 * GeckoLib 插件在每一帧都执行 `#keyframe_type_label > div` 的 `hidden = true`，
	 * 也就是把核心的 “add data point” 按钮藏起来。这里只在“选中的关键帧全都是效果关键帧”
	 * 时把它恢复显示；骨骼关键帧（GeckoLib 的 pre/post 按钮）保持原样。
	 *
	 * @returns {boolean|null} 期望的可见性；null 表示不干预
	 */
	function desiredAddButtonVisibility() {
		if (!isActive()) return null;
		const selected = selectedKeyframes();
		if (!selected.length) return null;
		if (!selected.every(isEffectKeyframe)) return null;
		return selected.some(canAddDataPoint);
	}

	function fixAddDataPointButton() {
		const desired = desiredAddButtonVisibility();
		if (desired === null) return;

		const label = document.getElementById('keyframe_type_label');
		if (!label) return;
		const button = label.querySelector(':scope > .in_list_button');
		if (!button) return;
		if (button.hidden === !desired) return;

		button.hidden = !desired;
	}

	/**
	 * 效果关键帧在 GeckoLib 里不需要插值方式（编译时只取 effect/locator/script），
	 * 但 GeckoLib 插件会在 `update_keyframe_selection` 时把“data_points != 1 且
	 * interpolation != 'linear'”的关键帧的多余 data point 删掉，所以这里强制保持线性。
	 */
	function enforceLinearInterpolation() {
		if (!isActive() || !setting('force_linear', true)) return;
		let keyframes;
		try {
			keyframes = Timeline.keyframes;
		} catch (error) {
			return;
		}
		if (!keyframes) return;
		keyframes.forEach(keyframe => {
			if (!isEffectKeyframe(keyframe)) return;
			if (keyframe.interpolation !== 'linear' && keyframe.data_points && keyframe.data_points.length > 1) {
				keyframe.interpolation = 'linear';
			}
		});
	}

	function ensurePanelObserver() {
		const panel = document.getElementById('panel_keyframe');
		if (!panel || typeof MutationObserver === 'undefined') return;
		if (state.observer && state.observerTarget === panel) return;

		if (state.observer) state.observer.disconnect();
		state.observerTarget = panel;
		state.observer = new MutationObserver(() => {
			try {
				fixAddDataPointButton();
			} catch (error) { /* ignore */ }
		});
		state.observer.observe(panel, { attributes: true, attributeFilter: ['hidden'], childList: true, subtree: true });
	}

	/* ====================================================================== *
	 * 手动添加效果
	 * ====================================================================== */

	function addEffectToSelection() {
		const targets = selectedEffectKeyframes().filter(canAddDataPoint);
		if (!targets.length) {
			notify('请先在时间轴上选中至少一个“粒子/音效”关键帧。');
			return;
		}

		const DataPoint = (typeof KeyframeDataPoint !== 'undefined') ? KeyframeDataPoint : null;
		if (!DataPoint) return;

		Undo.initEdit({ keyframes: targets });
		targets.forEach(keyframe => {
			const point = new DataPoint(keyframe);
			if (keyframe.data_points && keyframe.data_points[0]) point.extend(keyframe.data_points[0]);
			keyframe.data_points.push(point);
		});
		try {
			Animator.preview();
		} catch (error) { /* ignore */ }
		Undo.finishEdit('Add particle effect');
		fixAddDataPointButton();
	}

	/* ====================================================================== *
	 * Patch 动画编解码器
	 * ====================================================================== */

	function resolveAnimationCodec() {
		try {
			if (typeof Codecs !== 'undefined' && Codecs.bedrock && Codecs.bedrock.format
				&& Codecs.bedrock.format.animation_codec) {
				return Codecs.bedrock.format.animation_codec;
			}
		} catch (error) { /* ignore */ }
		try {
			if (typeof AnimationCodec !== 'undefined' && AnimationCodec.codecs && AnimationCodec.codecs.bedrock) {
				return AnimationCodec.codecs.bedrock;
			}
		} catch (error) { /* ignore */ }
		return null;
	}

	/**
	 * 给动画编解码器打 patch。
	 *
	 * 每次调用都会检查当前的实现是不是我们自己的包装函数：GeckoLib 插件（或别的插件）
	 * 可能在之后重新应用 monkeypatch 把我们的包装覆盖掉，这时需要重新包装一层。
	 */
	function patchAnimationCodec() {
		const codec = resolveAnimationCodec();
		if (!codec) return;

		// 导出：compileAnimation / compileFile / saveAnimation / exportFile 都会经过这里
		if (typeof codec.compileAnimation === 'function' && codec.compileAnimation !== state.patchedCompile) {
			const previous = codec.compileAnimation;
			state.originalCompileAnimation = previous;
			state.patchedCompile = function () {
				const result = previous.apply(this, arguments);
				try {
					if (isActive()) transformCompiledAnimation(result);
				} catch (error) {
					console.error('[GeckoLib Multi-Particle] export transform failed', error);
				}
				return result;
			};
			codec.compileAnimation = state.patchedCompile;
		}

		// 导入：在 GeckoLib 的解析器创建关键帧之前合并亚刻偏移条目
		if (typeof codec.loadFile === 'function' && codec.loadFile !== state.patchedLoad) {
			const previous = codec.loadFile;
			state.originalLoadFile = previous;
			state.patchedLoad = function (file) {
				try {
					if (isActive() && setting('merge_on_import', true)) prepareImportFile(file);
				} catch (error) {
					console.error('[GeckoLib Multi-Particle] import pre-merge failed', error);
				}
				const result = previous.apply(this, arguments);
				try {
					if (isActive() && setting('merge_on_import', true)) mergeEffectKeyframesOf(result);
				} catch (error) {
					console.error('[GeckoLib Multi-Particle] import merge failed', error);
				}
				return result;
			};
			codec.loadFile = state.patchedLoad;
		}

		state.codec = codec;
	}

	function unpatchAnimationCodec() {
		const codec = state.codec;
		if (codec) {
			if (state.patchedCompile && codec.compileAnimation === state.patchedCompile && state.originalCompileAnimation) {
				codec.compileAnimation = state.originalCompileAnimation;
			}
			if (state.patchedLoad && codec.loadFile === state.patchedLoad && state.originalLoadFile) {
				codec.loadFile = state.originalLoadFile;
			}
		}
		state.codec = null;
		state.patchedCompile = null;
		state.patchedLoad = null;
		state.originalCompileAnimation = null;
		state.originalLoadFile = null;
	}

	/* ====================================================================== *
	 * 插件本体
	 * ====================================================================== */

	Plugin.register(PLUGIN_ID, {
		title: 'GeckoLib Multi-Particle Keyframes',
		author: 'DSH',
		description: '在 GeckoLib 动画编辑器中，允许同一个关键帧包含多个粒子/音效效果（像基岩版实体动画那样）。导出时自动转换为 GeckoLib 运行时可以读取的格式。',
		version: '1.0.0',
		variant: 'both',
		min_version: '5.0.0',

		onload() {
			// --- 设置项 -------------------------------------------------------
			try {
				Settings.addCategory(CATEGORY_ID, { name: 'GeckoLib 多粒子关键帧', open: false });
			} catch (error) { /* 分类已存在 */ }

			config.enabled = new Setting(PLUGIN_ID + '_enabled', {
				name: '启用多粒子关键帧',
				description: '允许一个粒子/音效关键帧包含多个效果条目（多个 data point）。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'toggle',
				value: true,
			});

			config.export_mode = new Setting(PLUGIN_ID + '_export_mode', {
				name: '导出方式',
				description: '同一关键帧上的多个效果要如何写入动画 JSON。默认“输出数组”（基岩版写法，语义为严格同一刻），'
					+ '需要运行时支持 —— 本仓库已内置 mixin（ConfluenceOtherworld: integration.geckolib.KeyFramesAdapterMixin）。'
					+ '如果动画要给没有该补丁的 GeckoLib 使用，请改成“拆分为亚刻偏移”。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'select',
				value: 'array',
				options: {
					array: '输出数组（默认，需要 mixin 补丁）',
					split: '拆分为亚刻偏移（无需改运行时）',
				},
			});

			config.array_runtime_ready = new Setting(PLUGIN_ID + '_array_runtime_ready', {
				name: '运行时已支持数组（不再提示）',
				description: '本工程已内置让 GeckoLib 解析 particle_effects / sound_effects 数组的 mixin，默认打开；'
					+ '关掉后以数组模式导出会弹一次兼容性提示。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'toggle',
				value: true,
			});

			config.sub_tick_offset = new Setting(PLUGIN_ID + '_sub_tick_offset', {
				name: '亚刻偏移（秒）',
				description: '拆分模式下，同一关键帧的第二个及以后的效果依次后移的时间。默认 0.00001 秒 = 0.0002 游戏刻，仍会在同一游戏刻触发。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'number',
				value: DEFAULT_OFFSET,
				min: MIN_OFFSET,
				max: MAX_OFFSET,
				step: 1e-5,
			});

			config.merge_on_import = new Setting(PLUGIN_ID + '_merge_on_import', {
				name: '导入时合并亚刻效果',
				description: '导入动画 JSON 时，把彼此间隔小于合并阈值的粒子/音效条目重新合成一个关键帧的多个效果。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'toggle',
				value: true,
			});

			config.merge_epsilon = new Setting(PLUGIN_ID + '_merge_epsilon', {
				name: '导入合并阈值（秒）',
				description: '只有间隔小于该值的效果条目才会被视为“同一关键帧”。默认 0.0001 秒，远小于时间轴最小的 0.001 秒吸附步长。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'number',
				value: DEFAULT_EPSILON,
				min: MIN_EPSILON,
				max: MAX_EPSILON,
				step: 1e-5,
			});

			config.force_linear = new Setting(PLUGIN_ID + '_force_linear', {
				name: '效果关键帧强制线性插值',
				description: '效果关键帧的插值方式对 GeckoLib 没有意义，但非线性的插值会让 GeckoLib 插件删掉多余的 data point，因此建议保持开启。',
				category: CATEGORY_ID,
				plugin: PLUGIN_ID,
				type: 'toggle',
				value: true,
			});

			// --- 编解码器 patch ----------------------------------------------
			patchAnimationCodec();

			// --- 事件 ---------------------------------------------------------
			const listen = (event, handler) => {
				Blockbench.on(event, handler);
				state.listeners.push([event, handler]);
			};

			listen('render_frame', () => {
				if (!isGeckoLibProject()) return;
				try {
					fixAddDataPointButton();
					enforceLinearInterpolation();
					ensurePanelObserver();
				} catch (error) {
					console.error('[GeckoLib Multi-Particle] frame update failed', error);
				}
			});

			listen('update_keyframe_selection', () => {
				try {
					fixAddDataPointButton();
				} catch (error) { /* ignore */ }
			});

			listen('select_project', () => {
				state.warnedArrayMode = false;
				state.observerTarget = null;
				patchAnimationCodec();
			});

			// --- 面板按钮 / 右键菜单 ------------------------------------------
			const condition = () => isActive() && selectedEffectKeyframes().some(canAddDataPoint);

			try {
				state.toolbarItem = new Action(PLUGIN_ID + '_add_effect', {
					name: '添加粒子效果',
					description: '给选中的粒子/音效关键帧再加一个效果条目',
					icon: 'add_circle',
					category: 'animation',
					condition,
					click() {
						addEffectToSelection();
					},
				});
				const toolbar = (typeof Toolbars !== 'undefined' && Toolbars) ? Toolbars.keyframe : null;
				if (toolbar && typeof toolbar.add === 'function') toolbar.add(state.toolbarItem);
			} catch (error) {
				console.error('[GeckoLib Multi-Particle] could not add toolbar item', error);
			}

			try {
				state.menuItem = {
					name: '添加粒子效果',
					icon: 'add_circle',
					condition,
					click() {
						addEffectToSelection();
					},
				};
				if (typeof Keyframe !== 'undefined' && Keyframe.prototype.menu
					&& typeof Keyframe.prototype.menu.addAction === 'function') {
					Keyframe.prototype.menu.addAction(state.menuItem, '#actions');
				}
			} catch (error) {
				console.error('[GeckoLib Multi-Particle] could not add menu item', error);
			}
		},

		onunload() {
			unpatchAnimationCodec();

			state.listeners.forEach(([event, handler]) => {
				try {
					Blockbench.removeListener(event, handler);
				} catch (error) { /* ignore */ }
			});
			state.listeners = [];

			if (state.observer) {
				state.observer.disconnect();
				state.observer = null;
				state.observerTarget = null;
			}

			if (state.menuItem && typeof Keyframe !== 'undefined' && Keyframe.prototype.menu
				&& Array.isArray(Keyframe.prototype.menu.structure)) {
				const index = Keyframe.prototype.menu.structure.indexOf(state.menuItem);
				if (index !== -1) Keyframe.prototype.menu.structure.splice(index, 1);
				state.menuItem = null;
			}

			if (state.toolbarItem) {
				const toolbar = (typeof Toolbars !== 'undefined' && Toolbars) ? Toolbars.keyframe : null;
				if (toolbar && typeof toolbar.remove === 'function') toolbar.remove(state.toolbarItem);
				if (typeof state.toolbarItem.delete === 'function') state.toolbarItem.delete();
				state.toolbarItem = null;
			}

			for (const name in config) {
				if (config[name] && typeof config[name].delete === 'function') config[name].delete();
				delete config[name];
			}

			// 设置分类如果已经空了就一并清掉
			try {
				const category = Settings.structure[CATEGORY_ID];
				if (category && (!category.items || Object.keys(category.items).length === 0)) {
					delete Settings.structure[CATEGORY_ID];
				}
			} catch (error) { /* ignore */ }
		},
	});
})();
