package org.confluence.mod.network;

import net.minecraft.network.FriendlyByteBuf;

public record EchoBlockVisibilityPacketS2C(boolean visible) {
    public static void encode(EchoBlockVisibilityPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.visible());
    }

    public static EchoBlockVisibilityPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new EchoBlockVisibilityPacketS2C(friendlyByteBuf.readBoolean());
    }
}
