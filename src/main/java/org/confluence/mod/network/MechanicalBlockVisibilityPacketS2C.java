package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record MechanicalBlockVisibilityPacketS2C(boolean visible) {
    public static void encode(MechanicalBlockVisibilityPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.visible());
    }

    public static MechanicalBlockVisibilityPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new MechanicalBlockVisibilityPacketS2C(friendlyByteBuf.readBoolean());
    }
}
