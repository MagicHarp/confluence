package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record ShieldOfCthulhuPacketS2C(boolean has) {
    public static void encode(ShieldOfCthulhuPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.has);
    }

    public static ShieldOfCthulhuPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new ShieldOfCthulhuPacketS2C(friendlyByteBuf.readBoolean());
    }
}
