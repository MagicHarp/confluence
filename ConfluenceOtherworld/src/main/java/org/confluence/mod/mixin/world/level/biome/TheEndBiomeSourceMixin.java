package org.confluence.mod.mixin.world.level.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import org.confluence.lib.mixed.SelfGetter;
import org.confluence.mod.common.worldgen.biome.injector.BiomeSourceHandler;
import org.confluence.mod.common.worldgen.biome.injector.BiomeSourceInjector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/// 末地群系注入。
///
/// 旧实现用 `@ModifyReturnValue` + `RETURN ordinal` 逐个替换 `getNoiseBiome` 的 3 个返回点
/// （跳过中心岛），既依赖字节码里 RETURN 的出现顺序，又有个致命问题：这个 mixin
/// **从来没有被登记进 `confluence.mixins.json`**，属于死代码。
/// 这里改成环绕整个方法的 `@WrapMethod`，一次覆盖所有返回路径，不再依赖 ordinal。
///
/// 中心岛不需要额外保护：`TheEndBiomeHolder#replaceBiome` 自身有 3000 格半径的波纹抑制，
/// 距离中心小于该半径时 `trueNoise` 恒不为正，会原样返回原版群系。
///
/// 处理器当前是透传（`TheEndBiomeHolder#open` 未被调用），所以末地生成行为不变。
@Mixin(TheEndBiomeSource.class)
public abstract class TheEndBiomeSourceMixin implements SelfGetter<TheEndBiomeSource> {
    @WrapMethod(method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;")
    private Holder<Biome> confluence$injectBiome(int x, int y, int z, Climate.Sampler sampler, Operation<Holder<Biome>> original) {
        BiomeSourceHandler handler = BiomeSourceInjector.handlerOf(confluence$self());
        if (handler == null) return original.call(x, y, z, sampler);
        return handler.resolve(x, y, z, sampler, () -> original.call(x, y, z, sampler));
    }

    @ModifyReturnValue(method = "collectPossibleBiomes", at = @At("RETURN"))
    private Stream<Holder<Biome>> confluence$addPossibleBiomes(Stream<Holder<Biome>> original) {
        BiomeSourceHandler handler = BiomeSourceInjector.handlerOf(confluence$self());
        return handler == null ? original : Stream.concat(original, handler.extraBiomes());
    }
}
