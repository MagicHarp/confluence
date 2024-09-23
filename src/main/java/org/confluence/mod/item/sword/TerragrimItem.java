package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class TerragrimItem extends SwordItem {
    public TerragrimItem() {
        super(ModTiers.TITANIUM, 3, 5.2F, new Properties().rarity(ModRarity.ORANGE));
    }


}
