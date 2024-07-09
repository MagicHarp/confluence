package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.ReversalImage32x;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class BoneSwordItem extends SwordItem implements ReversalImage32x {
    public BoneSwordItem() {
        super(ModTiers.TITANIUM, 5, -0.2F, new Properties().rarity(ModRarity.ORANGE));
    }
}
