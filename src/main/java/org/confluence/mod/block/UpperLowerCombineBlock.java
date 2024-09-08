package org.confluence.mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.DOUBLE_BLOCK_HALF;

public abstract class UpperLowerCombineBlock extends Block {
    public UpperLowerCombineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return pContext.getLevel().getBlockState(pContext.getClickedPos().above()).canBeReplaced(pContext) ? defaultBlockState() : null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide) {
            pLevel.setBlockAndUpdate(pPos.above(), defaultBlockState().setValue(DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        pLevel.setBlockAndUpdate(pPos.relative(getConnectedDirection(pState)), Blocks.AIR.defaultBlockState());
    }

    public static Direction getConnectedDirection(BlockState blockState) {
        return blockState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN;
    }
}
