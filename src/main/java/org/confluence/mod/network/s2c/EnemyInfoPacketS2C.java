package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record EnemyInfoPacketS2C(int amount) {
    public static void encode(EnemyInfoPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.amount);
    }

    public static EnemyInfoPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new EnemyInfoPacketS2C(friendlyByteBuf.readInt());
    }
}
