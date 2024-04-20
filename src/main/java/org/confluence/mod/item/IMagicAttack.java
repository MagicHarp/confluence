package org.confluence.mod.item;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.capability.mana.ManaProvider;

public interface IMagicAttack {
    /**
     * Only for BaseCurioItem
     */
    default double getMagicBonus() {
        return 0.0;
    }

    /**
     * Only for BaseCurioItem
     */
    default void freshMagicAttackBonus(LivingEntity living) {
        living.getCapability(ManaProvider.CAPABILITY)
            .ifPresent(manaStorage -> manaStorage.freshMagicAttackBonus(living));
    }

    /**
     * Only for melee attack item
     */
    static float apply(DamageSource damageSource, float amount) {
        if (damageSource.getEntity() instanceof Player player && isMagic(player, damageSource)) {
            AtomicDouble atomic = new AtomicDouble(amount);
            player.getCapability(ManaProvider.CAPABILITY)
                .ifPresent(manaStorage -> atomic.set(amount * manaStorage.getMagicAttackBonus()));
            return atomic.floatValue();
        }
        return amount;
    }

    static boolean isMagic(Player player, DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC) || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IMagicAttack;
    }
}
