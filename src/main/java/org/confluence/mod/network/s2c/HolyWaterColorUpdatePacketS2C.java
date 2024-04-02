package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record HolyWaterColorUpdatePacketS2C(boolean show) {
    public static void encode(HolyWaterColorUpdatePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.show);
    }

    public static HolyWaterColorUpdatePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new HolyWaterColorUpdatePacketS2C(friendlyByteBuf.readBoolean());
    }
}
