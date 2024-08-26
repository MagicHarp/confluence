package org.confluence.mod.item.sword;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.entity.projectile.SwordProjectile;
import org.confluence.mod.misc.ModAttributes;

public interface ISwordProjectile { // 剑气
    int getCooldown();

    SoundEvent getSound();

    SwordProjectile getProjectile(Player player);

    float getBaseVelocity();

    default float getVelocity(LivingEntity living) {
        float velocity = getBaseVelocity();
        AttributeInstance attributeInstance = living.getAttribute(ModAttributes.getRangedVelocity());
        if (attributeInstance != null) velocity *= (float) attributeInstance.getValue();
        return velocity;
    }

    default int getAttackSpeed(LivingEntity living) {
        int cooldown = getCooldown();
        AttributeInstance attributeInstance = living.getAttribute(Attributes.ATTACK_SPEED);
        if (attributeInstance != null) return Math.max((int) (cooldown - cooldown * attributeInstance.getValue()), 0);
        return cooldown;
    }
}
