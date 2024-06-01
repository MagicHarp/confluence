package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record PlayerLightPacketS2C(UUID playerUUID, boolean enable) {
    public static void encode(PlayerLightPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(packet.playerUUID);
        friendlyByteBuf.writeBoolean(packet.enable);
    }

    public static PlayerLightPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerLightPacketS2C(friendlyByteBuf.readUUID(), friendlyByteBuf.readBoolean());
    }
}
