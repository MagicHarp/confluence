package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.block.UpperLowerCombineBlock;
import org.confluence.mod.datagen.limit.CustomModel;

public class BaseStatueBlock extends UpperLowerCombineBlock implements CustomModel {
    public BaseStatueBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }
}
