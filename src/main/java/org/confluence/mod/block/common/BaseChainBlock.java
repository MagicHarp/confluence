package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;

public class BaseChainBlock extends ChainBlock implements CustomModel, CustomItemModel {
    public BaseChainBlock(MapColor mapColor) {
        super(BlockBehaviour.Properties.copy(Blocks.CHAIN).mapColor(mapColor));
    }
}
