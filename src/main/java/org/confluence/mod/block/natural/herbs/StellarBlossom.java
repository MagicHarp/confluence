package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StellarBlossom extends BaseHerbBlock {
    public static final IntegerProperty PROP_LIGHT = IntegerProperty.create("level", 0, 7);
    public static final int MAX_AGE = 7;

    public StellarBlossom(){
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks().lightLevel(value -> value.getValue(AGE) == MAX_AGE ? value.getValue(PROP_LIGHT) : 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(PROP_LIGHT);
    }

    @Override
    public boolean canBloom(ServerLevel world, BlockState state){
        return true;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }
        return ModUtils.getTicker(pBlockEntityType, ModBlocks.HERBS_ENTITY.get(), (level, blockPos, blockState, e) -> {
            super.getTicker(level, blockState, pBlockEntityType);
        });
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        int age = getAge(pState);
        if (age == this.getMaxAge()){
            ModUtils.createItemEntity(new ItemStack(ModItems.STELLAR_BLOSSOM_SEED.get()), pPos.getCenter(), pLevel);
            ModUtils.createItemEntity(new ItemStack(Materials.STAR_PETALS.get(), pLevel.random.nextInt(1, 3)), pPos.getCenter(), pLevel);
        }
    }
}
