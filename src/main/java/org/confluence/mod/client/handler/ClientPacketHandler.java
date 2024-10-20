package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.confluence.mod.common.init.ModSoundEvents;
import org.confluence.mod.network.s2c.ManaPacketS2C;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;

    public static int getCurrentMana() {
        return currentMana;
    }

    public static int getMaxMana() {
        return maxMana;
    }

    public static void handleMana(ManaPacketS2C packet, Player player) {
        maxMana = packet.maxMana();
        currentMana = packet.currentMana();
        if (currentMana == maxMana) {
            Minecraft.getInstance().player.playSound(ModSoundEvents.COOLDOWN_RECOVERY.get());
        }
    }
}
