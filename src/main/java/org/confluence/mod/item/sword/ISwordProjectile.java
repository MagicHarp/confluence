package org.confluence.mod.item.sword;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.entity.projectile.SwordProjectile;

public interface ISwordProjectile { // 剑气
    int getCooldown();

    SoundEvent getSound();

    SwordProjectile getProjectile(Player player);

    float getBaseVelocity();

    default float getVelocity(ItemStack itemStack) {
        return PrefixProvider.getVelocity(itemStack, getBaseVelocity());
    }

    default int getAttackSpeed(ItemStack itemStack) {
        return PrefixProvider.getAttackSpeed(itemStack, getCooldown());
    }
}
