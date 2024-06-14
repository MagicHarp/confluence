package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.effect.ModEffects;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public interface IFallResistance {
    int getFallResistance();

    static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (!damageSource.is(DamageTypes.FALL) || living.hasEffect(ModEffects.STONED.get())) return amount;
        AtomicInteger atomic = new AtomicInteger();
        living.getCapability(AbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> atomic.set(playerAbility.getFallResistance()));
        int reduce = atomic.get();
        if (reduce < 0) return 0.0F;
        return Math.max(amount - reduce, 0.0F);
    }

    static boolean isInvul(LivingEntity living, DamageSource damageSource) {
        if (!damageSource.is(DamageTypes.FALL) || living.hasEffect(ModEffects.STONED.get())) return false;
        AtomicBoolean atomic = new AtomicBoolean();
        living.getCapability(AbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> atomic.set(playerAbility.getFallResistance() < 0));
        return atomic.get();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fall_resistance");
}
