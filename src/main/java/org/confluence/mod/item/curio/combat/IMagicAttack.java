package org.confluence.mod.item.curio.combat;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.capability.ability.AbilityProvider;

public interface IMagicAttack {
    double getMagicBonus();

    static float apply(DamageSource damageSource, float amount) {
        if (damageSource.getEntity() instanceof Player player && isMagic(damageSource)) {
            AtomicDouble atomic = new AtomicDouble(amount);
            player.getCapability(AbilityProvider.CAPABILITY).ifPresent(manaStorage ->
                atomic.set(amount * manaStorage.getMagicAttackBonus()));
            return atomic.floatValue();
        }
        return amount;
    }

    static boolean isMagic(DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC);
    }
}
