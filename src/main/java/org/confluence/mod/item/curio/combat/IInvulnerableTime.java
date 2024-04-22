package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.ability.AbilityProvider;

public interface IInvulnerableTime {
    int getTime();

    default void freshInvulnerableTime(LivingEntity living) {
        living.getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshInvulnerableTime(living));
    }
}
