package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import org.confluence.mod.item.gun.BaseGunItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.GunShootingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class GunShootingHandler {
    public static void handle(InputEvent.InteractionKeyMappingTriggered event, LocalPlayer localPlayer) {
        if (event.isUseItem()) return;
        if (localPlayer.getMainHandItem().getItem() instanceof BaseGunItem baseGunItem && !localPlayer.getCooldowns().isOnCooldown(baseGunItem)) {
            event.setSwingHand(false);
            event.setCanceled(true);
            performShooting(baseGunItem, true);
        } else if (localPlayer.getOffhandItem().getItem() instanceof BaseGunItem baseGunItem && !localPlayer.getCooldowns().isOnCooldown(baseGunItem)) {
            event.setSwingHand(false);
            event.setCanceled(true);
            performShooting(baseGunItem, false);
        }
    }

    private static void performShooting(BaseGunItem baseGunItem, boolean isMainHand) {
        if (!baseGunItem.isAuto()) {
            Minecraft.getInstance().options.keyAttack.setDown(false);
        }
        NetworkHandler.CHANNEL.sendToServer(new GunShootingPacketC2S(isMainHand));
    }
}
