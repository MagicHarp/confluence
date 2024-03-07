package org.confluence.mod.client;

import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.confluence.mod.client.particle.ExtendedBreakingItemParticle;
import org.confluence.mod.client.renderer.entity.CustomSlimeRenderer;
import org.confluence.mod.client.renderer.gui.ConfluenceOverlays;
import org.confluence.mod.entity.ConfluenceEntities;


@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ConfluenceClient {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mana_hud", ConfluenceOverlays.HUD_MANA);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ConfluenceEntities.BLUE_SLIME.get(), c -> new CustomSlimeRenderer(c, "textures/entity/slime/slime_blue.png"));
    }

    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {
        event.put(ConfluenceEntities.BLUE_SLIME.get(), Monster.createMonsterAttributes().build());
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ConfluenceParticles.ITEM_BLUE_SLIME.get(), new ExtendedBreakingItemParticle.BlueSlimeProvider());
    }
}
