package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.PlayerClimbHandler;

import java.util.function.Supplier;

public record PlayerClimbPacketS2C(int climberAmount) {
    public static void encode(PlayerClimbPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.climberAmount);
    }

    public static PlayerClimbPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerClimbPacketS2C(friendlyByteBuf.readInt());
    }

    public static void handle(PlayerClimbPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerClimbHandler.handlePacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
