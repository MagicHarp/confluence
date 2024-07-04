package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class JungleHiveBlock extends Block {

    public JungleHiveBlock() {
        super(BlockBehaviour.Properties.of());
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState blockState) {
        if (level instanceof ServerLevel) {
            ((ServerLevel) level).setBlockAndUpdate(pos, ModBlocks.HONEY.get().defaultBlockState());
        }
    }
}