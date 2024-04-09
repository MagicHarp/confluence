package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;

public interface IAggroAttach {
    int getAggro();

    default void freshAggro(LivingEntity living) {
        living.getCapability(PlayerAbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshAggro(living));
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.aggro_attach");
}
