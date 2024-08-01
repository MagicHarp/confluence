package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SandLayerBlock extends Block implements CustomModel, CustomItemModel {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
        Shapes.empty(), Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    public SandLayerBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.SAND));
        this.registerDefaultState(this.defaultBlockState().setValue(LAYERS, 1));
    }

    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS) - 1];
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext UseContext) {
        int StackLayers = state.getValue(LAYERS);
        if (UseContext.getItemInHand().is(this.asItem()) && StackLayers < 8) {
            if (UseContext.replacingClickedOnBlock()) {
                return UseContext.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return StackLayers == 1;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState $$1 = context.getLevel().getBlockState(context.getClickedPos());
        if ($$1.is(this)) {
            int StackLayers = $$1.getValue(LAYERS);
            return $$1.setValue(LAYERS, Math.min(8, StackLayers + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockState Below = level.getBlockState(pos.below());
        return Below.is(this) || !Below.isAir();
    }

    @Override
    public BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos CurrentPos, @NotNull BlockPos facingPos) {
        return !state.canSurvive(level, CurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, CurrentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }
}
