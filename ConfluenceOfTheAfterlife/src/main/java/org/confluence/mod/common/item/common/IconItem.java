package org.confluence.mod.common.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class IconItem extends Item {
    public IconItem() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }
}
