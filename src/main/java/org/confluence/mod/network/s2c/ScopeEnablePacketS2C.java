package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record ScopeEnablePacketS2C(boolean enable) {
    public static void encode(ScopeEnablePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.enable);
    }

    public static ScopeEnablePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new ScopeEnablePacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(ScopeEnablePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleScope(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
