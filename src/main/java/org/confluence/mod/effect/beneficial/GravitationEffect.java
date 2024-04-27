package org.confluence.mod.effect.beneficial;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.PlayerJumpPacketC2S;

import java.util.function.Consumer;

public class GravitationEffect extends MobEffect {
    private static boolean keyDown = false;
    private static boolean shouldRot = false;

    public GravitationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA00AA);
    }

    public static void handle(LocalPlayer localPlayer) {
        if (localPlayer.input.jumping) {
            if (!keyDown) {
                shouldRot = !shouldRot;
                NetworkHandler.CHANNEL.sendToServer(new PlayerJumpPacketC2S(false));
            }
            keyDown = true;
        } else {
            keyDown = false;
        }
    }

    public static void zRot(Consumer<Float> consumer) {
        if (shouldRot) consumer.accept(180.0F);
    }

    public static void expire() {
        shouldRot = false;
    }

    public static boolean isShouldRot() {
        return shouldRot;
    }
}
