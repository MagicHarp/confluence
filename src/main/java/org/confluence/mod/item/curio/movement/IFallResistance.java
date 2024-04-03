package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.curio.AbilityProvider;

import java.util.concurrent.atomic.AtomicInteger;

public interface IFallResistance {
    int getFallResistance();

    default void freshFallResistance(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshFallResistance(living));
    }

    static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (!damageSource.is(DamageTypes.FALL)) return amount;
        AtomicInteger atomic = new AtomicInteger();
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY)
            .ifPresent(playerAbility -> atomic.set(playerAbility.getFallResistance()));
        int reduce = atomic.get();
        if (reduce < 0) return 0.0F;
        return Math.min(amount - reduce, 0.0F);
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fall_resistance");
}
