package org.confluence.mod.item.curio;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.curio.AbilityProvider;

public interface ILavaImmune {
    default int getLavaImmuneTicks() {
        return 140;
    }

    default void freshLavaImmuneTicks(LivingEntity living) {
        living.getCapability(AbilityProvider.ABILITY_CAPABILITY)
            .ifPresent(playerAbility -> playerAbility.freshLavaImmuneTicks(living));
    }
}
