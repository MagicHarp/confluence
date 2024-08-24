package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.mixin.accessor.LivingEntityAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.PlayerJumpPacketC2S;
import org.confluence.mod.network.s2c.PlayerClimbPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class PlayerClimbHandler {
    private static boolean wallJumped = false;
    private static int climberAmount = 0;

    public static void handle(LocalPlayer localPlayer, Vec2 vector, boolean jumping) {
        if (climberAmount <= 0 || localPlayer.onGround() || (vector.x == 0.0 && vector.y == 0.0)){
            wallJumped = true;
            return;
        }
        Vec3 motion = localPlayer.getDeltaMovement();
        double motionY = motion.y;
        if (motionY > 0.0) return;
        float rad = localPlayer.getYRot() * Mth.DEG_TO_RAD;
        float cos = Mth.cos(rad);
        float sin = Mth.sin(rad);
        double x = vector.x * cos + vector.y * -sin;
        double z = vector.x * sin + vector.y * cos;

        if (hasMotionToWall(localPlayer, x, z)) {
            if (jumping) {
                if (!wallJumped) {
                    wallJump(localPlayer, x, z);
                    wallJumped = true;
                    return;
                }
            } else {
                wallJumped = false;
            }
            if (localPlayer.isShiftKeyDown()) {
                motionY = -0.1;
            } else if (climberAmount == 1) {
                motionY = -0.05;
            } else if (climberAmount >= 2) {
                motionY = 0.0;
            }
            localPlayer.hasImpulse = true;
            localPlayer.fallDistance = 0.0F;
            localPlayer.setDeltaMovement(motion.x * 0.93, motionY, motion.z * 0.93);
            PlayerJumpHandler.flushState(true);
            NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(false, true, (float) motionY));
        }
    }

    private static boolean hasMotionToWall(LocalPlayer localPlayer, double x, double z) {
        AABB aabb = localPlayer.getDimensions(localPlayer.getPose())
            .makeBoundingBox(localPlayer.position()).inflate(0.01)
            .move(x * 0.1, 0.0, z * 0.1);
        return !localPlayer.level().noCollision(aabb);
    }

    private static void wallJump(LocalPlayer localPlayer, double x, double z) {
        double motionY = ((LivingEntityAccessor) localPlayer).callGetJumpPower() * 1.1;
        Vec3 vec3 = localPlayer.getDeltaMovement();
        localPlayer.setDeltaMovement(vec3.add(vec3.x - x * 0.11, motionY, vec3.z - z * 0.11));
        localPlayer.hasImpulse = true;
        NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(true, false, (float) motionY));
    }

    public static void handlePacket(PlayerClimbPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> climberAmount = packet.climberAmount());
        context.setPacketHandled(true);
    }
}
