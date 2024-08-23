package org.confluence.mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.confluence.mod.Confluence;
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
        CHANNEL.registerMessage(packetId++, PlayerJumpPacketS2C.class, PlayerJumpPacketS2C::encode, PlayerJumpPacketS2C::decode, PlayerJumpPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, PlayerFlyPacketS2C.class, PlayerFlyPacketS2C::encode, PlayerFlyPacketS2C::decode, PlayerFlyPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, InfoCurioCheckPacketS2C.class, InfoCurioCheckPacketS2C::encode, InfoCurioCheckPacketS2C::decode, InfoCurioCheckPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, EntityKilledPacketS2C.class, EntityKilledPacketS2C::encode, EntityKilledPacketS2C::decode, EntityKilledPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, AttackDamagePacketS2C.class, AttackDamagePacketS2C::encode, AttackDamagePacketS2C::decode, AttackDamagePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, AutoAttackPacketS2C.class, AutoAttackPacketS2C::encode, AutoAttackPacketS2C::decode, AutoAttackPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, ShieldOfCthulhuPacketS2C.class, ShieldOfCthulhuPacketS2C::encode, ShieldOfCthulhuPacketS2C::decode, ShieldOfCthulhuPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, GravityGlobePacketS2C.class, GravityGlobePacketS2C::encode, GravityGlobePacketS2C::decode, GravityGlobePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, FlushPlayerAbilityPacketS2C.class, FlushPlayerAbilityPacketS2C::encode, FlushPlayerAbilityPacketS2C::decode, FlushPlayerAbilityPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, BroadcastGravitationRotPacketS2C.class, BroadcastGravitationRotPacketS2C::encode, BroadcastGravitationRotPacketS2C::decode, BroadcastGravitationRotPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, ScopeEnablePacketS2C.class, ScopeEnablePacketS2C::encode, ScopeEnablePacketS2C::decode, ScopeEnablePacketS2C::handle);
        CHANNEL.registerMessage(packetId++, RightClickSubtractorPacketS2C.class, RightClickSubtractorPacketS2C::encode, RightClickSubtractorPacketS2C::decode, RightClickSubtractorPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, PlayerClimbPacketS2C.class, PlayerClimbPacketS2C::encode, PlayerClimbPacketS2C::decode, PlayerClimbPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, TabiPacketS2C.class, TabiPacketS2C::encode, TabiPacketS2C::decode, TabiPacketS2C::handle);
        CHANNEL.registerMessage(packetId++, StepStoolStepPacketS2C.class, StepStoolStepPacketS2C::encode, StepStoolStepPacketS2C::decode, StepStoolStepPacketS2C::handle);

        CHANNEL.registerMessage(packetId++, PlayerJumpPacketC2S.class, PlayerJumpPacketC2S::encode, PlayerJumpPacketC2S::decode, PlayerJumpPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, SpeedBootsNBTPacketC2S.class, SpeedBootsNBTPacketC2S::encode, SpeedBootsNBTPacketC2S::decode, SpeedBootsNBTPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, GravitationPacketC2S.class, GravitationPacketC2S::encode, GravitationPacketC2S::decode, GravitationPacketC2S::handle);
        CHANNEL.registerMessage(packetId++, FallDistancePacketC2S.class, FallDistancePacketC2S::encode, FallDistancePacketC2S::decode, FallDistancePacketC2S::handle);
        CHANNEL.registerMessage(packetId++, StepStoolStepPacketC2S.class, StepStoolStepPacketC2S::encode, StepStoolStepPacketC2S::decode, StepStoolStepPacketC2S::handle);
    }
}
