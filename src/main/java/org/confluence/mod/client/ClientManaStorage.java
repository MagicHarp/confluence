package org.confluence.mod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.ManaPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ClientManaStorage {
    private static int maxMana = 20;
    private static int currentMana = 20;

    public static void handlePacket(ManaPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            maxMana = packet.maxMana();
            currentMana = packet.currentMana();
        });
        ctx.get().setPacketHandled(true);
    }

    public static int getCurrentMana() {
        return currentMana;
    }

    public static int getMaxMana() {
        return maxMana;
    }
}
