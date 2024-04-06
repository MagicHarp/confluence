package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record WindSpeedPacketS2C(float x, float z) {
    public static void encode(WindSpeedPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.x);
        friendlyByteBuf.writeFloat(packet.z);
    }

    public static WindSpeedPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new WindSpeedPacketS2C(friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat());
    }
}
