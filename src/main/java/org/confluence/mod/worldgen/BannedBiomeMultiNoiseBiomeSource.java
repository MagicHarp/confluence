package org.confluence.mod.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.confluence.mod.mixin.accessor.MultiNoiseBiomeSourceAccessor;
import org.jetbrains.annotations.NotNull;

public class BannedBiomeMultiNoiseBiomeSource extends MultiNoiseBiomeSource {
    private final ResourceKey<Biome> bannedBiome;
    private final ResourceKey<Biome> targetBiome;
    private Holder<Biome> target;

    public BannedBiomeMultiNoiseBiomeSource(MultiNoiseBiomeSource biomeSource, ResourceKey<Biome> bannedBiome, ResourceKey<Biome> targetBiome) {
        super(((MultiNoiseBiomeSourceAccessor) biomeSource).getParameters());
        this.bannedBiome = bannedBiome;
        this.targetBiome = targetBiome;
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int pX, int pY, int pZ, Climate.@NotNull Sampler pSampler) {
        Holder<Biome> biome = super.getNoiseBiome(pX, pY, pZ, pSampler);
        if(biome.is(bannedBiome)) {
            if (target == null) {
                possibleBiomes().forEach(holder -> {
                    if (target == null && holder.is(targetBiome)) {
                        this.target = holder;
                    }
                });
            }
            return target;
        }
        return biome;
    }
}
