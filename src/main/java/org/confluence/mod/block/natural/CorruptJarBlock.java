package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.Nullable;


public class CorruptJarBlock extends Block implements CustomModel {

    public CorruptJarBlock(Properties pProperties) {
        super(pProperties);
    }
    public CorruptJarBlock() {
        this(Properties.of());
    }
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext placeContext){
        return defaultBlockState().setValue(FACING,placeContext.getHorizontalDirection().getOpposite());
    }
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14));
            case NORTH -> Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14));
            case EAST -> Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14));
            case WEST -> Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14));
        };
    }






    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


}
