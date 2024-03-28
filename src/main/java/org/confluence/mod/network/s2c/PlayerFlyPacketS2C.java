package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record PlayerFlyPacketS2C(int maxFlyTicks, double flySpeed) {
    public static void encode(PlayerFlyPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.maxFlyTicks);
        friendlyByteBuf.writeDouble(packet.flySpeed);
    }

    public static PlayerFlyPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerFlyPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readDouble());
    }
}
