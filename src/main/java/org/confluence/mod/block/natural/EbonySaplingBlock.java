package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class EbonySaplingBlock extends SaplingBlock implements CustomModel {
    public EbonySaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties) {
        super(pTreeGrower, pProperties);
    }
    @Override
    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos below = pPos.below();
        BlockState blockBelow = pLevel.getBlockState(below);
        if (blockBelow.is(ModBlocks.CORRUPT_GRASS_BLOCK.get())) {
            return true;
        }
        return false;
    }
}