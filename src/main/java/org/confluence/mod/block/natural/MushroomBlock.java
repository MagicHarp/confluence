package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class MushroomBlock extends BushBlock implements CustomModel {
    private final Block survive;
    private static final VoxelShape SHAPE=Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
    public MushroomBlock(Block surviveBlock){
        super(Properties.copy(Blocks.DANDELION));
        this.survive = surviveBlock;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos){
        return pLevel.getBlockState(pPos.below()).is(survive);
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext){
        return SHAPE;
    }
}
