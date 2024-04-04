package org.confluence.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerInputHandler;
import org.confluence.mod.network.c2s.PlayerJumpPacketC2S;
import org.confluence.mod.network.c2s.SpeedBootsNBTPacketC2S;
import org.confluence.mod.network.s2c.*;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Confluence.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, ManaPacketS2C.class, ManaPacketS2C::encode, ManaPacketS2C::decode, ClientPacketHandler::handleMana);
        CHANNEL.registerMessage(packetId++, EchoBlockVisibilityPacketS2C.class, EchoBlockVisibilityPacketS2C::encode, EchoBlockVisibilityPacketS2C::decode, ClientPacketHandler::handleEchoBlock);
        CHANNEL.registerMessage(packetId++, HolyWaterColorUpdatePacketS2C.class, HolyWaterColorUpdatePacketS2C::encode, HolyWaterColorUpdatePacketS2C::decode, ClientPacketHandler::handleHolyWater);
        CHANNEL.registerMessage(packetId++, SpecificMoonPacketS2C.class, SpecificMoonPacketS2C::encode, SpecificMoonPacketS2C::decode, ClientPacketHandler::handleSpecificMoon);
        CHANNEL.registerMessage(packetId++, PlayerJumpPacketS2C.class, PlayerJumpPacketS2C::encode, PlayerJumpPacketS2C::decode, PlayerInputHandler::handleJumpPacket);
        CHANNEL.registerMessage(packetId++, PlayerFlyPacketS2C.class, PlayerFlyPacketS2C::encode, PlayerFlyPacketS2C::decode, PlayerInputHandler::handleFlyPacket);
        CHANNEL.registerMessage(packetId++, InfoCurioCheckPacketS2C.class, InfoCurioCheckPacketS2C::encode, InfoCurioCheckPacketS2C::decode, InformationHandler::handlePacket);
        CHANNEL.registerMessage(packetId++, EntityKilledPacketS2C.class, EntityKilledPacketS2C::encode, EntityKilledPacketS2C::decode, InformationHandler::handleEntityKilled);

        CHANNEL.registerMessage(packetId++, PlayerJumpPacketC2S.class, PlayerJumpPacketC2S::encode, PlayerJumpPacketC2S::decode, PlayerJumpPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, SpeedBootsNBTPacketC2S.class, SpeedBootsNBTPacketC2S::encode, SpeedBootsNBTPacketC2S::decode, SpeedBootsNBTPacketC2S::handle);
    }
}
