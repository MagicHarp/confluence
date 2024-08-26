package org.confluence.mod.item.bow;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.mixinauxiliary.IAbstractArrow;
import org.jetbrains.annotations.NotNull;

public class ShortBowItem extends BowItem implements CustomModel {
    public static final float FULL_POWER_TICKS = 4.0F; // 满蓄力时间为4 tick
    private final float baseDamage;

    public ShortBowItem(float baseDamage, Properties pProperties) {
        super(pProperties);
        this.baseDamage = baseDamage;
    }

    public float getShortPowerForTime(int pCharge) {
        float f = (float) pCharge / FULL_POWER_TICKS;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public float getVelocityMultiplier() {
        return 2.0F;
    }

    @Override
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow) {
        arrow.setBaseDamage(baseDamage);
        return arrow;
    }

    public static void applyToArrow(ItemStack itemStack, AbstractArrow arrow) {
        if (itemStack.getItem() instanceof ShortBowItem) {
            ((IAbstractArrow) arrow).confluence$setShootFromShortBow(true);
        }
    }
}
