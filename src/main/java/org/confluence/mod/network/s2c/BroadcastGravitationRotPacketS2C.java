package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.GravitationHandler;

import java.util.function.Supplier;

public record BroadcastGravitationRotPacketS2C(int entityId, boolean enabled) {
    public static void encode(BroadcastGravitationRotPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.entityId);
        friendlyByteBuf.writeBoolean(packet.enabled);
    }

    public static BroadcastGravitationRotPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new BroadcastGravitationRotPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readBoolean());
    }

    public static void handle(BroadcastGravitationRotPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> GravitationHandler.handleRemoteRot(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
