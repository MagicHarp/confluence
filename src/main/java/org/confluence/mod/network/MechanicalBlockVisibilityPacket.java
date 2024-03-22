package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record MechanicalBlockVisibilityPacket(boolean visible) {
    public static void encode(MechanicalBlockVisibilityPacket packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.visible());
    }

    public static MechanicalBlockVisibilityPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new MechanicalBlockVisibilityPacket(friendlyByteBuf.readBoolean());
    }
}
