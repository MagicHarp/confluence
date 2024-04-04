package org.confluence.mod.item.curio.combat;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;

import java.util.concurrent.atomic.AtomicBoolean;

public interface IFireImmune {
    default void freshFireImmune(LivingEntity living) {
        living.getCapability(PlayerAbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshFireImmune(living));
    }

    static boolean apply(LivingEntity living, DamageSource damageSource) {
        if (damageSource.is(DamageTypes.IN_FIRE) ||
            damageSource.is(DamageTypes.ON_FIRE) ||
            damageSource.is(DamageTypes.HOT_FLOOR) ||
            damageSource.is(DamageTypes.UNATTRIBUTED_FIREBALL) ||
            damageSource.is(DamageTypes.FIREBALL)
        ) {
            AtomicBoolean atomic = new AtomicBoolean();
            living.getCapability(PlayerAbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> atomic.set(playerAbility.isFireImmune()));
            return atomic.get();
        }
        return false;
    }
}
