package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record PlayerOneTimeJumpPacketS2C(int maxOneTimeJumpTicks, double jumpSpeed) {
    public static void encode(PlayerOneTimeJumpPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.maxOneTimeJumpTicks);
        friendlyByteBuf.writeDouble(packet.jumpSpeed);
    }

    public static PlayerOneTimeJumpPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerOneTimeJumpPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readDouble());
    }
}
