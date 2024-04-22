package org.confluence.mod.item.curio;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.ability.AbilityProvider;

public interface ILavaImmune {
    default int getLavaImmuneTicks() {
        return 140;
    }

    default void freshLavaInvulTicks(LivingEntity living) {
        living.getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshLavaImmuneTicks(living));
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.lava_immune");
}
