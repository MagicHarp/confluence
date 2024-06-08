package org.confluence.mod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.BeeProjectileModel;
import org.confluence.mod.client.renderer.entity.BeeProjectileRenderer;
import org.confluence.mod.client.renderer.entity.StarCloakEntityRenderer;
import org.confluence.mod.client.renderer.gui.ConfluenceOverlays;

import static org.confluence.mod.entity.ModEntities.BEE_PROJECTILE;
import static org.confluence.mod.entity.ModEntities.STAR_CLOAK;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(CuriosClient::registerRenderers);
    }

    @SubscribeEvent
    public static void keyBinding(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.METAL_DETECTOR.get());
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("info_hud", ConfluenceOverlays.INFO_HUD);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CuriosClient.registerLayers(event::registerLayerDefinition);
        event.registerLayerDefinition(BeeProjectileModel.LAYER_LOCATION, BeeProjectileModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BEE_PROJECTILE.get(), BeeProjectileRenderer::new);
        event.registerEntityRenderer(STAR_CLOAK.get(), StarCloakEntityRenderer::new);
    }
}
