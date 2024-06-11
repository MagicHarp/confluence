package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.Image32x;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class PurpleClubberFishItem extends SwordItem implements Image32x {
    public PurpleClubberFishItem() {
        super(ModTiers.TITANIUM, 11, -3.5F, new Properties().rarity(ModRarity.BLUE));
    }


}
