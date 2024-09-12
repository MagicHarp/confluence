package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EvaporativeCloudBlock extends BaseEntityBlock implements EntityBlock {

    public EvaporativeCloudBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.SNOW).randomTicks());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }
        return ModUtils.getTicker(pBlockEntityType, ModBlocks.EVAPORATIVE_CLOUD_BLOCK_ENTITY.get(), (level, blockPos, blockState, e) -> {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = blockPos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                if (neighborState.getBlock() == Blocks.WATER) {
                    level.setBlockAndUpdate(neighborPos, Blocks.WATER.defaultBlockState());
                    level.removeBlock(blockPos, false);
                }
            }
        });
    }

    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, net.minecraft.world.entity.Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    public static class Entity extends BlockEntity {
        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.EVAPORATIVE_CLOUD_BLOCK_ENTITY.get(), pPos, pBlockState);
        }
    }
}
