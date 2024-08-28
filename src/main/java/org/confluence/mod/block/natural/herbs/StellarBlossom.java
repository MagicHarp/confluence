package org.confluence.mod.block.natural.herbs;

import net.minecraft.server.level.ServerLevel;
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
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StellarBlossom extends BaseHerbBlock {
    public static final IntegerProperty PROP_LIGHT = IntegerProperty.create("level", BRIGHTNESS, 7);

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

    //todo 逻辑
}
