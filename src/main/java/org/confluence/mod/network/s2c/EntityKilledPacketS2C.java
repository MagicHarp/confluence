package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record EntityKilledPacketS2C(int amount, ResourceLocation type) {
    public static void encode(EntityKilledPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.amount);
        friendlyByteBuf.writeResourceLocation(packet.type);
    }

    public static EntityKilledPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new EntityKilledPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readResourceLocation());
    }
}
