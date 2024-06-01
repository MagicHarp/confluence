package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import org.confluence.mod.item.gun.AbstractGunItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.GunShootingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class GunShootingHandler {
    public static void handle(InputEvent.InteractionKeyMappingTriggered event, LocalPlayer localPlayer) {
        if (event.isUseItem()) return;
        if (localPlayer.getMainHandItem().getItem() instanceof AbstractGunItem abstractGunItem && !localPlayer.getCooldowns().isOnCooldown(abstractGunItem)) {
            event.setSwingHand(false);
            event.setCanceled(true);
            performShooting(abstractGunItem, true);
        } else if (localPlayer.getOffhandItem().getItem() instanceof AbstractGunItem abstractGunItem && !localPlayer.getCooldowns().isOnCooldown(abstractGunItem)) {
            event.setSwingHand(false);
            event.setCanceled(true);
            performShooting(abstractGunItem, false);
        }
    }

    private static void performShooting(AbstractGunItem abstractGunItem, boolean isMainHand) {
        if (!abstractGunItem.isAuto()) {
            Minecraft.getInstance().options.keyAttack.setDown(false);
        }
        NetworkHandler.CHANNEL.sendToServer(new GunShootingPacketC2S(isMainHand));
    }
}
