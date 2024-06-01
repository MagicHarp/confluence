package org.confluence.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.network.c2s.*;
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
        CHANNEL.registerMessage(packetId++, ManaPacketS2C.class, ManaPacketS2C::encode, ManaPacketS2C::decode, ClientPacketHandler::handleMana);
        CHANNEL.registerMessage(packetId++, EchoBlockVisibilityPacketS2C.class, EchoBlockVisibilityPacketS2C::encode, EchoBlockVisibilityPacketS2C::decode, ClientPacketHandler::handleEchoBlock);
        CHANNEL.registerMessage(packetId++, HolyWaterColorUpdatePacketS2C.class, HolyWaterColorUpdatePacketS2C::encode, HolyWaterColorUpdatePacketS2C::decode, ClientPacketHandler::handleHolyWater);
        CHANNEL.registerMessage(packetId++, SpecificMoonPacketS2C.class, SpecificMoonPacketS2C::encode, SpecificMoonPacketS2C::decode, ClientPacketHandler::handleSpecificMoon);
        CHANNEL.registerMessage(packetId++, PlayerJumpPacketS2C.class, PlayerJumpPacketS2C::encode, PlayerJumpPacketS2C::decode, PlayerJumpHandler::handleJumpPacket);
        CHANNEL.registerMessage(packetId++, PlayerFlyPacketS2C.class, PlayerFlyPacketS2C::encode, PlayerFlyPacketS2C::decode, PlayerJumpHandler::handleFlyPacket);
        CHANNEL.registerMessage(packetId++, InfoCurioCheckPacketS2C.class, InfoCurioCheckPacketS2C::encode, InfoCurioCheckPacketS2C::decode, InformationHandler::handlePacket);
        CHANNEL.registerMessage(packetId++, EntityKilledPacketS2C.class, EntityKilledPacketS2C::encode, EntityKilledPacketS2C::decode, InformationHandler::handleEntityKilled);
        CHANNEL.registerMessage(packetId++, AttackDamagePacketS2C.class, AttackDamagePacketS2C::encode, AttackDamagePacketS2C::decode, InformationHandler::handleAttackDamage);
        CHANNEL.registerMessage(packetId++, WindSpeedPacketS2C.class, WindSpeedPacketS2C::encode, WindSpeedPacketS2C::decode, InformationHandler::handleWindSpeed);
        CHANNEL.registerMessage(packetId++, AutoAttackPacketS2C.class, AutoAttackPacketS2C::encode, AutoAttackPacketS2C::decode, ClientPacketHandler::handleSwing);
        CHANNEL.registerMessage(packetId++, ShieldOfCthulhuPacketS2C.class, ShieldOfCthulhuPacketS2C::encode, ShieldOfCthulhuPacketS2C::decode, ClientPacketHandler::handleCthulhu);
        CHANNEL.registerMessage(packetId++, GravityGlobePacketS2C.class, GravityGlobePacketS2C::encode, GravityGlobePacketS2C::decode, GravitationEffect::handleGlobe);
        CHANNEL.registerMessage(packetId++, FlushPlayerAbilityPacketS2C.class, FlushPlayerAbilityPacketS2C::encode, FlushPlayerAbilityPacketS2C::decode, ClientPacketHandler::handleFlush);
        CHANNEL.registerMessage(packetId++, BroadcastGravitationRotPacketS2C.class, BroadcastGravitationRotPacketS2C::encode, BroadcastGravitationRotPacketS2C::decode, ClientPacketHandler::handleRemoteRot);
        CHANNEL.registerMessage(packetId++, PlayerLightPacketS2C.class, PlayerLightPacketS2C::encode, PlayerLightPacketS2C::decode, ClientPacketHandler::handleLight);

        CHANNEL.registerMessage(packetId++, PlayerJumpPacketC2S.class, PlayerJumpPacketC2S::encode, PlayerJumpPacketC2S::decode, PlayerJumpPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, SpeedBootsNBTPacketC2S.class, SpeedBootsNBTPacketC2S::encode, SpeedBootsNBTPacketC2S::decode, SpeedBootsNBTPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, WingsGlidePacketC2S.class, WingsGlidePacketC2S::encode, WingsGlidePacketC2S::decode, WingsGlidePacketC2S::handle);
        CHANNEL.registerMessage(packetId++, GravitationPacketC2S.class, GravitationPacketC2S::encode, GravitationPacketC2S::decode, GravitationPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, FallDistancePacketC2S.class, FallDistancePacketC2S::encode, FallDistancePacketC2S::decode, FallDistancePacketC2S::handle);
        CHANNEL.registerMessage(packetId++, HookThrowingPacketC2S.class, HookThrowingPacketC2S::encode, HookThrowingPacketC2S::decode, HookThrowingPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, GunShootingPacketC2S.class, GunShootingPacketC2S::encode, GunShootingPacketC2S::decode, GunShootingPacketC2S::handle);
    }
}
