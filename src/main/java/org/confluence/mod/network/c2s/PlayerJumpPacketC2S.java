package org.confluence.mod.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PlayerJumpPacketC2S(boolean jumpBySelf, boolean resetFallDistance, float motionY) {
    public static void encode(PlayerJumpPacketC2S packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(packet.jumpBySelf);
        friendlyByteBuf.writeBoolean(packet.resetFallDistance);
        friendlyByteBuf.writeFloat(packet.motionY);
    }

    public static PlayerJumpPacketC2S decode(FriendlyByteBuf friendlyByteBuf) {
        return new PlayerJumpPacketC2S(friendlyByteBuf.readBoolean(), friendlyByteBuf.readBoolean(), friendlyByteBuf.readFloat());
    }

    public static void handle(PlayerJumpPacketC2S packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            serverPlayer.hasImpulse = true;
            if (packet.jumpBySelf) {
                serverPlayer.awardStat(Stats.JUMP);
                serverPlayer.causeFoodExhaustion(serverPlayer.isSprinting() ? 0.2F : 0.05F);
            }
            if (packet.resetFallDistance) {
                serverPlayer.resetFallDistance();
            }
            Vec3 motion = serverPlayer.getDeltaMovement();
            serverPlayer.setDeltaMovement(motion.x, packet.motionY, motion.z);
        });
        context.setPacketHandled(true);
    }
}
