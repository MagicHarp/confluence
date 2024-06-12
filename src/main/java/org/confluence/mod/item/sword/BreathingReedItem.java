package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;

public class BreathingReedItem extends SwordItem {
    public BreathingReedItem() {
        super(ModTiers.TITANIUM, 0, -2.4F, new Properties().rarity(ModRarity.BLUE));
    }
}
//手持着时，芦苇呼吸管会出现在玩家上方，呼吸计的消耗速度降低，并且溺水时失去生命值的速度减半