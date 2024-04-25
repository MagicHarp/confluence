package org.confluence.mod.item.mana;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;

import java.util.Optional;

public interface IManaWeapon {
    default int getManaCost(ItemStack itemStack, int amount) {
        Optional<ItemPrefix> optional = PrefixProvider.getPrefix(itemStack);
        if (optional.isPresent()) amount *= (1 + optional.get().manaCost);
        return amount;
    }

    default float getVelocity(ItemStack itemStack, float velocity) {
        Optional<ItemPrefix> optional = PrefixProvider.getPrefix(itemStack);
        if (optional.isPresent()) velocity *= (1 + optional.get().velocity);
        return velocity;
    }

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
        return damageSource.is(DamageTypes.MAGIC) || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IManaWeapon;
    }
}
