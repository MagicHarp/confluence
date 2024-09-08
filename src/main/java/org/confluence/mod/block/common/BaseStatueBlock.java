package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.UpperLowerCombineBlock;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.DOUBLE_BLOCK_HALF;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class BaseStatueBlock extends UpperLowerCombineBlock implements CustomModel {
    private static final VoxelShape LOWER_SHAPE_Z = Shapes.or(
            box(0, 0, 0, 16, 3, 16),
            box(1, 3, 1, 15, 7, 15),
            box(0, 7, 0, 16, 10, 16),
            box(1, 10, 6, 15, 16, 10)
    );
    private static final VoxelShape LOWER_SHAPE_X = Shapes.or(
            box(0, 0, 0, 16, 3, 16),
            box(1, 3, 1, 15, 7, 15),
            box(0, 7, 0, 16, 10, 16),
            box(6, 10, 1, 10, 16, 15)
    );
    private static final VoxelShape UPPER_SHAPE_Z = box(1, 0, 6, 15, 14, 10);
    private static final VoxelShape UPPER_SHAPE_X = box(6, 0, 1, 10, 14, 15);

    public BaseStatueBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(stateDefinition.any()
                .setValue(DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DOUBLE_BLOCK_HALF, HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        boolean isLower = pState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
        return switch (pState.getValue(HORIZONTAL_FACING)) {
            case NORTH, SOUTH -> isLower ? LOWER_SHAPE_Z : UPPER_SHAPE_Z;
            default -> isLower ? LOWER_SHAPE_X : UPPER_SHAPE_X;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockState = super.getStateForPlacement(pContext);
        return blockState == null ? null : blockState.setValue(HORIZONTAL_FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide) {
            pLevel.setBlockAndUpdate(pPos.above(), defaultBlockState()
                    .setValue(DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)
                    .setValue(HORIZONTAL_FACING, pState.getValue(HORIZONTAL_FACING)));
        }
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(HORIZONTAL_FACING, pRot.rotate(pState.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(HORIZONTAL_FACING)));
    }

//    @Override
//    public RenderShape getRenderShape(BlockState pState) {
//        return pState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? RenderShape.MODEL : RenderShape.INVISIBLE;
//    }
}
