package org.confluence.mod.item.mana;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.effect.ModEffects;

public interface IManaWeapon {
    default int getManaCost(ItemStack itemStack, int amount) {
        CompoundTag prefix = itemStack.getTagElement(PrefixProvider.KEY);
        if (prefix != null) amount *= (1 + prefix.getDouble("manaCost"));
        return amount;
    }

    default int getAttackSpeed(ItemStack itemStack, int amount) {
        CompoundTag prefix = itemStack.getTagElement(PrefixProvider.KEY);
        if (prefix != null) amount *= (1 - prefix.getDouble("attackSpeed"));
        return amount;
    }

    static float apply(DamageSource damageSource, float amount) {
        if (damageSource.getEntity() instanceof Player player && isMagic(player, damageSource)) {
            AtomicDouble atomic = new AtomicDouble(amount);
            player.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage ->
                atomic.set(amount * manaStorage.getMagicAttackBonus()));
            float actually = atomic.floatValue();
            if (player.hasEffect(ModEffects.MAGIC_POWER.get())) {
                actually *= 1.2F;
            }
            return actually;
        }
        return amount;
    }

    static boolean isMagic(Player player, DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC) || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IManaWeapon;
    }
}
