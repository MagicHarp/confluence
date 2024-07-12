package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class DeathChestBlock extends BaseChestBlock {
    public DeathChestBlock() {
        super(Properties.copy(Blocks.TRAPPED_CHEST), ModBlocks.DEATH_CHEST_BLOCK_ENTITY::get);
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    protected @NotNull Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    public boolean isSignalSource(@NotNull BlockState pState) {
        return true;
    }

    public int getSignal(@NotNull BlockState pBlockState, @NotNull BlockGetter pBlockAccess, @NotNull BlockPos pPos, @NotNull Direction pSide) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(pBlockAccess, pPos), 0, 15);
    }

    public int getDirectSignal(@NotNull BlockState pBlockState, @NotNull BlockGetter pBlockAccess, @NotNull BlockPos pPos, @NotNull Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }

    public static class Entity extends BaseChestBlock.Entity {
        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.DEATH_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
        }

        protected void signalOpenCount(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, int pEventId, int pEventParam) {
            super.signalOpenCount(pLevel, pPos, pState, pEventId, pEventParam);
            if (pEventId != pEventParam) {
                Block block = pState.getBlock();
                pLevel.updateNeighborsAt(pPos, block);
                pLevel.updateNeighborsAt(pPos.below(), block);
            }
        }
    }
}
