package org.confluence.mod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BeamBlock extends Block {
    public BeamBlock(Properties properties) {
        super(properties);
    }

    public BeamBlock() {
        this(BlockBehaviour.Properties.of());
    }
}
