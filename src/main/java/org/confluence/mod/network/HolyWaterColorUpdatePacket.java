package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record HolyWaterColorUpdatePacket(boolean show) {
    public static void encode(HolyWaterColorUpdatePacket packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.show);
    }

    public static HolyWaterColorUpdatePacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new HolyWaterColorUpdatePacket(friendlyByteBuf.readBoolean());
    }
}
