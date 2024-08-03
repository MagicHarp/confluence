package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class EvaporativeCloudBlock extends Block {
    private static final BooleanProperty NeighborBlockState = BooleanProperty.create("neighbor_block_state");

    public EvaporativeCloudBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.SNOW).randomTicks());
        registerDefaultState(this.defaultBlockState().setValue(NeighborBlockState, false));
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.getBlock() == Blocks.WATER) {
                level.setBlockAndUpdate(neighborPos, Blocks.WATER.defaultBlockState());
                state =state.setValue(NeighborBlockState,true);
                level.setBlockAndUpdate(pos,state);
            }
        }
    }

    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NeighborBlockState);
    }
}
