package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.FallDistancePacketC2S;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.PlayerFlyPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PlayerFlyHandler {
    private static int maxFlyTicks = 0;
    private static int flyTicks = 0;
    private static double maxFlySpeed = 0.0D;

    public static void handleFly(LocalPlayer localPlayer){
        if (localPlayer.onGround()) {
            flyTicks = maxFlyTicks;
        } else if (localPlayer.input.jumping) {
            if (PlayerJumpHandler.mayFly() && flyTicks > 0) {
                Vec3 vec3 = localPlayer.getDeltaMovement();
                localPlayer.setDeltaMovement(vec3.x, maxFlySpeed, vec3.z);
                flyTicks--;
                localPlayer.hasImpulse = true;
                localPlayer.resetFallDistance();
                NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(false));
            }
        }
    }

    public static void handlePacket(PlayerFlyPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxFlyTicks = packet.maxFlyTicks();
            maxFlySpeed = packet.maxFlySpeed();
        });
        context.setPacketHandled(true);
    }
}
