package org.confluence.mod.item.axe;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class BaseAxeItem extends AxeItem {
    public BaseAxeItem(Tier tier, float rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public BaseAxeItem(Tier tier, float rawDamage, float rawSpeed, Properties properties) {
        super(tier, rawDamage - tier.getAttackDamageBonus() - 1, rawSpeed - 4, properties);
    }
}
