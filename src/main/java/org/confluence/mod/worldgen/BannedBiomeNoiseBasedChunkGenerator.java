package org.confluence.mod.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.confluence.mod.mixin.accessor.MultiNoiseBiomeSourceAccessor;
import org.confluence.mod.mixin.accessor.MultiNoiseBiomeSourceParameterListAccessor;
import org.jetbrains.annotations.NotNull;

public class BannedBiomeNoiseBasedChunkGenerator extends NoiseBasedChunkGenerator {
    public static final Codec<BannedBiomeNoiseBasedChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(BannedBiomeNoiseBasedChunkGenerator::getBiomeSource),
            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(BannedBiomeNoiseBasedChunkGenerator::generatorSettings),
            ResourceKey.codec(Registries.BIOME).fieldOf("banned_biome").forGetter(BannedBiomeNoiseBasedChunkGenerator::getBannedBiome)
    ).apply(instance, instance.stable(BannedBiomeNoiseBasedChunkGenerator::new)));
    private final ResourceKey<Biome> bannedBiome;

    public BannedBiomeNoiseBasedChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settingsHolder, ResourceKey<Biome> bannedBiome) {
        super(biomeSource, settingsHolder);
        this.bannedBiome = bannedBiome;
    }

    @Override
    public void applyBiomeDecoration(@NotNull WorldGenLevel pLevel, @NotNull ChunkAccess pChunk, @NotNull StructureManager pStructureManager) {
        if (biomeSource instanceof MultiNoiseBiomeSource multiNoise) {
            var parameters = ((MultiNoiseBiomeSourceAccessor) multiNoise).getParameters();
            parameters.ifLeft(left -> {
                var pairs = Lists.newArrayList(left.values());
                pairs.removeIf(pair -> pair.getSecond().is(bannedBiome));
                ((MultiNoiseBiomeSourceAccessor) multiNoise).setParameters(Either.left(new Climate.ParameterList<>(ImmutableList.copyOf(pairs))));
            });
            parameters.ifRight(right -> {
                var pairs = Lists.newArrayList(right.value().parameters().values());
                pairs.removeIf(pair -> pair.getSecond().is(bannedBiome));
                ((MultiNoiseBiomeSourceParameterListAccessor) right.value()).setParameters(new Climate.ParameterList<>(ImmutableList.copyOf(pairs)));
                ((MultiNoiseBiomeSourceAccessor) multiNoise).setParameters(Either.right(right));
            });
        }
        super.applyBiomeDecoration(pLevel, pChunk, pStructureManager);
    }

    public ResourceKey<Biome> getBannedBiome() {
        return bannedBiome;
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
}
