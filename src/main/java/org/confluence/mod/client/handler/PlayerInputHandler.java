package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.mixin.LivingEntityAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.FallDistancePacketC2S;
import org.confluence.mod.network.s2c.PlayerFlyPacketS2C;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;
import org.confluence.mod.network.s2c.PlayerOneTimeJumpPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PlayerInputHandler {
    private static boolean jumpKeyDown = true;
    private static boolean jumpFinished = false;

    private static int maxJumpCount = 0;
    private static int remainJumpCount = 0;
    private static double multiY = 0.0;

    private static int maxOneTimeJumpTicks = 0;
    private static int remainOneTimeJumpTicks = 0;
    private static double jumpSpeed = 0.0;

    private static int maxFlyTicks = 0;
    private static int remainFlyTicks = 0;
    private static double flySpeed = 0.0;

    public static void handleJump(LocalPlayer localPlayer) {
        if (localPlayer.onGround()) {
            jumpKeyDown = true;
            jumpFinished = false;
            remainJumpCount = maxJumpCount;
            remainOneTimeJumpTicks = maxOneTimeJumpTicks;
            remainFlyTicks = maxFlyTicks;
        } else if (localPlayer.input.jumping) {
            if (jumpKeyDown) return;
            if (remainJumpCount > 0) {
                remainJumpCount--;
                jumpKeyDown = true;
                Vec3 vec3 = localPlayer.getDeltaMovement();
                double motionY = ((LivingEntityAccessor) localPlayer).callGetJumpPower() * multiY;
                localPlayer.setDeltaMovement(vec3.x, motionY, vec3.z);
                if (localPlayer.isSprinting()) {
                    float f = localPlayer.getYRot() * ((float) Math.PI / 180F);
                    localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().add(-Mth.sin(f) * 0.2F, 0.0D, Mth.cos(f) * 0.2F));
                }
                localPlayer.hasImpulse = true;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(true));
            } else if (!jumpFinished && remainOneTimeJumpTicks > 0) {
                remainOneTimeJumpTicks--;
                Vec3 vec3 = localPlayer.getDeltaMovement();
                localPlayer.setDeltaMovement(vec3.x, jumpSpeed, vec3.z);
                localPlayer.hasImpulse = true;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(false));
            } else if (jumpFinished && remainFlyTicks > 0) {
                remainFlyTicks--;
                Vec3 vec3 = localPlayer.getDeltaMovement();
                localPlayer.setDeltaMovement(vec3.x, flySpeed, vec3.z);
                localPlayer.hasImpulse = true;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(false));
            }
        } else {
            jumpKeyDown = false;
            jumpFinished = remainJumpCount == 0 && (maxOneTimeJumpTicks == 0 || remainOneTimeJumpTicks < maxOneTimeJumpTicks);
        }
    }

    public static void handleJumpPacket(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxJumpCount = packet.maxJumpCount();
            multiY = packet.multiY();
        });
        context.setPacketHandled(true);
    }

    public static void handleOneTimeJumpPacket(PlayerOneTimeJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxOneTimeJumpTicks = packet.maxOneTimeJumpTicks();
            jumpSpeed = packet.jumpSpeed();
        });
        context.setPacketHandled(true);
    }

    public static void handleFlyPacket(PlayerFlyPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxFlyTicks = packet.maxFlyTicks();
            flySpeed = packet.flySpeed();
        });
        context.setPacketHandled(true);
    }
}
