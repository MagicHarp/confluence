package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.capability.mana.ManaProvider;

public interface IMagicAttack {
    double getMagicBonus();

    default void freshMagicAttackBonus(LivingEntity living) {
        living.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> manaStorage.freshMagicAttackBonus(living));
    }
}
