package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import org.confluence.mod.item.ModRarity;

public class BaseItem extends Item {
    public BaseItem() {
        super(new Properties().rarity(ModRarity.GRAY));
    }
}
