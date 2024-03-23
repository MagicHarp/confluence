package org.confluence.mod.item.curio;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BaseCurioItem extends Item implements ICurioItem {
    public BaseCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public BaseCurioItem() {
        this(new Properties());
    }
}
