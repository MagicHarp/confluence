package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.misc.ModRarity;

public class BaseItem extends Item {
    public BaseItem() {
        super(new Properties().rarity(ModRarity.GRAY));
    }

    public BaseItem(Rarity rarity) {
        super(new Properties().rarity(rarity));
    }
}
