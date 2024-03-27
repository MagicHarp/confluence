package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.mixin.LivingEntityAccessor;
import org.confluence.mod.network.FallDistancePacketC2S;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.PlayerJumpPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PlayerJumpHandler {
    private static boolean firstJumped = false;
    private static int maxJump = 0;
    private static int jumpCount = 0;
    private static double multiY = 0.0D;

    public static void handleJump(LocalPlayer localPlayer) {
        if (localPlayer.onGround()) {
            jumpCount = maxJump;
            firstJumped = false;
        } else if (localPlayer.input.jumping && localPlayer.getDeltaMovement().y < 0.16) {
            if (firstJumped && jumpCount > 0) {
                Vec3 vec3 = localPlayer.getDeltaMovement();
                double motionY = ((LivingEntityAccessor) localPlayer).callGetJumpPower() * multiY;
                localPlayer.setDeltaMovement(vec3.x, motionY, vec3.z);
                if (localPlayer.isSprinting()) {
                    float f = localPlayer.getYRot() * ((float) Math.PI / 180F);
                    localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().add(-Mth.sin(f) * 0.2F, 0.0D, Mth.cos(f) * 0.2F));
                }

                jumpCount--;
                localPlayer.hasImpulse = true;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(true));
            }
            firstJumped = true;
        }
    }

    public static void handlePacket(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxJump = packet.maxJump();
            multiY = packet.multiY();
        });
        context.setPacketHandled(true);
    }

    public static boolean mayFly() {
        return jumpCount == 0;
    }
}
