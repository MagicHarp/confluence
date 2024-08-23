package org.confluence.mod.block.natural.spreadable;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MushroomGrassBlock extends CustomModelSpreadingBlock {
    public static final ColorPointLight.Template LIGHT = new ColorPointLight.Template(5, 0, 0, 1, 1);

    public MushroomGrassBlock() {
        super(Type.PURE, Properties.copy(Blocks.GRASS_BLOCK).lightLevel(value -> 5));
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!serverLevel.isAreaLoaded(blockPos, 3)) return;
        if (ISpreadable.isFullBlock(serverLevel, blockPos.above())) {
            serverLevel.setBlockAndUpdate(blockPos, Blocks.MUD.defaultBlockState());
        } else {
            super.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }
}
