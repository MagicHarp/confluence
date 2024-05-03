package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.GravitationPacketC2S;
import org.confluence.mod.network.s2c.GravityGlobePacketS2C;

import java.util.UUID;
import java.util.function.Supplier;

public class GravitationEffect extends MobEffect {
    public static final UUID GRAVITY_UUID = UUID.fromString("30AE55C6-016B-09A2-74B8-96C68C22AFE1");
    public static final ImmutableMultimap<Attribute, AttributeModifier> GRAVITY = ImmutableMultimap.of(
        ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(GRAVITY_UUID, "Gravitation", -2.0, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );
    private static boolean keyDown = false;
    private static boolean shouldRot = false;
    private static boolean hasGlobe = false;

    public GravitationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA00AA);
    }

    public static void handle(LocalPlayer localPlayer, boolean jumping) {
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

    public static void handleGlobe(GravityGlobePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasGlobe = packet.has());
        context.setPacketHandled(true);
    }

    public static boolean isHasGlobe() {
        return hasGlobe;
    }
}
