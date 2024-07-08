package org.confluence.mod.block.natural;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import org.confluence.mod.block.functional.StateProperties;

public class SwordInStoneBlock extends Block {
    public static final EnumProperty<StateProperties.SwordType> swordType = EnumProperty.create("sword_type", StateProperties.SwordType.class);

    public SwordInStoneBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak());
        this.registerDefaultState(this.getStateDefinition().any().setValue(swordType, StateProperties.SwordType.Null));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(swordType);
    }
}
