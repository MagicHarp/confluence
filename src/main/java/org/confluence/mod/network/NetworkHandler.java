package org.confluence.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.PlayerFlyHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;

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
            EchoBlockVisibilityPacketS2C.class,
            EchoBlockVisibilityPacketS2C::encode,
            EchoBlockVisibilityPacketS2C::decode,
            ClientPacketHandler::handleEchoBlock
        );

        CHANNEL.registerMessage(
            packetId++,
            HolyWaterColorUpdatePacketS2C.class,
            HolyWaterColorUpdatePacketS2C::encode,
            HolyWaterColorUpdatePacketS2C::decode,
            ClientPacketHandler::handleHolyWater
        );

        CHANNEL.registerMessage(
            packetId++,
            MechanicalBlockVisibilityPacketS2C.class,
            MechanicalBlockVisibilityPacketS2C::encode,
            MechanicalBlockVisibilityPacketS2C::decode,
            ClientPacketHandler::handleMechanicalBlock
        );

        CHANNEL.registerMessage(
            packetId++,
            FallDistancePacketC2S.class,
            FallDistancePacketC2S::encode,
            FallDistancePacketC2S::decode,
            FallDistancePacketC2S::handle
        );

        CHANNEL.registerMessage(
            packetId++,
            PlayerJumpPacketS2C.class,
            PlayerJumpPacketS2C::encode,
            PlayerJumpPacketS2C::decode,
            PlayerJumpHandler::handlePacket
        );

        CHANNEL.registerMessage(
            packetId++,
            PlayerFlyPacketS2C.class,
            PlayerFlyPacketS2C::encode,
            PlayerFlyPacketS2C::decode,
            PlayerFlyHandler::handlePacket
        );
    }
}
