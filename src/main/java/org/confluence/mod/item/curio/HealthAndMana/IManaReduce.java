package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.mana.ManaProvider;

public interface IManaReduce {
    double getManaReduce();

    default void freshManaReduce(LivingEntity living) {
        living.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> manaStorage.freshExtractRatio(living));
    }
}
