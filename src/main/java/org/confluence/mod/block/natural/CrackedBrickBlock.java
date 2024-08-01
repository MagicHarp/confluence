package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrackedBrickBlock extends Block {
    public CrackedBrickBlock() {
        super(Properties.copy(Blocks.STONE_BRICKS));
    }

    @Override
    public void updateEntityAfterFallOn(@NotNull BlockGetter blockGetter, @NotNull Entity entity) {
        BlockState blockState = blockGetter.getBlockState(entity.getOnPos());
        if (blockState.is(ModBlocks.CRACKED_BLUE_BRICK.get())
            || blockState.is(ModBlocks.CRACKED_GREEN_BRICK.get())
            || blockState.is(ModBlocks.CRACKED_PINK_BRICK.get())) {
            super.updateEntityAfterFallOn(blockGetter, entity);
        }
    }

    @Override
    public void playerDestroy(Level p_54157_, Player player, BlockPos p_54159_, BlockState p_54160_, @Nullable BlockEntity p_54161_, ItemStack p_54162_) {
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
    }

    public interface IceSafe {
    }
}
