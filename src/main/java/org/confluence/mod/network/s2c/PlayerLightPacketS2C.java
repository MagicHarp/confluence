package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record PlayerLightPacketS2C(UUID playerUUID, int color, boolean enable) {
    public static void encode(PlayerLightPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(packet.playerUUID);
        friendlyByteBuf.writeInt(packet.color);
        friendlyByteBuf.writeBoolean(packet.enable);
    }

    public static PlayerLightPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerLightPacketS2C(friendlyByteBuf.readUUID(), friendlyByteBuf.readInt(), friendlyByteBuf.readBoolean());
    }
}
