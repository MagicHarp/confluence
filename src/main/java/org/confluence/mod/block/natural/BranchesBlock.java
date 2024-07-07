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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class BranchesBlock extends PipeBlock implements CustomModel, CustomItemModel {
    private static final Block ground = ModBlocks.STONY_LOGS.get();

    public BranchesBlock() {
        super(0.3125f, BlockBehaviour.Properties.of().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).randomTicks());
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    public BlockState getStateForPlacement(BlockGetter level, BlockPos pos) {
        BlockState $$2 = level.getBlockState(pos.below());
        return this.defaultBlockState()
            .setValue(DOWN, $$2.is(this) || $$2.is(ground))
            .setValue(UP, $$2.is(this))
            .setValue(NORTH, $$2.is(this))
            .setValue(EAST, $$2.is(this))
            .setValue(SOUTH, $$2.is(this))
            .setValue(WEST, $$2.is(this));
    }

    @Override
    public BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos pCurrentPos, @NotNull BlockPos facingPos) {
        if (!state.canSurvive(level, pCurrentPos)) {
            level.destroyBlock(pCurrentPos, true);
            return Blocks.AIR.defaultBlockState();
        } else {
            BlockState $$6 = level.getBlockState(pCurrentPos.below());
            boolean $$7 = $$6.is(this) || $$6.is(ground);
            return state.setValue(DOWN, $$7);
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
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        int horizontalRange = 5; // 水平方向检测范围
        int verticalRange = 5; // 竖直方向检测范围

        for (int i = -horizontalRange; i <= horizontalRange; i++) {
            for (int j = -verticalRange; j <= verticalRange; j++) {
                for (int k = -horizontalRange; k <= horizontalRange; k++) {
                    BlockPos checkPos = pos.offset(i, j, k);
                    BlockState checkBlock = level.getBlockState(checkPos);
                    if (checkBlock.is(ground)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }
}


