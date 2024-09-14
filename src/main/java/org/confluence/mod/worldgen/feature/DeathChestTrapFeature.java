package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class DeathChestTrapFeature  extends Feature<DeathChestTrapFeature.Config> {
    public DeathChestTrapFeature(Codec<Config> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        return false;
    }

    public record Config(int maxDistance, int maxSearchDown) implements FeatureConfiguration {
        public static final Codec<DartTrapFeature.Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.POSITIVE_INT.fieldOf("maxDistance").orElse(32).forGetter(DartTrapFeature.Config::maxDistance),
                ExtraCodecs.POSITIVE_INT.fieldOf("maxSearchDown").orElse(32).forGetter(DartTrapFeature.Config::maxSearchDown)
        ).apply(instance, DartTrapFeature.Config::new));
    }
}
