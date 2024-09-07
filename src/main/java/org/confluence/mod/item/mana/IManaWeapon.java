package org.confluence.mod.item.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.misc.ModAttributes;

public interface IManaWeapon {
    default int calculateManaCost(ItemStack itemStack, int amount) {
        CompoundTag prefix = itemStack.getTagElement(PrefixProvider.KEY);
        if (prefix != null) {
            return (int) (amount + amount * prefix.getFloat("manaCost"));
        }
        return amount;
    }

    default int getAttackSpeed(LivingEntity living, int cooldown) {
        AttributeInstance attributeInstance = living.getAttribute(Attributes.ATTACK_SPEED);
        if (attributeInstance != null) return Math.max(cooldown - (int) (attributeInstance.getValue() / 3.0), 0);
        return cooldown;
    }

    default float getVelocity(LivingEntity living, float velocity) {
        AttributeInstance attributeInstance = living.getAttribute(ModAttributes.getRangedVelocity());
        if (attributeInstance != null) return velocity * (float) attributeInstance.getValue();
        return velocity;
    }

    static boolean isMagic(Player player, DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC) ||
            damageSource.is(DamageTypes.INDIRECT_MAGIC) ||
            player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IManaWeapon;
    }
}
