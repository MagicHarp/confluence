package org.confluence.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.ClientPacketHandler;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel CHANNEL;

    private static int packetId = 0;

    public static void register() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Confluence.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );

        CHANNEL.registerMessage(
            packetId++,
            ManaPacketS2C.class,
            ManaPacketS2C::encode,
            ManaPacketS2C::decode,
            ClientPacketHandler::handleMana
        );

        CHANNEL.registerMessage(
            packetId++,
            EchoBlockVisibilityPacket.class,
            EchoBlockVisibilityPacket::encode,
            EchoBlockVisibilityPacket::decode,
            ClientPacketHandler::handleEchoBlock
        );
    }
}
