package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record PlayerJumpPacketS2C(int maxJump, int height) {
    public static void encode(PlayerJumpPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.maxJump);
        friendlyByteBuf.writeInt(packet.height);
    }

    public static PlayerJumpPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
    }
}
