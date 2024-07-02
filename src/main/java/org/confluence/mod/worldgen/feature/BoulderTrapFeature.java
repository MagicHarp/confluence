package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.functional.BoulderBlock;
import org.confluence.mod.block.functional.StateProperties;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class BoulderTrapFeature extends Feature<BoulderTrapFeature.Config> {
    private static final Direction[] HORIZONTAL = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

    public BoulderTrapFeature(Codec<Config> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        Config config = pContext.config();
        Block block = config.variant.get();
        if (block == null) return false;
        WorldGenLevel level = pContext.level();
        BlockPos blockPos = pContext.origin();
        if (level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir)) {
            Optional<Column> optionalColumn = Column.scan(level, blockPos, config.maxHeight, BlockBehaviour.BlockStateBase::isAir, blockState -> blockState.is(BlockTags.BASE_STONE_OVERWORLD));
            if (optionalColumn.isPresent() && optionalColumn.get() instanceof Column.Range range && range.height() > 4) {
                BlockPos adapterPos = blockPos.atY(range.floor() - 1);
                for (Direction direction : HORIZONTAL) {
                    if (level.isStateAtPosition(adapterPos.offset(direction.getNormal()), BlockBehaviour.BlockStateBase::isAir)) {
                        return false;
                    }
                }

                BlockPos boulderPos = blockPos.atY(range.ceiling());
                Predicate<BlockState> predicate = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
                safeSetBlock(level, boulderPos, block.defaultBlockState(), predicate);
                BlockState pressurePlate = level.isStateAtPosition(blockPos.atY(range.floor()), blockState -> blockState.is(Blocks.DEEPSLATE)) ?
                    ModBlocks.DEEPSLATE_PRESSURE_PLATE.get().defaultBlockState() : Blocks.STONE_PRESSURE_PLATE.defaultBlockState();
                safeSetBlock(level, blockPos.atY(range.floor() + 1), pressurePlate, predicate);
                BlockState signalAdapter = ModBlocks.SIGNAL_ADAPTER.get().defaultBlockState().setValue(StateProperties.REVERSE, true);
                safeSetBlock(level, adapterPos, signalAdapter, predicate);
                AbstractMechanicalBlock.Entity boulder = getEntity(level, boulderPos);
                AbstractMechanicalBlock.Entity adapter = getEntity(level, adapterPos);
                if (boulder != null && adapter != null) boulder.connectTo(0xFF0000, adapterPos, adapter);
                return true;
            }
        }
        return false;
    }

    private static @Nullable AbstractMechanicalBlock.Entity getEntity(WorldGenLevel level, BlockPos blockPos) {
        if (level.getBlockEntity(blockPos) instanceof AbstractMechanicalBlock.Entity entity) {
            return entity;
        }
        Confluence.LOGGER.error("Failed to fetch boulder block entity at ({}, {}, {})", blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return null;
    }

    public record Config(BoulderBlock.Variant variant, int maxHeight) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BoulderBlock.Variant.CODEC.fieldOf("variant").orElse(BoulderBlock.Variant.NORMAL).forGetter(Config::variant),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxHeight").orElse(64).forGetter(Config::maxHeight)
        ).apply(instance, Config::new));
    }
}
