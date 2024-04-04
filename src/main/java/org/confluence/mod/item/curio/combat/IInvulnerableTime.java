package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.curio.AbilityProvider;

public interface IInvulnerableTime {
    int getTime();

    default void freshInvulnerableTime(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshInvulnerableTime(living));
    }
}