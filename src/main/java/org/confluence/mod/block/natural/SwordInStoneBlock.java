package org.confluence.mod.block.natural;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.confluence.mod.block.functional.StateProperties;

public class SwordInStoneBlock extends Block {
    StateProperties.SwordType swordType;

    public SwordInStoneBlock(StateProperties.SwordType swordType) {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak());
        this.swordType = swordType;
    }
}
