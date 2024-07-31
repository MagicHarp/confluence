package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class CoinPileBlock extends FallingBlock implements CustomModel, CustomItemModel {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    private static final IntegerProperty HEAPS = IntegerProperty.create("heaps", 1, 13);
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = new EnumMap<>(Direction.class);

    static {
        PROPERTY_BY_DIRECTION.put(Direction.NORTH, NORTH);
    }

    public CoinPileBlock() {
        super(Properties.of().sound(SoundType.AMETHYST));
        registerDefaultState(this.defaultBlockState().setValue(HEAPS, 1).setValue(WATERLOGGED, true).setValue(NORTH, false));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int heaps = state.getValue(HEAPS);
        if (heaps <= 3){
           return Block.box(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
        }else if(heaps <= 6){
            return Block.box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
        }else if (heaps <= 9){
            return Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
        }else {
            return Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext UseContext) {
        return !UseContext.isSecondaryUseActive() && UseContext.getItemInHand().is(this.asItem()) && state.getValue(HEAPS) < 13 || super.canBeReplaced(state, UseContext);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState clickedBlockState = context.getLevel().getBlockState(context.getClickedPos());
        if (clickedBlockState.is(this)) {
            return clickedBlockState.setValue(HEAPS, Math.min(13, clickedBlockState.getValue(HEAPS) + 1));
        }

        BlockState clickedBlockBelowState = context.getLevel().getBlockState(context.getClickedPos().below());
        if (clickedBlockBelowState.is(this)) {
            BlockState state = this.defaultBlockState();
            state = state.setValue(PROPERTY_BY_DIRECTION.get(Direction.NORTH), true);

            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
            if (fluidState.getType() == Fluids.WATER) {
                state = state.setValue(WATERLOGGED, true);
            }

            return state;
        }
        return this.defaultBlockState();
    }

        @Override
        public boolean canSurvive (@NotNull BlockState state, LevelReader level, BlockPos pos){
            BlockState Below = level.getBlockState(pos.below());
            return Below.is(this) || !Below.isAir();
        }

        @Override
        public @NotNull BlockState updateShape (BlockState state, @NotNull Direction facing, @NotNull BlockState
        facingState, @NotNull LevelAccessor level, @NotNull BlockPos CurrentPos, @NotNull BlockPos facingPos){
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(CurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
            return super.updateShape(state, facing, facingState, level, CurrentPos, facingPos);
        }

        @Override
        protected void createBlockStateDefinition (StateDefinition.Builder < Block, BlockState > builder){
            builder.add(HEAPS, WATERLOGGED, NORTH);
        }
}
