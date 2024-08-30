package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class PalmSaplingBlock extends SaplingBlock implements CustomModel {
    public PalmSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties) {
        super(pTreeGrower, pProperties);
    }
    @Override
    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos below = pPos.below();
        BlockState blockBelow = pLevel.getBlockState(below);
        if (blockBelow.is(Blocks.SAND)|| blockBelow.is(Blocks.RED_SAND)
            || blockBelow.is(Blocks.GRASS_BLOCK)) {
            return true;
        }
        return false;
    }
}
