package org.confluence.mod.item.common;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class MushroomItem extends BlockItem implements CustomModel {
    public MushroomItem(Block pBlock){
        super(pBlock, new Item.Properties());
    }

    @Override
    protected boolean canPlace(@NotNull BlockPlaceContext pContext, @NotNull BlockState pState){
        return false;
    }
}
