package org.confluence.mod.item.bow;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.mixinauxiliary.IAbstractArrow;

public class ShortBowItem extends BowItem {
    public static final float FULL_POWER_TICKS = 4.0F;

    public ShortBowItem(Properties pProperties) {
        super(pProperties);
    }

    public float getShortPowerForTime(int pCharge) {
        float f = (float) pCharge / FULL_POWER_TICKS; // 满蓄力时间为4 tick
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public float getVelocityMultiplier() {
        return 2.0F;
    }

    public static void applyToArrow(ItemStack itemStack, AbstractArrow arrow) {
        if (itemStack.getItem() instanceof ShortBowItem) {
            ((IAbstractArrow) arrow).confluence$setShootFromShortBow(true);
        }
    }
}
