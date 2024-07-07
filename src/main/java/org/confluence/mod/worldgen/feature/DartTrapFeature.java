package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.functional.StateProperties;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;

import java.util.function.Predicate;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public class DartTrapFeature extends Feature<DartTrapFeature.Config> {
    public DartTrapFeature(Codec<Config> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        Config config = pContext.config();
        WorldGenLevel level = pContext.level();
        BlockPos blockPos = pContext.origin();
        if (!ModFeatures.isPosAir(level, blockPos)) return false;
        BlockPos.MutableBlockPos mutablePos = blockPos.mutable();
        for (int v = 1; v < config.maxSearchDown && ModFeatures.isPosAir(level, mutablePos); ++v) {
            mutablePos.move(Direction.DOWN);
        }
        if (ModFeatures.isPosSturdy(level, mutablePos, Direction.UP)) {
            BlockPos supportPos = mutablePos.immutable();
            BlockPos adapterPos = supportPos.below();
            if (ModFeatures.isPosExposed(level, adapterPos)) return false;
            BlockPos dartPos = mutablePos.offset(0, 2, 0);
            Direction opposite = null;
            for (Direction direction : ModFeatures.HORIZONTAL) {
                BlockPos.MutableBlockPos copy = dartPos.mutable();
                int h;
                for (h = 1; h < config.maxDistance && ModFeatures.isPosAir(level, copy); ++h) {
                    copy.move(direction);
                }
                if (h >= 4 && !ModFeatures.isPosAir(level, copy)) {
                    dartPos = copy.immutable();
                    opposite = direction.getOpposite();
                    break;
                }
            }
            if (opposite == null) return false;
            Predicate<BlockState> predicate = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
            BlockState dartTrap = ModBlocks.DART_TRAP.get().defaultBlockState().setValue(FACING, opposite);
            safeSetBlock(level, dartPos, dartTrap, predicate);
            safeSetBlock(level, supportPos.above(), ModFeatures.getPressurePlate(level, supportPos), predicate);
            safeSetBlock(level, adapterPos, ModBlocks.SIGNAL_ADAPTER.get().defaultBlockState().setValue(StateProperties.REVERSE, true), predicate);
            AbstractMechanicalBlock.Entity dart = ModFeatures.getEntity(level, dartPos);
            AbstractMechanicalBlock.Entity adapter = ModFeatures.getEntity(level, adapterPos);
            if (dart != null && adapter != null) dart.connectTo(0x00FF00, adapterPos, adapter);
            return true;
        }
        return false;
    }

    public record Config(int maxDistance, int maxSearchDown) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("maxDistance").orElse(64).forGetter(Config::maxDistance),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxSearchDown").orElse(32).forGetter(Config::maxSearchDown)
        ).apply(instance, Config::new));
    }
}
