package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class HellStoneItem extends Item {

    public HellStoneItem(Rarity rarity) {
        super(new Properties().rarity(rarity).fireResistant());
    }
}
