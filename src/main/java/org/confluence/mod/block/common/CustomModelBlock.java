package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.datagen.limit.CustomModel;

public class CustomModelBlock extends Block implements CustomModel {
    public CustomModelBlock() {
        super(BlockBehaviour.Properties.of());
    }
}
