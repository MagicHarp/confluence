package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.StepStoolHandler;

import java.util.function.Supplier;

public record StepStoolStepPacketS2C(int slot, int maxStep) {
    public static final int NO_CURIO = -1;
    public static final int RESET_STEP = -2;

    public static StepStoolStepPacketS2C resetStep() {
        return new StepStoolStepPacketS2C(RESET_STEP, 0);
    }

    public static void encode(StepStoolStepPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(packet.slot);
        friendlyByteBuf.writeInt(packet.maxStep);
    }

    public static StepStoolStepPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new StepStoolStepPacketS2C(friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
    }

    public static void handle(StepStoolStepPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StepStoolHandler.handlePacket(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
