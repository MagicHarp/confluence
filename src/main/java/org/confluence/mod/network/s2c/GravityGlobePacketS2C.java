package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record GravityGlobePacketS2C(boolean has) {
    public static void encode(GravityGlobePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.has);
    }

    public static GravityGlobePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new GravityGlobePacketS2C(friendlyByteBuf.readBoolean());
    }
}
