package org.confluence.mod.client.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.renderer.gui.ManaHudLayer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = Confluence.MODID)
public final class ModClientEvents {
    @SubscribeEvent
    public static void onRegisterClientReload(RegisterClientReloadListenersEvent event) {

    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelow(VanillaGuiLayers.SELECTED_ITEM_NAME, Confluence.asResource("mana_hud"), new ManaHudLayer());
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {

    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {

    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

    }

    @SubscribeEvent
    public static void registerEntitySpectatorShaders(RegisterEntitySpectatorShadersEvent event) {

    }
}
