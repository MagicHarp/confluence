# Confluence Biome Injector（自研群系添加器）

取代 TerraBlender 的自研实现，位于
`ConfluenceOtherworld/src/main/java/org/confluence/mod/common/worldgen/biome/injector/`。

对外入口是 `ConfluenceBiomeInjector`。

---

## 1. 三个核心概念

| 概念 | 类 | 作用 |
|---|---|---|
| 区域 | `BiomeRegion` | 一块由气候参数盒子定义的群系区域。声明它可能产出哪些群系、占多少权重、接管哪些气候范围 |
| 区域分配器 | `BiomeRegionAllocator` | 把石英坐标 `(x, z)` 映射成「这一列归哪个区域」，0 表示原版 |
| 地表规则注册表 | `SurfaceRuleRegistry` | 按维度类别收集规则，在真正的原版规则源前后拼接 |

### 查询流程

```
getNoiseBiome(qx, qy, qz, sampler)
  ├─ allocator.index(qx, qz) == 0            → 原版（直接透传，不采样气候）
  └─ 否则取 sampler.sample(qx, qy, qz)
       └─ 在该区域的参数盒子里按声明顺序找第一个包含当前气候单元的
            ├─ 找到 → 返回该群系
            └─ 找不到 → 回落原版
```

关键点：用的是**包含判定**，不是 `Climate.RTree` 的最近邻。
TerraBlender 因为用最近邻，必须再引入 `terrablender:deferred_placeholder` 占位群系和
`VanillaParameterOverlayBuilder` 的补集展开来兜底；这里一个都不需要，
也就不用访问包私有的 `Climate$RTree`（省掉一整套 access widener）。

---

## 2. 区域分配器

`BiomeRegionAllocator` 用单个 `NormalNoise` 产生连续区域场，不复刻 TerraBlender 那套
移植自原版 `ZoomLayer` 的分层网格。

**权重语义**：噪声值先按**实测标准差**归一化成 `z`，再用 logistic 分布函数近似标准正态 CDF
映射到 `t ∈ (0,1)`，最后按权重把 `t` 切成等宽带。因为分带边界可以反解成 `z` 上的阈值

```
z_k = ln(t_k / (1 - t_k)) / 1.702
```

查询时只需一次比较 + 顺序扫描，不需要 `exp`。

实测标准差的采样让这套映射与实际振幅、倍频程个数无关，`weight` 直接就是面积占比
（实测量级误差 ≤ 1 个百分点，验证程序见 `build/_alloctest/BandingTest.java`）。

**索引稳定性**：区域索引只由本模组自己的区域列表顺序决定。装/卸任何第三方群系模组都
不会改变世界的区域布局 —— 这是相对 TerraBlender 最实质的兼容性收益
（TerraBlender 的索引来自所有模组共享的全局加权表）。

---

## 3. 时序

| 阶段 | 位置 | 做什么 |
|---|---|---|
| `FMLCommonSetupEvent` | `ModEvents#commonSetup` → `ModBiomes#registerRegionAndSurface` | `bootstrap()` 登记区域；登记地表规则 |
| `ServerAboutToStartEvent` | `ServerEvents#serverAboutToStart` → `ConfluenceBiomeInjector#install` | 按 `LevelStem` 建区域表、挂到 `BiomeSource` 实例、给生成器写地表规则 |
| `ServerStoppingEvent` | `ServerEvents#serverStopping` → `uninstall()` | 清空处理器表 |

**时序依据**：Forge 在 `DedicatedServer#initServer` / `IntegratedServer#initServer` 里于
`loadLevel()` **之前**触发 `ServerAboutToStartEvent`（见 `ServerLifecycleHooks#handleServerAboutToStart`，
它先设 `currentServer` 再 post 事件）；而原版第一次读取 `possibleBiomes()` 发生在
`loadLevel() -> createLevels() -> ChunkGeneratorStructureState.createForNormal` 里。

所以这里改 `collectPossibleBiomes` 一定生效，**不需要** TerraBlender 那种
「换掉记忆化 Supplier」的手段，也没有 `hasAppended` 这类「只能追加一次」的闩锁。
`possibleBiomes()` 必须带上区域群系，否则 `FeatureSorter.buildFeaturesPerStep`
不会为它们排序地物步骤，区域里的地物与结构都不会生成。

---

## 4. 注入点

| Mixin | 目标 | 手法 | 说明 |
|---|---|---|---|
| `world.level.biome.MultiNoiseBiomeSourceMixin` | `getNoiseBiome(III,Sampler)` | `@WrapMethod` | **环绕式**：非我方归属一律 `original.call(...)`，可与其它群系模组叠加 |
| 同上 | `collectPossibleBiomes()` | `@ModifyReturnValue` | 并入区域群系 |
| `world.level.biome.TheEndBiomeSourceMixin` | `getNoiseBiome(III,Sampler)` | `@WrapMethod` | 末地不使用气候参数，走同一个 `BiomeSourceHandler` 调度 |
| `world.level.levelgen.NoiseBasedChunkGeneratorMixin` | 7 参 `buildSurface` 里的 `settings.surfaceRule()` | `@WrapOperation` | 建面规则替换 |
| 同上 | `applyCarvers` 里的 `settings.surfaceRule()` | `@WrapOperation` | 挖洞时 `CarvingContext` 拿到的规则，供 `SurfaceSystem#topMaterial` |

### 为什么地表规则挂在生成器实例上

原版 8 参 `SurfaceSystem#buildSurface` 的调用点在
`NoiseBasedChunkGenerator#buildSurface(ChunkAccess, WorldGenerationContext, RandomState,
StructureManager, BiomeManager, Registry, Blender)` 里 —— 这个方法**没有** `WorldGenRegion`，
拿不到维度；而带 `WorldGenRegion` 的 4 参重载只是转发，根本不碰规则源。

所以由 `install()` 按 `LevelStem` 的维度类型把规则算好，通过 `INoiseBasedChunkGenerator`
写到生成器实例上，注入点只做替换。状态是**每实例**的：

- 同一个 `NoiseGeneratorSettings` 被多个维度复用时不会互相污染；
- 不存在 TerraBlender 那种「可变 `regionType` + `surfaceRule()` 懒记忆化」的时序陷阱
  （旧实现里只要有人在 `setRegionType` 之前调过一次 `surfaceRule()`，
  规则就会被永久缓存成原版且静默失效）。

---

## 5. 新增一个区域

```java
public final class MyRegion implements BiomeRegion {
    public static final ResourceLocation ID = Confluence.asResource("my_region");

    @Override public ResourceLocation id() { return ID; }
    @Override public int weight() { return 2; }               // 约占总面积的 2 份
    @Override public Set<ResourceKey<Biome>> biomes() { return Set.of(ModBiomes.MY_BIOME); }

    @Override
    public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        addColumn(consumer,                                    // 覆盖整列（含天空，不含最底部）
                Temperature.span(Temperature.WARM, Temperature.HOT),
                Humidity.FULL_RANGE,
                Continentalness.INLAND,
                Erosion.span(Erosion.EROSION_3, Erosion.EROSION_5),
                Weirdness.FULL_RANGE,
                ModBiomes.MY_BIOME);
    }
}
```

然后在 `ConfluenceBiomeInjector#bootstrap` 里 `OVERWORLD_REGIONS.add(new MyRegion())`。

**注意事项**

- `weight` 只影响面积占比；区域之间的实际分布由噪声场决定，不靠参数盒子区分。
  多个区域声明相同的气候盒子是允许的（下界的灰烬森林/灰烬荒原、主世界的腐化/猩红都是这样）。
- 气候轴尽量声明成**区间**。零宽度区间（`Climate.Parameter.point(v)`）在包含判定下
  几乎永远无法命中 —— 气候值是连续量。旧实现里发光蘑菇地的 `point(-0.10)` 就属于这种写法。
- `depth` 的取值大致在 `[-3, 2]`：地表约 0，往下增大，天空为负。
  用 `ParameterBuilder` 里现成的 `COLUMN` / `DEEP_UNDERGROUND` / `SURFACE` 等常量。
- 下界维度的 `erosion` / `continentalness` / `depth` / `weirdness` 路由恒为 0
  （`NoiseRouterData#nether` 全是 `DensityFunctions.zero()`），
  所以这些轴必须声明成**包含 0 的区间**才可能命中。

### 可达性：最容易踩的坑

`/locate biome` 只在玩家周围 6400 格内采样，而这个尺度上气候场近似**常量**。
所以区域能否被找到，几乎完全取决于「玩家脚下这一个点的气候是否落在声明区间内」——
四条轴各取 40%~50% 相乘之后只剩百分之几，实际表现就是「区域接管了这一列但一个群系都换不出来」。

启动日志会为每个区域各打印一次命中与一次未命中，未命中那句会带上实际气候值：

```
Biome region confluence:the_corruption took over at quart (x, y, z); climate T=... H=... C=... E=... D=... W=...
Biome region confluence:the_corruption owns quart column (x, z) but no parameter box covers T=... ...; falling back to vanilla
```

日志里的数值是 `Climate.quantizeCoord` 后的 long，除以 10000 就是实际值，对着它调区间即可。
几条经验：

- **整片替换地表的区域**（邪恶群系、灰烬系列）用 `Continentalness.LAND`（海岸到远内陆）
  而不是 `INLAND`。大陆度是平滑场，`INLAND` 只覆盖近内陆，站在海岸线上的玩家
  周围几千格都会落在区间外。
- 想要「全球都能找到」，就不要在温度/湿度/侵蚀度上叠加太多限制；
  区域之间靠**噪声带**区分就够了，不需要参数盒子也去区分。
- 洞穴群系（只靠 `depth` 定义）不要额外再叠四条窄轴，否则五轴相乘后概率趋近于零；
  保留 `depth` + 一条风味轴即可。

### `depth` 是相对地表的下沉量，不是世界高度

`NoiseRouterData` 的 depth 路由是
`yClampedGradient(-64, 320, 1.5, -1.5) + (-0.50375 + 地形偏移样条)`，
样条把 0 点对齐到地形表面，所以 **每 0.2 个 depth 单位 ≈ 25.6 格**：

| depth | 地表以下 | 含义 |
|---|---|---|
| `point(0.0)` | 0 | 原版地表群系用的值 |
| `span(0.2, 0.9)` = `UNDERGROUND` | 26–115 格 | 原版地下群系（繁茂/滴水石洞穴） |
| `span(0.35, 0.9)` = `CAVERN` | 45–115 格 | 洞穴层 |
| `span(0.6, 0.9)` = `DEEP_UNDERGROUND` | 77–115 格 | 只有最底那一段 |
| `point(1.1)` | 最底 | 原版深暗之域 |

写洞穴群系时如果发现「概率低得离谱」，先检查是不是把 `depth` 当世界高度用了。

### 整体缩放：`vanillaWeight` 才是主旋钮

所有区域和「原版」共享同一条噪声分带，只有**相对比例**有意义：

```
某个区域分到的列占比 = 区域权重 / (vanillaWeight + 所有区域权重之和)
```

因为单个区域的权重最小值就是 1，**想让新群系占得更少只能把 `vanillaWeight` 调大**。
换句话说 `vanillaWeight` 就是「全世界有多少份留给原版」这个旋钮。

主世界 `BiomeRegionType.OVERWORLD` 现在是 `27 : 1 : 1 : 1`（总计 30 ⇒ 新群系占 10% 的列）：

| | 列的占比 | 实际占比 |
|---|---|---|
| 原版 | 90.0% | — |
| 邪恶群系（腐化+猩红两带会重映射成同一个群系） | 6.67% | **4.7% 的地表**，因为整列占领所以也是 **6.7% 的地下** |
| 发光蘑菇地 | 3.33% | **2.3% 的地下体积** |

下界 `BiomeRegionType.NETHER` 是 `18 : 1 : 1`（总计 20 ⇒ 新群系占 10% 的列），
两种灰烬地形各 5%，乘温湿度闸后合计约 **6% 的下界**。

分带阈值是**标准正态分位数**（Acklam 近似），所以权重到占比的映射是精确的
（实测误差 < 0.0001）。早期版本用 logistic 近似正态 CDF，尾部反解出来的阈值偏大，
最靠后的那条带只能拿到标称值的约 70% —— 现在不会了。

### 范围太大就调 `regionSizeBlocks`（不是权重）

`BiomeRegionType` 的第三个参数是区域场的**特征尺度**，也就是「一个区域大概横跨多少格」。
它和占比**完全解耦**：占比只由分带阈值（权重）决定，跟噪声频率无关。
所以「数量合适但范围太大」只需要调小这个值，权重一概不用动。

| | 主世界 | 下界 |
|---|---|---|
| 值 | `512` 格 | `256` 格 |
| 单个区域大致跨度 | 200~400 格 | 100~250 格 |
| 参考 | 泰拉瑞亚腐化带约 300 格宽、发光蘑菇地约 150 格 | 下界只有 128 格高，本就紧凑 |

调小的效果是**更多更小的碎片**，调大是**更少更整片的大区域**。旧值 `4096` 会让单个区域
横跨上千米。启动日志里 `region size: 512 blocks` 会把这个值打出来。

### 调参实例：发光蘑菇地

`GlowingMushroomRegion` 是「像泰拉瑞亚那样挖洞能碰上」的参考调法。它**只用深度闸**，
其余五条气候轴全部放开：

```
区域带宽 weight 1 / 总计 30 = 3.33% 的列
  × 深度 UNDERGROUND [0.2, 0.9]（地表以下 26–115 格，约 90 格厚）≈ 占整个地下体积 70%
  ⇒ 整个地下体积的约 2.3%
```

**为什么其余轴要全部放开**：洞穴群系的定义条件就是「在地下多深」，跟地表是什么群系无关
（泰拉瑞亚也一样）。更重要的是，其余气候轴都是**平滑大尺度场** —— 在 `/locate biome`
那 ±6400 格窗口里近似常量，只要玩家站的地方不在区间内，整个窗口就是 0 命中。
`owns column ... but no parameter box covers H=514` 这类日志就是「整段出界」。

**深度闸特意取到 26 格这么浅**有两个原因：这个带厚约 90 格，比 `/locate biome` 的
y 采样步长（64 格）厚，保证搜索不会整条漏掉；而且泰拉瑞亚的发光蘑菇地也常出现在
地下层而不只是最深的洞穴层。

### `/locate biome` 找不到 ≠ 没生成

`/locate biome` 的搜索半径是**硬编码的 6400 格**，而区域场是 4096 格周期的大尺度场 ——
这个窗口里只有大约 3×3 个独立区域格。所以对一个占比几个百分点的群系，
**从随机位置一次找到的概率只有几成**：

| 群系占列比 | 一次找到 | 连试三次 |
|---|---|---|
| 3.3%（蘑菇地） | ~26% | ~60% |
| 6.7%（邪恶群系） | ~46% | ~84% |
| 25% | ~93% | ~100% |

找不到是正常现象，尤其站在海里时（邪恶群系被 `Continentalness.LAND` 排除在外）。

可靠的验证方式按可信度排序：

1. **启动日志的 `Measured band shares`** —— 在真实种子上抽样 26 万列测出的带宽占比。
   它不依赖世界生成，直接证明「权重 → 面积」映射在这个种子上成立。
2. **启动日志的 `Biome region ... took over at block (x, y, z)`** —— 一次性打印，
   坐标已经换算成方块坐标，直接 `/tp` 过去就能看到群系。
3. 挖到地表以下 26 格以上，按 F3 看群系名。
4. `/locate biome` 连试几次（每次换个地方，它是实时计算的，不用开新世界）。

配套的一条经验：群系既然已经被 `depth` 闸限在地下，**地表规则里就不要再叠硬高度闸**。
`SurfaceSystem` 每处理一个 y 都会重建 `Context.biome` 供应器
（`SurfaceRules$Context#updateY`），所以 `SurfaceRules.isBiome` 是逐 y 采样的，
地表永远不会命中地下群系 —— 多一层 `y <= N` 只会在高山地形把同一个群系切成两半。

---

## 6. 修改 SurfaceRule

```java
// 只作用于自己群系的规则：先于原版规则求值
ConfluenceBiomeInjector.addSurfaceRules(
        SurfaceRuleRegistry.Category.OVERWORLD,
        SurfaceRuleRegistry.Stage.PREPEND,
        10,
        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.MY_BIOME), SurfaceRules.state(Blocks.STONE)));

// 作为兜底：只在原版规则返回 null 的位置生效
ConfluenceBiomeInjector.addSurfaceRules(category, SurfaceRuleRegistry.Stage.APPEND, 0, rules);

// 按 owner 移除
ConfluenceBiomeInjector.removeSurfaceRules(SurfaceRuleRegistry.Category.OVERWORLD, "my_mod");
```

`Stage.PREPEND` 等价于 TerraBlender 的 `BEFORE_BEDROCK`：`SurfaceRules.sequence` 是
「首个非 null 获胜」，所以排在前面就是优先级更高。

**去重键是 `(owner, stage, priority)` 三元组，不是单独的 owner。**
同一个维度上经常要注册多条规则（本模组的 `confluence:biomes` 和
改写原版群系的 `confluence:vanilla_overrides` 就是这样），
如果只按 owner 去重，后一条会把前一条**静默删掉** —— 现象就是「地表规则没生效」，
而且启动时不会有任何报错。启动日志里 `Surface rules for OVERWORLD (level stem ...)`
这一行会把实际注册进去的规则逐条列出来，先看它。

**这里不复制原版地表规则**。TerraBlender 为了能在「原版规则之前/之后」两个位置插入，
整份复制了原版 `SurfaceRuleData.overworldLike` / `nether`（700+ 行），
必须随 MC 版本同步维护；本实现直接把真正的原版规则对象拿过来拼接。

---

## 7. 与 TerraBlender 的对照

| | TerraBlender | 本实现 |
|---|---|---|
| 区域索引 | 所有模组共享的全局加权表，按注册顺序 | 本模组自持的有序列表 |
| 装/卸第三方群系模组 | 会改变世界布局 | 完全不影响 |
| 未覆盖气候单元 | 占位群系 `terrablender:deferred_placeholder` + 2⁶ 补集展开 | 包含判定失败即回落原版 |
| 地表规则 | 需要整份复制原版规则 | 直接拼真正的原版规则源 |
| 维度状态 | 往共享的 `NoiseGeneratorSettings` 上挂可变 `regionType` | 挂在生成器实例上 |
| `possibleBiomes` | access widener 改可变字段 + 换掉记忆化 Supplier + `hasAppended` 闩锁 | `@ModifyReturnValue` 追加即可 |
| `getNoiseBiome` | 整体替换返回值 | `@WrapMethod` 环绕，可叠加 |
| 覆盖维度 | 只有主世界与下界 | 主世界 / 下界 / 末地 |
| 前置 | 独立模组，`mods.toml` 里 `mandatory = true` | 无外部前置 |

---

## 8. 已知限制

- **一个世界只会存在两种邪恶群系中的一种**。`MultiNoiseBiomeSourceMixin#confluence$getBiomePair`
  按世界种子二选一，`install()` 把另一个区域的群系键整体改写成选中的那个。
  因此被换掉的那个用 `/locate biome` **一定**报 not found（而且因为不在 `possibleBiomes()` 里，
  是立刻返回）。这是设计如此。只有醉酒世界（`DRUNK_WORLD` → `DOUBLE_EVIL`）和
  「不是蜜蜂」密种会两种共存 —— 后者的这一行为是从替换前的实现原样保留下来的
  （旧 `OverworldUtils#replaceBiome` 只在 `else` 分支里取互换对）。
- **单人模式的世界创建界面**（Buffet 群系列表）读的是客户端 `possibleBiomes()`，
  那时服务器还没启动，列表里看不到区域群系。不影响实际世界生成。
- **自定义维度**：只有维度类型是 `minecraft:overworld` / `the_nether` / `the_end`
  的维度会被接管，其它一律保持原版行为。
- **区域表或参数盒子变更**会改变世界布局。已生成的区块保留旧群系，新生成的区块用新布局，
  同一存档里会出现接缝。需要跨版本兼容时应按 `IWorldOptions#confluence$getVersion`
  那套机制做布局版本迁移。
- **末地处理器当前是透传**：`TheEndBiomeHolder#open` / `close` 仍是注释状态，
  末地生成行为与替换 TerraBlender 之前一致。要启用恢复那两处调用即可。
- **诊断日志是一次性的**：每个区域只打印一次命中与一次未命中，用来判断区域是否生效；
  确认没问题后可以删掉 `BiomeRegionTable#diagnose` 的调用。
  同理 `InjectionProbe` 的三条 `[probe]` 日志只用来确认「注入点真的替换了」，
  调试完可以连同调用处一起删掉。
