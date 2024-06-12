package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.Image32x;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class BatBatItem extends SwordItem implements Image32x {
    public BatBatItem() {
        super(ModTiers.TITANIUM, 12, -3.7F, new Properties().rarity(ModRarity.ORANGE));
    }
    //击中敌对怪物回为玩家回复一点血量
}
