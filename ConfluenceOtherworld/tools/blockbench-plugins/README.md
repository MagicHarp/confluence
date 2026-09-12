# GeckoLib Multi-Particle Keyframes

让 **GeckoLib Animated Model**（Blockbench）的动画编辑器可以像基岩版实体动画那样，在**一个关键帧**上放
**多个粒子效果**（以及多个音效），而不是一个关键帧只能有一个效果。

包含两部分：

| 部分            | 位置                                                                                                            | 作用                                                             |
|---------------|---------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| Blockbench 插件 | `ConfluenceOtherworld/tools/blockbench-plugins/geckolib_multi_particle.js`                                    | 编辑器侧：恢复“+”按钮、多效果关键帧、按数组/亚刻偏移导出                                 |
| Mixin         | `ConfluenceOtherworld/src/main/java/org/confluence/mod/mixin/integration/geckolib/KeyFramesAdapterMixin.java` | 运行时侧：让 GeckoLib 原生读取 `particle_effects: [ {...}, {...} ]` 数组写法 |

* Blockbench 版本：5.x（验证于 **5.1.6** Windows 桌面版）
* GeckoLib：**4.8.3**（1.20.1 Forge）为主；已对照 4.8.4（1.21.1 NeoForge）与 main 分支（GeckoLib 5）源码
* 插件默认导出**数组**（依赖上面的 mixin）。动画若要给没有该补丁的 GeckoLib 使用，把「导出方式」改成
  `拆分为亚刻偏移` 即可。

---

## 1. 为什么原来做不到

Blockbench 核心其实**已经**支持 effect 通道（`particle` / `sound`）的关键帧带多个数据点：

| 位置                                                  | 内容                                                                       |
|-----------------------------------------------------|--------------------------------------------------------------------------|
| `timeline_animators.js` → `EffectAnimator.channels` | `particle: { max_data_points: 1000 }`、`sound: { max_data_points: 1000 }` |
| `keyframe.js` → `Keyframe#compileBedrockKeyframe`   | `data_points.length > 1` 时返回数组                                           |
| `keyframe.js` → 关键帧面板模板                             | 每个数据点都有独立的 effect / locator / script / bind_to_actor 输入框                 |

问题出在 GeckoLib 官方插件每渲染帧都会执行：

```js
// geckolib.js / ts/keyframe.ts
const addPrePostButton = document.querySelector('#keyframe_type_label > div');
if (addPrePostButton) addPrePostButton.hidden = true;
```

`#keyframe_type_label > div` 就是关键帧面板里的 **“+”（add data point）按钮**，于是编辑器里没有办法给
一个粒子关键帧再加第二个效果。

插件因此做三件事：

1. 当**选中的关键帧全部是效果关键帧**（`particle` / `sound`）时恢复 “+” 按钮；骨骼关键帧
   （rotation/position/scale）保持 GeckoLib 原本行为，不做任何改动。
2. 让效果关键帧始终保持 `linear` 插值（避免 GeckoLib 插件的 `updateKeyframe()` 删掉多余数据点）。
3. 决定导出写法（默认数组，可切成亚刻偏移），并在导入时把亚刻偏移条目合并回一个关键帧。

另外提供工具栏按钮和关键帧右键菜单的 **“添加粒子效果”**（等价于点 “+”，会复制当前效果作为新条目）。

---

## 2. 安装 / 生效

插件已经放进 Blockbench 插件目录：

```
%APPDATA%\Blockbench\plugins\geckolib_multi_particle.js
```

如果 Blockbench 当时正在运行，需要**重启 Blockbench**（或
`File → Plugins → Load Plugin from File` 重新加载）才会用上新的默认值；也可以不重启，直接在
`File → Preferences → Settings → GeckoLib 多粒子关键帧 → 导出方式` 里手动选 **输出数组**。

Mixin 部分需要重新编译 mod 才生效（见第 5 节）。

---

## 3. 使用

1. 打开 / 新建 **GeckoLib Animated Model** 工程，进入 `Animate` 模式。
2. 在时间轴 **Effects → particle** 轨道上选中（或新建）一个粒子关键帧。
3. 关键帧面板出现 **“+”** 按钮 → 点一下多出一个效果条目，每个条目可单独填
   `Effect`、`Locator`、`Pre Effect Script`、`Bind to actor`。
    * 也可用工具栏 **添加粒子效果** 或右键关键帧 → **添加粒子效果**。
    * 删除某个效果：点该条目右上角 `✕`。
4. 正常保存 / 导出：`.bbmodel` 里保存的是“一个关键帧 + N 个数据点”（完全保真），
   `.animation.json` 按第 4 节写法导出。

音效（`sound` 通道）同理。

**以前用亚刻偏移导出的旧文件**：重新导入一次（导入时会把 `0.50001` 这类条目合并回一个关键帧）再导出，
就会变成数组写法。

---

## 4. 导出格式

### 方案 A：数组（默认，需要 mixin）

```json
"particle_effects": {
"0.0": [
{"effect": "pa", "locator": "la", "pre_effect_script": "sa;"
},
{
"effect": "pb", "locator": "lb", "pre_effect_script": "sb;"}
]
}
```

* 所有效果都在**同一个时间刻**，与基岩版语义完全一致，文件里不会出现 `0.00001` 之类的伪造时间码。
* 官方 GeckoLib 读不了数组（4.8.x 的 `KeyFramesAdapter`、5.x 的 `ActorAnimationParticleEffect` 都是
  `json.getAsJsonObject()`，遇数组抛 `IllegalStateException: Not a JSON Object`），所以需要第 5 节的
  mixin。
* Blockbench 侧 GeckoLib 插件本身能读数组（`data_points: particles`），往返编辑无损。

### 方案 B：拆分为亚刻偏移（兼容任何 GeckoLib，包括没打补丁的）

```json
"particle_effects": {
"0.5": {"effect": "minecraft:flame"},
"0.50001": {
"effect": "minecraft:smoke"
},
"0.50002": { "effect": "minecraft:heart"
}
}
```

为什么等于“同时发射”：

```java
// KeyFramesAdapter：时间码（秒）→ 游戏刻
new ParticleKeyframeData(Double.parseDouble(entry.getKey())*20d,effect,locator,script)

// AnimationController#processCurrentAnimation
    for(
ParticleKeyframeData keyframeData :currentAnimation.

animation().

keyFrames().

particles()){
    if(adjustedTick >=keyframeData.

getStartTick() &&this.executedKeyFrames.

add(keyframeData)){...}
    }
```

* `adjustedTick` 来自 `GeoModel#handleAnimations`：
  `currentFrameTime = currentTick + mc.getFrameTime()`，
  每**渲染帧**推进（含 partial tick）。
* 默认偏移 `1e-5 s = 0.0002 游戏刻`，几个效果会在同一游戏刻内相邻渲染帧（约 16 ms 内）全部触发；
  由于判定是 `adjustedTick >= startTick`（追上就触发），实际往往是同一帧一起触发。
* 时间码不同 → `ParticleKeyframeData` 的 `hashCode/equals` 不同，不会被 `executedKeyFrames` 去重。

### 怎么选

| 场景                          | 建议       |
|-----------------------------|----------|
| 本工程（自带 mixin），要严格同一刻触发、文件干净 | 方案 A（默认） |
| 动画要给没有补丁的 GeckoLib / 别人的整合包 | 方案 B     |

导入时无论文件里是数组还是亚刻偏移条目，都能正确还原成“一个关键帧 + 多个数据点”。

---

## 5. Mixin：让运行时原生支持数组

文件：
`ConfluenceOtherworld/src/main/java/org/confluence/mod/mixin/integration/geckolib/KeyFramesAdapterMixin.java`
（已注册进 `ConfluenceOtherworld/src/main/resources/confluence.mixins.json` 的 `mixins` 数组）。

做法：

* `@Mixin(value = KeyFramesAdapter.class, remap = false)` —— GeckoLib 不是 MC 类所以
  `remap = false`；
  `@Pseudo` 让 GeckoLib 缺失时优雅跳过。
* 在 `deserialize` 的 `HEAD` 注入并 `cancellable`，**只在检测到数组写法时**自行构建
  `Animation.Keyframes`；否则直接 return，完全走 GeckoLib 原逻辑（timeline、普通对象写法行为不变）。
* 注入点写了完整描述符，避免选中 `JsonDeserializer` 生成的 `Object deserialize(...)` bridge 方法。
* 数组元素逐个生成 `ParticleKeyframeData` / `SoundKeyframeData`，**`startTick` 完全相同、不做任何时间偏移
  **。
* 由此带来一个 GeckoLib 侧的行为：`AnimationController` 用 `Set#add` 去重，而 `KeyFrameData#equals`
  比较的是 `hashCode`（粒子的 `hashCode` = `hash(startTick, effect, locator, script)`，音效 =
  `hash(startTick, sound)`），所以**同一时间码且内容完全相同**的条目只会触发一次；
  只要 effect / locator / pre_effect_script 任一不同就互不影响，全部正常触发。
  想让同一个粒子在同一刻发射多次，用不同的 `locator`/`pre_effect_script` 区分，
  或把该关键帧改用插件的「拆分为亚刻偏移」导出。

已验证：

* `gradlew :ConfluenceOtherworld:compileJava` **BUILD SUCCESSFUL**（Mixin 注解处理器 0.8.5 通过）。
* `javap` 对照 GeckoLib 4.8.3 运行库，确认
  `deserialize(JsonElement, Type, JsonDeserializationContext)`
  返回 `Animation$Keyframes`、`Animation.Keyframes` 是公开 record（构造器签名与 mixin 一致）。
* GeckoLib 4.8.3 中 `particle_effects` 只被 `KeyFramesAdapter` 读取，一个注入点即可全覆盖。

注意：

* 运行时补丁需要重新编译 mod；`confluence.mixins.json` 的 `injectors.defaultRequire = 1`，
  将来 GeckoLib 改了方法签名会在启动阶段直接报错（便于发现，而不是静默失效）。
* GeckoLib 5 的动画加载结构不同（`ActorAnimation` + `ActorAnimationParticleEffect`），升级要另写一份。
* `ParticleStorm` 也 patch 了 GeckoLib 粒子关键帧（`ParticleKeyframeDataMixin`），两者不冲突
  （它给 `ParticleKeyframeData` 加访问器，本 mixin 只管反序列化）。

---

## 6. 设置项

`File → Preferences → Settings → GeckoLib 多粒子关键帧`

| 设置             | 默认        | 说明                                         |
|----------------|-----------|--------------------------------------------|
| 启用多粒子关键帧       | 开         | 总开关（关掉后不做任何转换，也不恢复 “+” 按钮）                 |
| 导出方式           | **输出数组**  | `输出数组（默认，需要 mixin 补丁）` / `拆分为亚刻偏移（无需改运行时）` |
| 运行时已支持数组（不再提示） | 开         | 关掉后以数组模式导出会弹一次兼容性提示                        |
| 亚刻偏移（秒）        | `0.00001` | 方案 B 中第 2 个及以后效果的时间偏移                      |
| 导入时合并亚刻效果      | 开         | 导入动画 JSON 时把亚刻偏移条目合并回一个关键帧                 |
| 导入合并阈值（秒）      | `0.0001`  | 只有间隔小于该值的条目才会被合并                           |
| 效果关键帧强制线性插值    | 开         | 防止 GeckoLib 插件删除多余数据点（建议保持开启）              |

> 设置只有手动改过才会写进 Blockbench 偏好；没改过就一直跟随插件的默认值。

---

## 7. 已知限制

* **不要给粒子关键帧选非线性插值**。GeckoLib 插件的 `updateKeyframe()` 在
  `update_keyframe_selection` 时，只要动画里存在“数据点 != 1 且 interpolation != linear”的关键帧
  （例如 catmullrom 骨骼关键帧），就会从**当前选中**的关键帧里删掉多余数据点 —— 这是 GeckoLib
  插件自身的行为，本插件通过“强制效果关键帧为线性”规避。
* **GeckoLib 插件的 pre/post 转换**：把骨骼关键帧设成 `step` 时，GeckoLib 会把它变成“线性 + 两个数据点”，
  若此时**同时选中了粒子关键帧**，它也会顺手给粒子关键帧加一个重复数据点，面板点 `✕` 删掉即可。
* 数组写法下，**同一时间刻 + 内容完全相同**的条目只会触发一次：GeckoLib 的 `AnimationController`
  用 `Set#add` 去重，而 `KeyFrameData#equals` 比的是 `hashCode`（粒子含
  `startTick/effect/locator/script`，
  音效含 `startTick/sound`）。内容不同的条目不受影响。想让同一粒子在同一刻发射多次，
  用不同的 `locator` / `pre_effect_script` 区分，或对该关键帧改用「拆分为亚刻偏移」导出。
* 粒子能否显示、`locator` 是否存在，取决于 mod 里的 `ParticleKeyframeHandler` / locator 定义，与本插件无关。
* 插件只影响 `geckolib_model` 格式；基岩版格式完全保持原样。

---

## 8. 测试

插件逻辑有离线单元测试（最小化 Blockbench 全局桩，Node 直跑，无第三方依赖）：

```bash
node ConfluenceOtherworld/tools/blockbench-plugins/test/geckolib_multi_particle.test.mjs
```

覆盖（13 项）：插件注册 / 设置项、**默认输出数组**、0.0 关键帧两效果的数组输出、数组模式提示与抑制、
亚刻偏移拆分（含 `0.1 + 1e-5 → "0.10001"` 浮点噪声检查）、导入合并与阈值边界、模型层兜底合并、
添加效果动作、恢复 “+” 按钮（粒子显示 / 骨骼不干预 / 超过 `max_data_points` 隐藏）、强制线性插值、卸载还原。

**仍需人工确认**：真实时间轴交互、面板 Vue 重渲染时按钮的观感、以及游戏内实际触发粒子的表现
（含 mixin 生效后数组写法的触发次数）。
