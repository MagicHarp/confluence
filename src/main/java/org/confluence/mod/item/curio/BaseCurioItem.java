package org.confluence.mod.item.curio;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BaseCurioItem extends Item implements ICurioItem {
    public BaseCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public BaseCurioItem() {
        this(new Properties());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
