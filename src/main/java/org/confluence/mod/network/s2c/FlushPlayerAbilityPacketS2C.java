package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record FlushPlayerAbilityPacketS2C() {
    public static void encode(FlushPlayerAbilityPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {}

    public static FlushPlayerAbilityPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new FlushPlayerAbilityPacketS2C();
    }

    public static void handle(FlushPlayerAbilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleFlush(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
