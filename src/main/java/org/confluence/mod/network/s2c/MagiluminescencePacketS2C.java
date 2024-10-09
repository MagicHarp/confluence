package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record MagiluminescencePacketS2C(boolean has) {
    public static void encode(MagiluminescencePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.has);
    }

    public static MagiluminescencePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new MagiluminescencePacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(MagiluminescencePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleMagiluminescence(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
