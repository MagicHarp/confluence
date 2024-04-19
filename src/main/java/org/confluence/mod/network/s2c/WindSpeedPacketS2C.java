package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record WindSpeedPacketS2C(float speed) {
    public static void encode(WindSpeedPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.speed);
    }

    public static WindSpeedPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new WindSpeedPacketS2C(friendlyByteBuf.readFloat());
    }
}
