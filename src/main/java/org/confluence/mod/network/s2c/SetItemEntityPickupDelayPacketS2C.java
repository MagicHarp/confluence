package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record SetItemEntityPickupDelayPacketS2C(int id, int delay) {
    public static void encode(SetItemEntityPickupDelayPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.id);
        friendlyByteBuf.writeInt(packet.delay);
    }

    public static SetItemEntityPickupDelayPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new SetItemEntityPickupDelayPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
    }

    public static void handle(SetItemEntityPickupDelayPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlePickupDelay(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
