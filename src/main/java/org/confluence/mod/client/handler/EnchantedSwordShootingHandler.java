
package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import org.confluence.mod.item.gun.AbstractGunItem;
import org.confluence.mod.item.sword.Swords;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.EnchantedSwordShootingPacketC2S;

@OnlyIn(Dist.CLIENT)
public final class EnchantedSwordShootingHandler {
    public static void handle(InputEvent.InteractionKeyMappingTriggered event, LocalPlayer localPlayer) {
        if (event.isUseItem()) return;
        if(localPlayer.getAttackStrengthScale(0.0F) == 1.0F && localPlayer.getMainHandItem().is(Swords.ENCHANTED_SWORD.get())) {
            NetworkHandler.CHANNEL.sendToServer(new EnchantedSwordShootingPacketC2S());
        }
    }
}
