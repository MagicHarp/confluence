package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CoinPileBlock extends FallingBlock implements CustomModel, CustomItemModel {
    public static final BooleanProperty ISBASE = BooleanProperty.create("isbase");
    private static final IntegerProperty HEAPS = IntegerProperty.create("heaps", 1, 12);

    public CoinPileBlock() {
        super(Properties.of().sound(SoundType.AMETHYST));
        registerDefaultState(this.defaultBlockState().setValue(HEAPS, 1).setValue(ISBASE, true));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int heaps = state.getValue(HEAPS);
        if (heaps <= 3) {
            return Block.box(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
        } else if (heaps <= 6) {
            return Block.box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
        } else if (heaps <= 9) {
            return Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
        } else {
            return Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext UseContext) {
        return !UseContext.isSecondaryUseActive() && UseContext.getItemInHand().is(this.asItem()) && state.getValue(HEAPS) < 12 || super.canBeReplaced(state, UseContext);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState clickedBlockState = context.getLevel().getBlockState(context.getClickedPos());
        BlockState state = this.defaultBlockState();
        if (clickedBlockState.is(this)) {
            return clickedBlockState.setValue(HEAPS, Math.min(12, clickedBlockState.getValue(HEAPS) + 1));
        }

        BlockState clickedBlockBelowState = context.getLevel().getBlockState(context.getClickedPos().below());
        if (isCoinPileBlock(clickedBlockBelowState)) {
            state = state.setValue(ISBASE, false);
        } else {
            state = state.setValue(ISBASE, true);
        }
        return state;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockState Below = level.getBlockState(pos.below());
        return isCoinPileBlock(Below) || !Below.isAir();
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState blockstate, @NotNull LevelAccessor level, @NotNull BlockPos CurrentPos, @NotNull BlockPos facingPos) {
        level.scheduleTick(CurrentPos, this, this.getDelayAfterPlace());
        blockstate = level.getBlockState(CurrentPos.below());
        boolean isBaseBlock = !isCoinPileBlock(blockstate);
        state = state.setValue(ISBASE, isBaseBlock);
        return state;
    }

    private boolean isCoinPileBlock(BlockState blockState) {
        return blockState.is(this) || blockState.is(ModBlocks.COPPER_COIN_PILE.get()) || blockState.is(ModBlocks.SILVER_COIN_PILE.get()) ||
            blockState.is(ModBlocks.GOLDEN_COIN_PILE.get()) || blockState.is(ModBlocks.PLATINUM_COIN_PILE.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HEAPS, ISBASE);
    }

}
