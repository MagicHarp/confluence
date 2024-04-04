package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;

public interface IInvulnerableTime {
    int getTime();

    default void freshInvulnerableTime(LivingEntity living) {
        living.getCapability(PlayerAbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshInvulnerableTime(living));
    }
}
