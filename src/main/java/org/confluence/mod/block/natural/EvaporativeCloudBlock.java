package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class EvaporativeCloudBlock extends Block {
    private static final IntegerProperty SURVIVE_TIME = IntegerProperty.create("survive_time", 0, 1);

    public EvaporativeCloudBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.SNOW).randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(SURVIVE_TIME, 0));
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        int surviveTime = state.getValue(SURVIVE_TIME);
        if (surviveTime < 1) {
            level.setBlock(pos, state.setValue(SURVIVE_TIME, surviveTime + 1), Block.UPDATE_CLIENTS);
        } else if (surviveTime == 1) {
            level.removeBlock(pos, false);
        }
    }

    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SURVIVE_TIME);
    }
}
