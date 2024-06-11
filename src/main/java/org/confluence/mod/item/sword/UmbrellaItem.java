package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;


public class UmbrellaItem extends SwordItem implements  CustomModel {
    public UmbrellaItem() {
        super(ModTiers.TITANIUM, 0, -2.4F, new Properties().rarity(ModRarity.BLUE));
    }
}