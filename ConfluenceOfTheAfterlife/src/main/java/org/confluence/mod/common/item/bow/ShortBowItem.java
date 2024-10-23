package org.confluence.mod.common.item.bow;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.init.ModDataComponentTypes;
import org.confluence.mod.mixinauxi.IAbstractArrow;
import org.jetbrains.annotations.NotNull;

public class ShortBowItem extends BowItem {
    public static final int MAX_DRAW_DURATION = 4; // 满蓄力时间为4 tick
    private final float baseDamage;

    public ShortBowItem(float baseDamage, int durability,ModRarity rarity) {
        super(new Properties().durability(durability)
                .component(ModDataComponentTypes.MOD_RARITY, rarity));
        this.baseDamage = baseDamage;
    }

    public float getShortPowerForTime(int pCharge) {
        float f = (float) pCharge / MAX_DRAW_DURATION;
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
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        arrow.setBaseDamage(baseDamage);
        return arrow;
    }

    public static void applyToArrow(ItemStack itemStack, AbstractArrow arrow) {
        if (itemStack.getItem() instanceof ShortBowItem) {
            ((IAbstractArrow) arrow).confluence$setShootFromShortBow(true);
        }
    }
}
