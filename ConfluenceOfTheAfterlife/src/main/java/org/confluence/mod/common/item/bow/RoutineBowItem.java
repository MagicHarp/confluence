package org.confluence.mod.common.item.bow;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.terra_curio.common.component.ModRarity;
import org.confluence.mod.terra_curio.common.init.ModDataComponentTypes;


public class RoutineBowItem extends BowItem {
    private final float baseDamage;

    public RoutineBowItem(float baseDamage, int durability, ModRarity rarity) {
        super(new Properties().durability(durability)
                .component(ModDataComponentTypes.MOD_RARITY, rarity));
        this.baseDamage = baseDamage;
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        arrow.setBaseDamage(baseDamage);
        return arrow;
    }

}
