package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record BroadcastGravitationRotPacketS2C(int entityId, boolean enabled) {
    public static void encode(BroadcastGravitationRotPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.entityId);
        friendlyByteBuf.writeBoolean(packet.enabled);
    }

    public static BroadcastGravitationRotPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new BroadcastGravitationRotPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readBoolean());
    }
}
