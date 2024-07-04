package org.confluence.mod.block.natural.spreadable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.ThornBlock;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class SpreadingGrassBlock extends SpreadingBlock implements  CustomModel {
    public SpreadingGrassBlock(Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!serverLevel.isAreaLoaded(blockPos, 3)) return;
        BlockPos above = blockPos.above();
        if (ISpreadable.isFullBlock(serverLevel, above)) {
            serverLevel.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
        } else {
            ThornBlock thorn=switch(getType()){
                case CRIMSON -> ModBlocks.CRIMSON_THORN.get();
                case CORRUPT -> ModBlocks.CORRUPTION_THORN.get();
                default -> null;
            };
            if(thorn != null && randomSource.nextInt(10) == 0
                && serverLevel.getBlockState(above).isAir()
                && serverLevel.getBlockState(above.east()).isAir()
                && serverLevel.getBlockState(above.west()).isAir()
                && serverLevel.getBlockState(above.south()).isAir()
                && serverLevel.getBlockState(above.north()).isAir()
            ){
                serverLevel.setBlockAndUpdate(above, thorn.getStateForPlacement(serverLevel,above));
            }
            super.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }
}
