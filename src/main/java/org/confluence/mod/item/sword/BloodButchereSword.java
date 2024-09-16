package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.Image24x;

public class BloodButchereSword extends SwordItem implements CustomItemModel, Image24x {
    public BloodButchereSword(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, rawDamage, rawSpeed, properties);
    }
}