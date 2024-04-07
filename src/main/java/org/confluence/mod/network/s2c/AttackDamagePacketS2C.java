package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record AttackDamagePacketS2C(float amount, long gameTime) {
    public static void encode(AttackDamagePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(packet.amount);
        friendlyByteBuf.writeLong(packet.gameTime);
    }

    public static AttackDamagePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new AttackDamagePacketS2C(friendlyByteBuf.readFloat(), friendlyByteBuf.readLong());
    }
}
