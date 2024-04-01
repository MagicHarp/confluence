package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record SpecificMoonPacketS2C(int id) {
    public static void encode(SpecificMoonPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.id);
    }

    public static SpecificMoonPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new SpecificMoonPacketS2C(friendlyByteBuf.readInt());
    }
}
