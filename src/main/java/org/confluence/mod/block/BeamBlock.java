package org.confluence.mod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.datagen.ICubeTop;

public class BeamBlock extends Block implements ICubeTop {
    public BeamBlock(Properties properties) {
        super(properties);
    }

    public BeamBlock() {
        this(BlockBehaviour.Properties.of());
    }
}
