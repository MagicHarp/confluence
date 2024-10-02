package org.confluence.mod.item.axe;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.datagen.limit.Image24x;

public class BigAxeItem extends AxeItem implements Image24x {
    public BigAxeItem(Tier tier, float rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public BigAxeItem(Tier tier, float rawDamage, float rawSpeed, Properties properties) {
        super(tier, rawDamage - tier.getAttackDamageBonus() - 1, rawSpeed - 4, properties);
    }
}
