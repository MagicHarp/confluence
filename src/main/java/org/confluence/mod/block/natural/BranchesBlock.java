package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.EnumMap;

public class BranchesBlock extends PipeBlock implements CustomModel, CustomItemModel {
    private static final Block ground = ModBlocks.STONY_LOGS.get();
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = new EnumMap<>(Direction.class);

    static {
        PROPERTY_BY_DIRECTION.put(Direction.NORTH, NORTH);
        PROPERTY_BY_DIRECTION.put(Direction.EAST, EAST);
        PROPERTY_BY_DIRECTION.put(Direction.SOUTH, SOUTH);
        PROPERTY_BY_DIRECTION.put(Direction.WEST, WEST);
        PROPERTY_BY_DIRECTION.put(Direction.UP, UP);
        PROPERTY_BY_DIRECTION.put(Direction.DOWN, DOWN);
    }

    public BranchesBlock() {
        super(0.3125f, BlockBehaviour.Properties.of().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).randomTicks());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockGetter level = context.getLevel();
        Direction placedOn = context.getClickedFace().getOpposite();

        BlockState state = this.defaultBlockState().setValue(PROPERTY_BY_DIRECTION.get(placedOn), true);

        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.is(this)) {
                boolean adjacentDown = adjacentState.getValue(DOWN);
                if (direction == Direction.UP || direction == Direction.DOWN || !state.getValue(DOWN) || !adjacentDown) {
                    state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), true);
                }
            } else if (adjacentState.is(ground) && (direction == Direction.UP || direction == Direction.DOWN)) {
                state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), true);
            }
        }

        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            boolean isConnected = facingState.is(this) || facingState.is(ground);
            if (facing == Direction.UP || facing == Direction.DOWN || !state.getValue(DOWN) || (facingState.is(this) && !facingState.getValue(DOWN))) {
                state = state.setValue(PROPERTY_BY_DIRECTION.get(facing), isConnected);
            } else {
                state = state.setValue(PROPERTY_BY_DIRECTION.get(facing), false);
            }

            return state;
        }
    }

    @Override
    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            dropResources(state, level, pos);
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        // 获取当前方块下方的方块状态
        BlockState stateBelow = pLevel.getBlockState(pPos.below());

        // 检查当前方块下方是否是同类方块或ground
        if (stateBelow.is(this) || stateBelow.is(ground)) {
            return true;
        }

        // 遍历所有水平方向（北、南、东、西）
        Iterator<Direction> horizontalDirections = Direction.Plane.HORIZONTAL.iterator();

        // 循环检查水平相邻的方块
        while (horizontalDirections.hasNext()) {
            Direction direction = horizontalDirections.next();
            BlockPos posAtSide = pPos.relative(direction);
            BlockState stateAtSide = pLevel.getBlockState(posAtSide);

            // 检查相邻的方块是否是同类方块或ground
            if (stateAtSide.is(this) || stateAtSide.is(ground)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }
}
