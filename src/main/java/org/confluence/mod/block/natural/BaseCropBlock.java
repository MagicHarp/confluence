package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public abstract class BaseCropBlock extends CropBlock implements CustomModel, CustomItemModel {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);
    public static final int MAX_AGE = 7;

    public BaseCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected abstract @NotNull ItemLike getBaseSeedId();

    @Override
    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    public abstract Set<Block> getCanPlaceBlocks();

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return getCanPlaceBlocks().contains(pState.getBlock());
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockstate, LevelReader worldIn, BlockPos pos){
        BlockPos blockpos = pos.below();
        BlockState groundState = worldIn.getBlockState(blockpos);
        return this.mayPlaceOn(groundState, worldIn, blockpos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        if (getAge(pState) == getMaxAge()){
            ModUtils.createItemEntity(getCropDrops(), pPos.getX(), pPos.getY(), pPos.getZ(), pLevel);
            ModUtils.createItemEntity(new ItemStack(getBaseSeedId(), 2), pPos.getX(), pPos.getY(), pPos.getZ(), pLevel);
        }
    }

    public abstract List<ItemStack> getCropDrops();
}
