package org.confluence.mod.item.pickaxe;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class BasePickaxeItem extends PickaxeItem {
    public BasePickaxeItem(Tier tier, int rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public BasePickaxeItem(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, (int) (rawDamage - tier.getAttackDamageBonus() - 1), rawSpeed - 4, properties);
    }
}
