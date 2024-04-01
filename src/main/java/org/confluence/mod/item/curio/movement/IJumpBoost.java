package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.curio.AbilityProvider;

public interface IJumpBoost {
    double getBoost();

    default void freshJumpBoost(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            playerAbility.freshJumpBoost(living);
        });
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.jump_boost");
}
