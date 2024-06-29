package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.confluence.mod.block.functional.BoulderBlock;

public class BoulderTrapFeature extends Feature<BoulderTrapFeature.Config> {
    public BoulderTrapFeature(Codec<Config> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        return false;
    }

    public record Config(ResourceLocation boulderId) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("boulderId").orElse(BoulderBlock.NORMAL).forGetter(Config::boulderId)
        ).apply(instance, Config::new));
    }
}
