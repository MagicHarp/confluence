package org.confluence.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.combat.IAutoAttack;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ForgeClient {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        if (event.phase == TickEvent.Phase.START) return;
        GravitationEffect.tick(localPlayer);
        if (localPlayer == null) return;
        InformationHandler.update(localPlayer);
        IAutoAttack.apply(minecraft, localPlayer);

        ModRarity.Animate.doUpdateExpertColor();
        ModRarity.Animate.doUpdateMasterColor();
    }

    @SubscribeEvent
    public static void movementInputUpdate(MovementInputUpdateEvent event) {
        LocalPlayer localPlayer = (LocalPlayer) event.getEntity();
        boolean jumping = event.getInput().jumping;
        if (GravitationEffect.isHasGlobe() || localPlayer.hasEffect(ModEffects.GRAVITATION.get())) {
            GravitationEffect.handle(localPlayer, jumping);
        } else {
            GravitationEffect.expire();
            PlayerJumpHandler.handle(localPlayer, jumping);
        }
    }

    @SubscribeEvent
    public static void cameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (GravitationEffect.isShouldRot()) {
            event.setRoll(180.0F);
        }
    }
}
