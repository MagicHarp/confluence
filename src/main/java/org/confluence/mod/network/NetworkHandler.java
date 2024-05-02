package org.confluence.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.network.c2s.FallDistancePacketC2S;
import org.confluence.mod.network.c2s.GravitationPacketC2S;
import org.confluence.mod.network.c2s.PlayerJumpPacketC2S;
import org.confluence.mod.network.c2s.SpeedBootsNBTPacketC2S;
import org.confluence.mod.network.s2c.*;

public final class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Confluence.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, PlayerJumpPacketS2C.class, PlayerJumpPacketS2C::encode, PlayerJumpPacketS2C::decode, PlayerJumpHandler::handleJumpPacket);
        CHANNEL.registerMessage(packetId++, PlayerFlyPacketS2C.class, PlayerFlyPacketS2C::encode, PlayerFlyPacketS2C::decode, PlayerJumpHandler::handleFlyPacket);
        CHANNEL.registerMessage(packetId++, InfoCurioCheckPacketS2C.class, InfoCurioCheckPacketS2C::encode, InfoCurioCheckPacketS2C::decode, InformationHandler::handlePacket);
        CHANNEL.registerMessage(packetId++, EntityKilledPacketS2C.class, EntityKilledPacketS2C::encode, EntityKilledPacketS2C::decode, InformationHandler::handleEntityKilled);
        CHANNEL.registerMessage(packetId++, AttackDamagePacketS2C.class, AttackDamagePacketS2C::encode, AttackDamagePacketS2C::decode, InformationHandler::handleAttackDamage);
        CHANNEL.registerMessage(packetId++, AutoAttackPacketS2C.class, AutoAttackPacketS2C::encode, AutoAttackPacketS2C::decode, ClientPacketHandler::handleSwing);
        CHANNEL.registerMessage(packetId++, ShieldOfCthulhuPacketS2C.class, ShieldOfCthulhuPacketS2C::encode, ShieldOfCthulhuPacketS2C::decode, ClientPacketHandler::handleCthulhu);
        CHANNEL.registerMessage(packetId++, GravityGlobePacketS2C.class, GravityGlobePacketS2C::encode, GravityGlobePacketS2C::decode, ClientPacketHandler::handleGlobe);

        CHANNEL.registerMessage(packetId++, PlayerJumpPacketC2S.class, PlayerJumpPacketC2S::encode, PlayerJumpPacketC2S::decode, PlayerJumpPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, SpeedBootsNBTPacketC2S.class, SpeedBootsNBTPacketC2S::encode, SpeedBootsNBTPacketC2S::decode, SpeedBootsNBTPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, GravitationPacketC2S.class, GravitationPacketC2S::encode, GravitationPacketC2S::decode, GravitationPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, FallDistancePacketC2S.class, FallDistancePacketC2S::encode, FallDistancePacketC2S::decode, FallDistancePacketC2S::handle);
    }
}
