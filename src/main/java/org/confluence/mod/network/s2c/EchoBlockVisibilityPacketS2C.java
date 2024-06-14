package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record EchoBlockVisibilityPacketS2C(boolean visible) {
    public static void encode(EchoBlockVisibilityPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.visible);
    }

    public static EchoBlockVisibilityPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new EchoBlockVisibilityPacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(EchoBlockVisibilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleEchoBlock(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
