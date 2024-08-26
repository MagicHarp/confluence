package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class JewelryTreeFeature extends Feature<JewelryTreeFeature.Config> {
    public JewelryTreeFeature(Codec<Config> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        // todo 放置逻辑
        return false;
    }

    public record Config(BlockStateProvider trunk, BlockStateProvider brunch, int maxWidth, int maxHeight) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("brunch").forGetter(Config::brunch),
            BlockStateProvider.CODEC.fieldOf("trunk").forGetter(Config::trunk),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxWidth").forGetter(Config::maxWidth),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxHeight").forGetter(Config::maxHeight)
        ).apply(instance, Config::new));
    }
}
