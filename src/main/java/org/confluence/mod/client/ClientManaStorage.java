package org.confluence.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.ManaPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ClientManaStorage {
    public static ClientManaStorage INSTANCE;

    private static int stars;
    private static int currentMana;

    public static void handlePacket(ManaPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            stars = packet.stars();
            currentMana = packet.currentMana();
        });
        ctx.get().setPacketHandled(true);

        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Client: " + currentMana));
        }
    }

    public static int getStars() {
        return stars;
    }

    public static int getCurrentMana() {
        return currentMana;
    }
}
