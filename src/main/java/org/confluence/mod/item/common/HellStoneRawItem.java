package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class HellStoneRawItem extends Item {

    public HellStoneRawItem(Rarity rarity) {
        super(new Properties().rarity(rarity).fireResistant());
    }
}
