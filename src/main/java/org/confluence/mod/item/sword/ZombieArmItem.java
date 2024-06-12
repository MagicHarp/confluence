package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class ZombieArmItem extends SwordItem  {
    public ZombieArmItem() {
        super(ModTiers.TITANIUM, 3, -1.4F, new Properties().rarity(ModRarity.WHITE));
    }
}
