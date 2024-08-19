
package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.item.sword.ISwordProjectile;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.EnchantedSwordShootingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class SwordProjectileShootingHandler {
    public static void handle(Minecraft minecraft, LocalPlayer localPlayer) {
        if (minecraft.gameMode == null || minecraft.gameMode.isDestroying() || !minecraft.options.keyAttack.isDown()) return;
        Item item = localPlayer.getMainHandItem().getItem();
        if (item instanceof ISwordProjectile swordProjectile && !localPlayer.getCooldowns().isOnCooldown(item)) {
            NetworkHandler.CHANNEL.sendToServer(new EnchantedSwordShootingPacketC2S());
            localPlayer.getCooldowns().addCooldown(localPlayer.getMainHandItem().getItem(), swordProjectile.getCooldown());
            localPlayer.swing(InteractionHand.MAIN_HAND);
        }
    }
}
