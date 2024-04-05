package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record ContinuingSwingHandPacketS2C(boolean swing) {
    public static void encode(ContinuingSwingHandPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.swing);
    }

    public static ContinuingSwingHandPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new ContinuingSwingHandPacketS2C(friendlyByteBuf.readBoolean());
    }
}
