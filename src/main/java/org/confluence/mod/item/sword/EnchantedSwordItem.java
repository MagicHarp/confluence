package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class EnchantedSwordItem extends SwordItem implements CustomItemModel {

    public EnchantedSwordItem() {
        super(ModTiers.TITANIUM, 7, -0.2F, new Properties().rarity(ModRarity.ORANGE));
    }
}
