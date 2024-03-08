package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.datagen.CustomModel;

public class IconItem extends Item implements CustomModel {
    public IconItem() {
        super(new Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(1));
    }
}
