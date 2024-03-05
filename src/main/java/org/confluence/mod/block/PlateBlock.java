package org.confluence.mod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.datagen.ICubeTop;

public class PlateBlock extends Block implements ICubeTop {
    public PlateBlock(Properties properties) {
        super(properties);
    }

    public PlateBlock() {
        this(BlockBehaviour.Properties.of());
    }
}
