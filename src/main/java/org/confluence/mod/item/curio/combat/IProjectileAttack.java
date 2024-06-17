package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.util.CuriosUtils;

public interface IProjectileAttack {
    float getProjectileBonus();

    static float apply(DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE) && damageSource.getEntity() instanceof LivingEntity attacker) {
            float bonus = (float) CuriosUtils.calculateValue(attacker, IProjectileAttack.class, IProjectileAttack::getProjectileBonus, 1.0);
            return amount * bonus;
        }
        return amount;
    }

    default Component getComponent() {
        return Component.translatable(
            "curios.tooltip.projectile_attack",
            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getProjectileBonus() * 100.0)
        );
    }
}
