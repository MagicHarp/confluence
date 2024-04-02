package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.curio.AbilityProvider;

public interface IFallResistance {
    int getFallResistance();

    default void freshFallResistance(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            playerAbility.freshFallResistance(living);
        });
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fall_resistance");
}
