package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record FlushPlayerAbilityPacketS2C(boolean flush) {
    public static void encode(FlushPlayerAbilityPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.flush);
    }

    public static FlushPlayerAbilityPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new FlushPlayerAbilityPacketS2C(friendlyByteBuf.readBoolean());
    }
}
