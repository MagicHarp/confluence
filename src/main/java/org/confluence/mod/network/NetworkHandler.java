package org.confluence.mod.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
import org.confluence.mod.network.c2s.*;
import org.confluence.mod.network.s2c.*;

public final class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        Confluence.asResource("main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, ManaPacketS2C.class, ManaPacketS2C::encode, ManaPacketS2C::decode, ManaPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, EchoBlockVisibilityPacketS2C.class, EchoBlockVisibilityPacketS2C::encode, EchoBlockVisibilityPacketS2C::decode, EchoBlockVisibilityPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, SpecificMoonPacketS2C.class, SpecificMoonPacketS2C::encode, SpecificMoonPacketS2C::decode, SpecificMoonPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, PlayerJumpPacketS2C.class, PlayerJumpPacketS2C::encode, PlayerJumpPacketS2C::decode, PlayerJumpPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, PlayerFlyPacketS2C.class, PlayerFlyPacketS2C::encode, PlayerFlyPacketS2C::decode, PlayerFlyPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, InfoCurioCheckPacketS2C.class, InfoCurioCheckPacketS2C::encode, InfoCurioCheckPacketS2C::decode, InfoCurioCheckPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, EntityKilledPacketS2C.class, EntityKilledPacketS2C::encode, EntityKilledPacketS2C::decode, EntityKilledPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, AttackDamagePacketS2C.class, AttackDamagePacketS2C::encode, AttackDamagePacketS2C::decode, AttackDamagePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, WindSpeedPacketS2C.class, WindSpeedPacketS2C::encode, WindSpeedPacketS2C::decode, WindSpeedPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, AutoAttackPacketS2C.class, AutoAttackPacketS2C::encode, AutoAttackPacketS2C::decode, AutoAttackPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, ShieldOfCthulhuPacketS2C.class, ShieldOfCthulhuPacketS2C::encode, ShieldOfCthulhuPacketS2C::decode, ShieldOfCthulhuPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, GravityGlobePacketS2C.class, GravityGlobePacketS2C::encode, GravityGlobePacketS2C::decode, GravityGlobePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, FlushPlayerAbilityPacketS2C.class, FlushPlayerAbilityPacketS2C::encode, FlushPlayerAbilityPacketS2C::decode, FlushPlayerAbilityPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, BroadcastGravitationRotPacketS2C.class, BroadcastGravitationRotPacketS2C::encode, BroadcastGravitationRotPacketS2C::decode, BroadcastGravitationRotPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, PlayerLightPacketS2C.class, PlayerLightPacketS2C::encode, PlayerLightPacketS2C::decode, PlayerLightPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, GamePhasePacketS2C.class, GamePhasePacketS2C::encode, GamePhasePacketS2C::decode, GamePhasePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, ScopeEnablePacketS2C.class, ScopeEnablePacketS2C::encode, ScopeEnablePacketS2C::decode, ScopeEnablePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, RightClickSubtractorPacketS2C.class, RightClickSubtractorPacketS2C::encode, RightClickSubtractorPacketS2C::decode, RightClickSubtractorPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, PlayerClimbPacketS2C.class, PlayerClimbPacketS2C::encode, PlayerClimbPacketS2C::decode, PlayerClimbPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, TabiPacketS2C.class, TabiPacketS2C::encode, TabiPacketS2C::decode, TabiPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, StepStoolStepPacketS2C.class, StepStoolStepPacketS2C::encode, StepStoolStepPacketS2C::decode, StepStoolStepPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, SetItemEntityPickupDelayPacketS2C.class, SetItemEntityPickupDelayPacketS2C::encode, SetItemEntityPickupDelayPacketS2C::decode, SetItemEntityPickupDelayPacketS2C::handle);

        CHANNEL.registerMessage(packetId++, PlayerJumpPacketC2S.class, PlayerJumpPacketC2S::encode, PlayerJumpPacketC2S::decode, PlayerJumpPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, SpeedBootsNBTPacketC2S.class, SpeedBootsNBTPacketC2S::encode, SpeedBootsNBTPacketC2S::decode, SpeedBootsNBTPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, WingsGlidePacketC2S.class, WingsGlidePacketC2S::encode, WingsGlidePacketC2S::decode, WingsGlidePacketC2S::handle);
        CHANNEL.registerMessage(packetId++, GravitationPacketC2S.class, GravitationPacketC2S::encode, GravitationPacketC2S::decode, GravitationPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, FallDistancePacketC2S.class, FallDistancePacketC2S::encode, FallDistancePacketC2S::decode, FallDistancePacketC2S::handle);
        CHANNEL.registerMessage(packetId++, HookThrowingPacketC2S.class, HookThrowingPacketC2S::encode, HookThrowingPacketC2S::decode, HookThrowingPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, GunShootingPacketC2S.class, GunShootingPacketC2S::encode, GunShootingPacketC2S::decode, GunShootingPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, SwordShootingPacketC2S.class, SwordShootingPacketC2S::encode, SwordShootingPacketC2S::decode, SwordShootingPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, StepStoolStepPacketC2S.class, StepStoolStepPacketC2S::encode, StepStoolStepPacketC2S::decode, StepStoolStepPacketC2S::handle);
    }
}
