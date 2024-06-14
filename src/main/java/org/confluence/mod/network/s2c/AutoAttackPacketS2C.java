package org.confluence.mod.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.client.handler.ClientPacketHandler;

import java.util.function.Supplier;

public record AutoAttackPacketS2C(boolean autoAttack) {
    public static void encode(AutoAttackPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.autoAttack);
    }

    public static AutoAttackPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new AutoAttackPacketS2C(friendlyByteBuf.readBoolean());
    }

    public static void handle(AutoAttackPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSwing(packet, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
