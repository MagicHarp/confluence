package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.mixin.client.accessor.LocalPlayerAccessor;
import org.confluence.mod.mixinauxiliary.IEntity;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.GravitationPacketC2S;
import org.confluence.mod.network.s2c.BroadcastGravitationRotPacketS2C;
import org.confluence.mod.network.s2c.GravityGlobePacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class GravitationHandler {
    public static final Vec3 DOWN = new Vec3(0.0, -0.3000001, 0.0);
    private static boolean keyDown = false;
    private static boolean shouldRot = false;
    private static boolean hasGlobe = false;

    public static void handle(LocalPlayer localPlayer, boolean jumping) {
        if (StepStoolHandler.onStool() || localPlayer.getAbilities().flying) return;

        if (jumping) {
            if (!keyDown) {
                shouldRot = !shouldRot;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new GravitationPacketC2S(shouldRot));
            }
            keyDown = true;
        } else {
            keyDown = false;
        }
    }

    public static void expire() {
        if (shouldRot) {
            shouldRot = false;
            NetworkHandler.CHANNEL.sendToServer(new GravitationPacketC2S(false));
        }
    }

    public static boolean isShouldRot() {
        return shouldRot;
    }

    public static void tick(LocalPlayer localPlayer) {
        if (localPlayer == null) {
            shouldRot = false;
            hasGlobe = false;
        } else if (localPlayer.getY() > localPlayer.level().getMaxBuildHeight()) {
            expire();
        }
    }

    public static void unCrouching(Player localPlayer) {
        if (shouldRot && localPlayer.onGround() && localPlayer.isCrouching() && !localPlayer.isShiftKeyDown()) {
            localPlayer.move(MoverType.SELF, DOWN);
            localPlayer.setPose(Pose.STANDING);
            ((LocalPlayerAccessor) localPlayer).setCrouching(false);
        }
    }

    public static void handleGlobe(GravityGlobePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasGlobe = packet.has());
        context.setPacketHandled(true);
    }

    public static boolean isHasGlobe() {
        return hasGlobe;
    }

    public static void handleRemoteRot(BroadcastGravitationRotPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            Entity entity = localPlayer.level().getEntity(packet.entityId());
            if (entity != null) {
                ((IEntity) entity).confluence$setShouldRot(packet.enabled());
            }
        });
        context.setPacketHandled(true);
    }
}
