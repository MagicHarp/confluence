package org.confluence.mod.client.player;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.PlayerJumpPacketC2S;
import org.confluence.mod.network.PlayerJumpPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PlayerJump {
    private static boolean jumped = false;
    private static int maxJump = 0;
    private static int jumpCount = 0;

    public static void handleJump(LocalPlayer localPlayer) {
        if (localPlayer.onGround()) {
            jumpCount = maxJump;
        } else if (localPlayer.input.jumping) {
            if (!jumped && jumpCount > 0 && localPlayer.getDeltaMovement().y < 0.333) {
                localPlayer.jumpFromGround();
                localPlayer.fallDistance = 0.0F;
                NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(0.0F));
                jumpCount--;
            }
            jumped = true;
        } else {
            jumped = false;
        }
    }

    public static void handlePacket(PlayerJumpPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> maxJump = packet.maxJump());
        context.setPacketHandled(true);
    }
}
