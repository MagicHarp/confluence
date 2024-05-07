package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class SpreadingGrassBlock extends SpreadingBlock implements CustomModel {
    public SpreadingGrassBlock(Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!serverLevel.isAreaLoaded(blockPos, 3)) return;
        if (ISpreadable.isFullBlock(serverLevel, blockPos.above())) {
            serverLevel.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
        } else {
            super.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }
}
