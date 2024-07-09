package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.ReversalImage32x;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class FalconBladeItem extends SwordItem implements ReversalImage32x {
    public FalconBladeItem() {
        super(ModTiers.TITANIUM, 3, -1.5F, new Properties().rarity(ModRarity.BLUE));
    }
}
