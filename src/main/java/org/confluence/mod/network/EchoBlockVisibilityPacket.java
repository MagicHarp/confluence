package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record EchoBlockVisibilityPacket(boolean visible) {
    public static void encode(EchoBlockVisibilityPacket packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.visible());
    }

    public static EchoBlockVisibilityPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new EchoBlockVisibilityPacket(friendlyByteBuf.readBoolean());
    }
}
