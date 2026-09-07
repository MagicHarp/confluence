package org.confluence.mod.mixin.integration.terrablender;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import org.confluence.mod.common.worldgen.TheEndBiomeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(TheEndBiomeSource.class)
public abstract class TheEndBiomeSourceMixin { // todo 1.3.0
    @ModifyReturnValue(method = "getNoiseBiome", at = @At(value = "RETURN", ordinal = 1))
    private Holder<Biome> replaceBiome1(
            Holder<Biome> original,
            int x, int y, int z,
            Climate.Sampler sampler
    ) {
        return TheEndBiomeHolder.replaceBiome(x, y, z, sampler, original);
    }

    @ModifyReturnValue(method = "getNoiseBiome", at = @At(value = "RETURN", ordinal = 2))
    private Holder<Biome> replaceBiome2(
            Holder<Biome> original,
            int x, int y, int z,
            Climate.Sampler sampler
    ) {
        return TheEndBiomeHolder.replaceBiome(x, y, z, sampler, original);
    }

    @ModifyReturnValue(method = "getNoiseBiome", at = @At(value = "RETURN", ordinal = 3))
    private Holder<Biome> replaceBiome3(
            Holder<Biome> original,
            int x, int y, int z,
            Climate.Sampler sampler
    ) {
        return TheEndBiomeHolder.replaceBiome(x, y, z, sampler, original);
    }

    @ModifyReturnValue(method = "collectPossibleBiomes", at = @At("RETURN"))
    private Stream<Holder<Biome>> addConfluence(Stream<Holder<Biome>> original) {
        return TheEndBiomeHolder.addConfluenceBiomes(original);
    }
}
