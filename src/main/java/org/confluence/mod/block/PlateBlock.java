package org.confluence.mod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PlateBlock extends Block {
    public PlateBlock(Properties properties) {
        super(properties);
    }

    public PlateBlock() {
        this(BlockBehaviour.Properties.of());
    }
}
