package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.integration.airhop.AirHopHelper;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.mixin.accessor.LivingEntityAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.PlayerJumpPacketC2S;
import org.confluence.mod.network.s2c.PlayerFlyPacketS2C;
import org.confluence.mod.network.s2c.PlayerJumpPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class PlayerJumpHandler {
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

    private static double flySpeed = 0.0;
    private static int maxFlyTicks = 0;
    private static int remainFlyTicks = 0;
    private static boolean couldGlide = false;

    public static boolean onFly = false;

    public static void handle(LocalPlayer localPlayer, boolean jumping) {
        if (StepStoolHandler.onStool()) return;

        if (localPlayer.onGround()) {
            flushState(true);
        } else if (jumping) {
            if (AirHopHelper.isLoaded() && AirHopHelper.notFinishJump(localPlayer)) {
                jumpKeyDown = true;
                return;
            }

            if (couldGlide) {
                if (remainFlyTicks-- > 0) {
                    onFly = true;
                    fly(localPlayer, flySpeed);
                } else if (!localPlayer.getAbilities().flying && localPlayer.getDeltaMovement().y < -0.15) {
                    onFly = false;
                    fly(localPlayer, -0.2);
                }
            }
            if (jumpKeyDown) return;
            if (couldGlide && remainFlyTicks > 0) return;

            if (!fartFinished && fartSpeed > 0.0) {
                fartFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, fartSpeed);
                localPlayer.playSound(ModSoundEvents.FART_SOUND.get());
            } else if (!sandstormFinished && sandstormSpeed > 0.0) {
                if (remainSandstormTicks-- > 0) oneTimeJump(localPlayer, sandstormSpeed);
                else jumpKeyDown = true;
            } else if (!blizzardFinished && blizzardSpeed > 0.0) {
                if (remainBlizzardTicks-- > 0) oneTimeJump(localPlayer, blizzardSpeed);
                else jumpKeyDown = true;
            } else if (!tsunamiFinished && tsunamiSpeed > 0.0) {
                tsunamiFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, tsunamiSpeed);
                localPlayer.playSound(ModSoundEvents.DOUBLE_JUMP.get());
            } else if (!cloudFinished && cloudSpeed > 0.0) {
                cloudFinished = true;
                jumpKeyDown = true;
                multiJump(localPlayer, cloudSpeed);
                localPlayer.playSound(ModSoundEvents.DOUBLE_JUMP.get());
            } else if (remainFlyTicks-- > 0) {
                fly(localPlayer, flySpeed);
            } else {
                jumpKeyDown = true;
            }
        } else {
            jumpKeyDown = false;
            sandstormFinished = remainSandstormTicks < maxSandstormTicks;
            blizzardFinished = remainBlizzardTicks < maxBlizzardTicks;
            onFly = false;
        }
    }

    public static void flushState(boolean jumpKey) {
        jumpKeyDown = jumpKey;
        fartFinished = false;
        remainSandstormTicks = maxSandstormTicks;
        sandstormFinished = false;
        remainBlizzardTicks = maxBlizzardTicks;
        blizzardFinished = false;
        tsunamiFinished = false;
        cloudFinished = false;
        remainFlyTicks = maxFlyTicks;
    }

    private static void multiJump(LocalPlayer localPlayer, double speed) {
        Vec3 vec3 = localPlayer.getDeltaMovement();
        double motionY = ((LivingEntityAccessor) localPlayer).callGetJumpPower() * speed;
        localPlayer.setDeltaMovement(vec3.x, motionY, vec3.z);
        if (localPlayer.isSprinting()) {
            float f = localPlayer.getYRot() * Mth.DEG_TO_RAD;
            localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().add(-Mth.sin(f) * 0.2, 0.0, Mth.cos(f) * 0.2));
        }
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(true, true, (float) speed));
    }

    private static void oneTimeJump(LocalPlayer localPlayer, double speed) {
        Vec3 vec3 = localPlayer.getDeltaMovement();
        double y = vec3.y;
        if (y < speed) {
            y += speed / 2.5;
        } else {
            y = speed;
        }
        localPlayer.setDeltaMovement(vec3.x, y, vec3.z);
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(false, true, (float) y));
    }

    private static void fly(LocalPlayer localPlayer, double speed) {
        Vec3 vec3 = localPlayer.getDeltaMovement();
        double y = vec3.y;
        if (y < speed) {
            y += speed / 2.5;
        } else {
            y = speed;
        }
        localPlayer.setDeltaMovement(vec3.x, y, vec3.z);
        localPlayer.hasImpulse = true;
        localPlayer.resetFallDistance();
        NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(false, true, (float) y));
    }

    public static void handleJumpPacket(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (packet.fartSpeed() > -1.5) fartSpeed = packet.fartSpeed();
            sandstormSpeed = packet.sandstormSpeed();
            maxSandstormTicks = packet.sandstormTicks();
            blizzardSpeed = packet.blizzardSpeed();
            maxBlizzardTicks = packet.blizzardTicks();
            if (packet.tsunamiSpeed() > -1.5) tsunamiSpeed = packet.tsunamiSpeed();
            cloudSpeed = packet.cloudSpeed();
        });
        context.setPacketHandled(true);
    }

    public static void handleFlyPacket(PlayerFlyPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxFlyTicks = packet.maxFlyTicks();
            flySpeed = packet.flySpeed();
            couldGlide = packet.glide();
        });
        context.setPacketHandled(true);
    }

    public static boolean isOnGlide() {
        return onFly;
    }
}
