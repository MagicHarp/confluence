package org.confluence.mod.client;

import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.confluence.mod.client.model.entity.BeeProjectileModel;
import org.confluence.mod.client.model.entity.StepStoolModel;
import org.confluence.mod.client.particle.CurrentColorDustParticle;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.client.renderer.AchievementDisplay;
import org.confluence.mod.client.renderer.AchievementToast;
import org.confluence.mod.client.renderer.entity.BeeProjectileRenderer;
import org.confluence.mod.client.renderer.entity.StarCloakEntityRenderer;
import org.confluence.mod.client.renderer.entity.StepStoolRenderer;
import org.confluence.mod.client.renderer.gui.InfoHudOverlay;
import org.confluence.mod.client.renderer.gui.WorkshopScreen;
import org.confluence.mod.menu.ModMenus;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.entity.ModEntities.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CuriosClient.registerRenderers();
            ClientConfigs.onLoad();
            AchievementToast.registerToast(new ResourceLocation(MODID, "boots_of_the_hero"), new AchievementToast(
                    new ResourceLocation(MODID, "textures/achievement/boots_of_the_hero.png"),
                    new AchievementDisplay(FrameType.CHALLENGE,
                            Component.translatable("achievements.confluence.boots_of_the_hero.title"),
                            Component.translatable("achievements.confluence.boots_of_the_hero.description")
                    )));
            MenuScreens.register(ModMenus.WORKSHOP.get(), WorkshopScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("info_hud", new InfoHudOverlay());
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CuriosClient.registerLayers(event::registerLayerDefinition);
        event.registerLayerDefinition(BeeProjectileModel.LAYER_LOCATION, BeeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(StepStoolModel.LAYER_LOCATION, StepStoolModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BEE_PROJECTILE.get(), BeeProjectileRenderer::new);
        event.registerEntityRenderer(STAR_CLOAK.get(), StarCloakEntityRenderer::new);
        event.registerEntityRenderer(STEP_STOOL.get(), StepStoolRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.CURRENT_DUST.get(), CurrentColorDustParticle.Provider::new);
    }
}
