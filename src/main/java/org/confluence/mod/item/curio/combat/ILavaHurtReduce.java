package org.confluence.mod.item.curio.combat;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.curio.AbilityProvider;

import java.util.concurrent.atomic.AtomicBoolean;

public interface ILavaHurtReduce {
    default void freshLavaReduce(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshLavaHurtReduce(living));
    }

    static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.LAVA)) {
            AtomicBoolean atomic = new AtomicBoolean();
            living.getCapability(AbilityProvider.ABILITY_CAPABILITY)
                .ifPresent(playerAbility -> atomic.set(playerAbility.isLavaHurtReduce()));
            if (atomic.get()) {
                living.setSecondsOnFire(7);
                return amount * 0.5F;
            }
        }
        return amount;
    }
}
