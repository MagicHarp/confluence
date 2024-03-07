package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record ManaPacketS2C(int stars, int currentMana) {
    public static void encode(ManaPacketS2C manaPacketS2C, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(manaPacketS2C.stars());
        friendlyByteBuf.writeInt(manaPacketS2C.currentMana());
    }

    public static ManaPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new ManaPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
    }
}
