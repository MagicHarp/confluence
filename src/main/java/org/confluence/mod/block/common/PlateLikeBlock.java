package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.datagen.ICubeTop;

public class PlateLikeBlock extends Block implements ICubeTop {
    public PlateLikeBlock(Properties properties) {
        super(properties);
    }

    public PlateLikeBlock() {
        this(BlockBehaviour.Properties.of());
    }
}
