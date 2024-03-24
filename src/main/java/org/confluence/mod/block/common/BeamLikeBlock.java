package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.datagen.limit.ICubeTop;

public class BeamLikeBlock extends Block implements ICubeTop {
    public BeamLikeBlock(Properties properties) {
        super(properties);
    }

    public BeamLikeBlock() {
        this(BlockBehaviour.Properties.of());
    }
}
