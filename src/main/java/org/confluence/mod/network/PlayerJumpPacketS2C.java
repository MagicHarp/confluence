package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record PlayerJumpPacketS2C(int maxJump, double multiY) {
    public static void encode(PlayerJumpPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.maxJump);
        friendlyByteBuf.writeDouble(packet.multiY);
    }

    public static PlayerJumpPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readDouble());
    }
}
