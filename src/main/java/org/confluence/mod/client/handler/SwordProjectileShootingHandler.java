
package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.item.sword.ISwordProjectile;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.SwordShootingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class SwordProjectileShootingHandler {
    public static void handle(Minecraft minecraft, LocalPlayer localPlayer) {
        if (minecraft.gameMode == null || minecraft.gameMode.isDestroying() || !minecraft.options.keyAttack.isDown()) {
            return;
        }
        ItemStack mainHandItem = localPlayer.getMainHandItem();
        Item item = mainHandItem.getItem();
        if (item instanceof ISwordProjectile swordProjectile && !localPlayer.getCooldowns().isOnCooldown(item)) {
            NetworkHandler.CHANNEL.sendToServer(new SwordShootingPacketC2S(item));
            localPlayer.getCooldowns().addCooldown(mainHandItem.getItem(), swordProjectile.getAttackSpeed(mainHandItem));
            localPlayer.swing(InteractionHand.MAIN_HAND);
        }
    }
}
