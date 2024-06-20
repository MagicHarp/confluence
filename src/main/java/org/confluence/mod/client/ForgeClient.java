package org.confluence.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.color.AnimateColor;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.mixin.client.MinecraftAccessor;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ForgeClient {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        if (event.phase == TickEvent.Phase.START) return;
        GravitationHandler.tick(localPlayer);
        if (localPlayer == null) return;
        InformationHandler.handle(localPlayer);
        IAutoAttack.apply(minecraft, localPlayer);

        AnimateColor.doUpdateExpertColor();
        AnimateColor.doUpdateMasterColor();
    }

    @SubscribeEvent
    public static void movementInputUpdate(MovementInputUpdateEvent event) {
        LocalPlayer localPlayer = (LocalPlayer) event.getEntity();
        boolean jumping = event.getInput().jumping;
        if (GravitationHandler.isHasGlobe() || localPlayer.hasEffect(ModEffects.GRAVITATION.get())) {
            GravitationHandler.handle(localPlayer, jumping);
        } else {
            GravitationHandler.expire();
            PlayerJumpHandler.handle(localPlayer, jumping);
        }
    }

    @SubscribeEvent
    public static void cameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (GravitationHandler.isShouldRot()) {
            event.setRoll(180.0F);
        }
    }

    @SubscribeEvent
    public static void fov(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        if (ClientPacketHandler.isHasScope() && player.isCrouching() &&
            Minecraft.getInstance().options.getCameraType().isFirstPerson() &&
            player.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.RANGED_WEAPON)
        ) {
            event.setNewFovModifier(0.1F);
        }
    }

    @SubscribeEvent
    public static void interactionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isUseItem()) {
            MinecraftAccessor instance = (MinecraftAccessor) Minecraft.getInstance();
            int delay = instance.getRightClickDelay() - ClientPacketHandler.getRightClickSubtractor();
            instance.setRightClickDelay(Math.max(0, delay));
        }
    }
}
