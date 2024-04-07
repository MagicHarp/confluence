package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record AutoAttackPacketS2C(boolean autoAttack) {
    public static void encode(AutoAttackPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.autoAttack);
    }

    public static AutoAttackPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new AutoAttackPacketS2C(friendlyByteBuf.readBoolean());
    }
}
