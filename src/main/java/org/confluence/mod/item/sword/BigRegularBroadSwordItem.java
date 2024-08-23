package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.datagen.limit.Image24x;

public class BigRegularBroadSwordItem extends SwordItem implements Image24x {
    public BigRegularBroadSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
