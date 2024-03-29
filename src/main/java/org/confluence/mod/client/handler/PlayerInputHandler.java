package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.mixin.LivingEntityAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.PlayerJumpPacketC2S;
import org.confluence.mod.network.s2c.PlayerFlyPacketS2C;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PlayerInputHandler {
    private static boolean jumpKeyDown = true;

    private static double fartSpeed = 0.0;
    private static boolean fartFinished = false;

    private static double sandstormSpeed = 0.0;
    private static int maxSandstormTicks = 0;
    private static int remainSandstormTicks = 0;
    private static boolean sandstormFinished = false;

    private static double blizzardSpeed = 0.0;
    private static int maxBlizzardTicks = 0;
    private static int remainBlizzardTicks = 0;
    private static boolean blizzardFinished = false;

    private static double tsunamiSpeed = 0.0;
    private static boolean tsunamiFinished = false;

    private static double cloudSpeed = 0.0;
    private static boolean cloudFinished = false;

    private static int maxFlyTicks = 0;
    private static int remainFlyTicks = 0;
    private static double flySpeed = 0.0;

    public static void handleJump(LocalPlayer localPlayer) {
        if (localPlayer.onGround()) {
            jumpKeyDown = true;
            fartFinished = false;
            remainSandstormTicks = maxSandstormTicks;
            sandstormFinished = false;
            remainBlizzardTicks = maxBlizzardTicks;
            blizzardFinished = false;
            tsunamiFinished = false;
            cloudFinished = false;
            remainFlyTicks = maxFlyTicks;
        } else if (localPlayer.input.jumping) {
            if (jumpKeyDown) return;

            if (fartSpeed != 0.0 && !fartFinished) {
                fartFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, fartSpeed, true);
            } else if (sandstormSpeed != 0.0 && !sandstormFinished) {
                if (remainSandstormTicks > 0) {
                    remainSandstormTicks--;
                    oneTimeJump(localPlayer, sandstormSpeed);
                } else {
                    jumpKeyDown = true;
                }
            } else if (blizzardSpeed != 0.0 && !blizzardFinished) {
                if (remainBlizzardTicks > 0) {
                    remainBlizzardTicks--;
                    oneTimeJump(localPlayer, blizzardSpeed);
                } else {
                    jumpKeyDown = true;
                }
            } else if (tsunamiSpeed != 0.0 && !tsunamiFinished) {
                tsunamiFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, tsunamiSpeed, false);
            } else if (cloudSpeed != 0.0 && !cloudFinished) {
                cloudFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, cloudSpeed, false);
            } else if (remainFlyTicks > 0) {
                remainFlyTicks--;
                Vec3 vec3 = localPlayer.getDeltaMovement();
                localPlayer.setDeltaMovement(vec3.x, flySpeed, vec3.z);
                localPlayer.hasImpulse = true;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(false, false));
            } else {
                jumpKeyDown = true;
            }
        } else {
            jumpKeyDown = false;
            sandstormFinished = remainSandstormTicks < maxSandstormTicks;
            blizzardFinished = remainBlizzardTicks < maxBlizzardTicks;
        }
    }

    private static void multiJump(LocalPlayer localPlayer, double speed, boolean isFart) {
        Vec3 vec3 = localPlayer.getDeltaMovement();
        double motionY = ((LivingEntityAccessor) localPlayer).callGetJumpPower() * speed;
        localPlayer.setDeltaMovement(vec3.x, motionY, vec3.z);
        if (localPlayer.isSprinting()) {
            float f = localPlayer.getYRot() * ((float) Math.PI / 180F);
            localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().add(-Mth.sin(f) * 0.2F, 0.0D, Mth.cos(f) * 0.2F));
        }
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(true, isFart));
    }

    private static void oneTimeJump(LocalPlayer localPlayer, double speed) {
        Vec3 vec3 = localPlayer.getDeltaMovement();
        localPlayer.setDeltaMovement(vec3.x, speed, vec3.z);
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(false, false));
    }

    public static void handleJumpPacket(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            fartSpeed = packet.fartSpeed();
            sandstormSpeed = packet.sandstormSpeed();
            maxSandstormTicks = packet.sandstormTicks();
            blizzardSpeed = packet.blizzardSpeed();
            maxBlizzardTicks = packet.blizzardTicks();
            tsunamiSpeed = packet.tsunamiSpeed();
            cloudSpeed = packet.cloudSpeed();
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
