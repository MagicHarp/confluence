package org.confluence.mod.mixin.world.level.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.confluence.mod.common.init.ModBiomes;
import org.confluence.mod.common.init.ModSecretSeeds;
import org.confluence.mod.common.worldgen.BannedBiomeMultiNoiseBiomeSource;
import org.confluence.mod.common.worldgen.biome.injector.BiomeSourceHandler;
import org.confluence.mod.common.worldgen.biome.injector.BiomeSourceInjector;
import org.confluence.mod.mixed.IMinecraftServer;
import org.confluence.mod.mixed.IMultiNoiseBiomeSource;
import org.confluence.mod.mixed.IWorldOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/// 群系注入的核心注入点。
///
/// 与 TerraBlender 的 `MixinMultiNoiseBiomeSource` 的关键区别：这里是**环绕式**的。
/// 只有确实被本模组区域接管的坐标才不调用原实现，其余一律 `original.call(...)` 透传，
/// 所以可以和其它同样修改 `getNoiseBiome` 的模组叠加，而不会互相覆盖。
@Mixin(value = MultiNoiseBiomeSource.class, priority = 1100)
public abstract class MultiNoiseBiomeSourceMixin implements IMultiNoiseBiomeSource {
    @Unique
    private Pair<Holder<Biome>, Holder<Biome>> confluence$biomePair;

    @WrapMethod(method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;")
    private Holder<Biome> confluence$injectBiome(int x, int y, int z, Climate.Sampler sampler, Operation<Holder<Biome>> original) {
        BiomeSourceHandler handler = BiomeSourceInjector.handlerOf(confluence$self());
        if (handler == null) return original.call(x, y, z, sampler);
        return handler.resolve(x, y, z, sampler, () -> original.call(x, y, z, sampler));
    }
    /// 把区域群系并入 `possibleBiomes()`。
    ///
    /// 原版 `ChunkGenerator` 会在构造时挂一个 `FeatureSorter.buildFeaturesPerStep(possibleBiomes())`
    /// 的记忆化供应商，`ChunkGeneratorStructureState` 也用它筛结构集 —— 漏掉这里区域里的地物与结构都不会生成。
    ///
    /// 时序上安全：`ServerAboutToStartEvent` 在 `loadLevel()` 之前触发，而 `possibleBiomes()`
    /// 的首次求值发生在 `loadLevel() -> createLevels()` 里，所以不需要 TerraBlender 那种
    /// 「换掉记忆化 Supplier」的手段，也不需要 `hasAppended` 闩锁。
    @ModifyReturnValue(method = "collectPossibleBiomes", at = @At("RETURN"))
    private Stream<Holder<Biome>> confluence$addPossibleBiomes(Stream<Holder<Biome>> original) {
        BiomeSourceHandler handler = BiomeSourceInjector.handlerOf(confluence$self());
        return handler == null ? original : Stream.concat(original, handler.extraBiomes());
    }

    @Override
    public Pair<Holder<Biome>, Holder<Biome>> confluence$getBiomePair() {
        if (confluence$biomePair == null) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null) return null;
            WorldOptions worldOptions = server.getWorldData().worldGenOptions();
            long flag = IWorldOptions.of(worldOptions).confluence$getSecretFlag();
            ResourceKey<Biome> from;
            ResourceKey<Biome> to;
            if (confluence$self() instanceof BannedBiomeMultiNoiseBiomeSource) {
                return this.confluence$biomePair = new Pair<>(null, null);
            } else if (ModSecretSeeds.DRUNK_WORLD.match(flag)) {
                IMinecraftServer.of(server).confluence$updateSecretFlag(IWorldOptions.DOUBLE_EVIL);
                return this.confluence$biomePair = new Pair<>(null, null);
            } else if ((flag & IWorldOptions.DOUBLE_EVIL) == 0) {
                if (net.minecraft.util.RandomSource.create(worldOptions.seed()).nextBoolean()) {
                    from = ModBiomes.THE_CORRUPTION;
                    to = ModBiomes.THE_CRIMSON;
                    IMinecraftServer.of(server).confluence$updateSecretFlag(IWorldOptions.THE_CRIMSON);
                } else {
                    from = ModBiomes.THE_CRIMSON;
                    to = ModBiomes.THE_CORRUPTION;
                    IMinecraftServer.of(server).confluence$updateSecretFlag(IWorldOptions.THE_CORRUPTION);
                }
            } else {
                if ((flag & IWorldOptions.THE_CORRUPTION) == 0) {
                    from = ModBiomes.THE_CORRUPTION;
                    to = ModBiomes.THE_CRIMSON;
                } else {
                    from = ModBiomes.THE_CRIMSON;
                    to = ModBiomes.THE_CORRUPTION;
                }
            }
            Registry<Biome> biomes = server.registryAccess().registryOrThrow(Registries.BIOME);
            this.confluence$biomePair = new Pair<>(biomes.getHolderOrThrow(from), biomes.getHolderOrThrow(to));
        }
        return confluence$biomePair;
    }
}
