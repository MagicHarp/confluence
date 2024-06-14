package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.command.GamePhase;

import java.util.function.Supplier;

public record GamePhasePacketS2C(GamePhase gamePhase) {
    public static void encode(GamePhasePacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeVarInt(packet.gamePhase.ordinal());
    }

    public static GamePhasePacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new GamePhasePacketS2C(GamePhase.values()[friendlyByteBuf.readVarInt()]);
    }

    public static void handle(GamePhasePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleGamePhase(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
