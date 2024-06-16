package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.util.CuriosUtils;

import java.util.Optional;

public interface IProjectileAttack {
    float getProjectileBonus();

    static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        Optional<IProjectileAttack> curio;
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE) && (curio = CuriosUtils.findCurio(living, IProjectileAttack.class)).isPresent()) {
            return amount * (1.0F + curio.get().getProjectileBonus());
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
