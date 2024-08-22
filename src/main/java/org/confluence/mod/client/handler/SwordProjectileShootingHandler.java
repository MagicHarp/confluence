
package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.projectile.EnchantedSwordProjectile;
import org.confluence.mod.entity.projectile.IceBladeSwordProjectile;
import org.confluence.mod.item.sword.EnchantedSwordItem;
import org.confluence.mod.item.sword.ISwordProjectile;
import org.confluence.mod.item.sword.IceBladeSwordItem;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.SwordShootingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class SwordProjectileShootingHandler {
    public static void handle(Minecraft minecraft, LocalPlayer localPlayer) {
        if (minecraft.gameMode == null || minecraft.gameMode.isDestroying() || !minecraft.options.keyAttack.isDown()) return;
        Item item = localPlayer.getMainHandItem().getItem();
        if (item instanceof ISwordProjectile swordProjectile && !localPlayer.getCooldowns().isOnCooldown(item)) {
            if (item instanceof EnchantedSwordItem) {
                NetworkHandler.CHANNEL.sendToServer(new SwordShootingPacketC2S(new EnchantedSwordProjectile(ModEntities.ENCHANTED_SWORD_PROJECTILE.get(), localPlayer.level())));
            }
            if (item instanceof IceBladeSwordItem) {
                localPlayer.playSound(ModSoundEvents.FROZEN_ARROW.get(), 1.0F, 1.0F);
                NetworkHandler.CHANNEL.sendToServer(new SwordShootingPacketC2S(new IceBladeSwordProjectile(ModEntities.ICE_BLADE_SWORD_PROJECTILE.get(), localPlayer.level())));
            }
            localPlayer.getCooldowns().addCooldown(localPlayer.getMainHandItem().getItem(), swordProjectile.getCooldown());
            localPlayer.swing(InteractionHand.MAIN_HAND);
        }
    }
}
