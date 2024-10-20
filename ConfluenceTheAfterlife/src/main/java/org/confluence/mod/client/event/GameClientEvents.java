package org.confluence.mod.client.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.animate.ExpertColorAnimation;
import org.confluence.mod.client.animate.MasterColorAnimation;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT, modid = Confluence.MODID)
public final class GameClientEvents {
    @SubscribeEvent
    public static void clientTick$Post(ClientTickEvent.Post event) {
        ExpertColorAnimation.INSTANCE.updateColor();
        MasterColorAnimation.INSTANCE.updateColor();
    }

    @SubscribeEvent
    public static void movementInputUpdate(MovementInputUpdateEvent event) {

    }

    @SubscribeEvent
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {

    }

    @SubscribeEvent
    public static void leftClick(InputEvent.InteractionKeyMappingTriggered event) {

    }

    @SubscribeEvent
    public static void itemToolTip(ItemTooltipEvent event) {

    }

    @SubscribeEvent
    public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {

    }

    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog event) {

    }

    @SubscribeEvent
    public static void computeFogColor(ViewportEvent.ComputeFogColor event) {

    }

    @SubscribeEvent
    public static void fov(ComputeFovModifierEvent event) {

    }

    @SubscribeEvent
    public static void interactionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {

    }

    @SubscribeEvent
    public static void renderGuiOverlay$Pre(RenderGuiLayerEvent.Pre event) {

    }

    @SubscribeEvent
    public static void beforeRenderLiving(RenderLivingEvent.Pre<?, ?> event) {

    }

    @SubscribeEvent
    public static void afterRenderLiving(RenderLivingEvent.Post<?, ?> event) {

    }
}
