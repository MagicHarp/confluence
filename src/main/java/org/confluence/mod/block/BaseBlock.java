package org.confluence.mod.block;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class BaseBlock extends Block {
    public BaseBlock() {
        super(BlockBehaviour.Properties.of());
    }

    public static class Item extends BlockItem {
        private String descriptionId;

        public Item(Block p_40565_, Properties p_40566_) {
            super(p_40565_, p_40566_);
        }

        @Override
        public @NotNull String getDescriptionId() {
            if (descriptionId == null) {
                this.descriptionId = Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
            }
            return descriptionId;
        }
    }
}
