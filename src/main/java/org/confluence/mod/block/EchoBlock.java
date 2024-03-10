package org.confluence.mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.confluence.mod.datagen.CustomItemModel;
import org.confluence.mod.datagen.CustomModel;
import org.jetbrains.annotations.NotNull;

public class EchoBlock extends Block implements CustomModel, CustomItemModel {
    public static final BooleanProperty VISIBLE = BooleanProperty.create("visible");

    public EchoBlock() {
        super(BlockBehaviour.Properties.of().noCollission());
        registerDefaultState(stateDefinition.any().setValue(VISIBLE, false));
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VISIBLE);
    }

    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return 1.0F;
    }
}
