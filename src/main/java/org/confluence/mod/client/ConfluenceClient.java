package org.confluence.mod.client;

import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.confluence.mod.client.particle.ExtendedBreakingItemParticle;
import org.confluence.mod.client.renderer.entity.CustomSlimeRenderer;
import org.confluence.mod.client.renderer.gui.ConfluenceOverlays;
import org.confluence.mod.entity.ConfluenceEntities;
import org.confluence.mod.item.ConfluenceItems;


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
        event.registerEntityRenderer(ConfluenceEntities.BLUE_SLIME.get(), c -> new CustomSlimeRenderer(c, "blue"));
        event.registerEntityRenderer(ConfluenceEntities.GREEN_SLIME.get(), c -> new CustomSlimeRenderer(c, "green"));
        event.registerEntityRenderer(ConfluenceEntities.PINK_SLIME.get(), c -> new CustomSlimeRenderer(c, "pink"));
        event.registerEntityRenderer(ConfluenceEntities.CORRUPTED_SLIME.get(), c -> new CustomSlimeRenderer(c, "corrupted"));
        event.registerEntityRenderer(ConfluenceEntities.DESERT_SLIME.get(), c -> new CustomSlimeRenderer(c, "desert"));
        event.registerEntityRenderer(ConfluenceEntities.EVIL_SLIME.get(), c -> new CustomSlimeRenderer(c, "evil"));
        event.registerEntityRenderer(ConfluenceEntities.ICE_SLIME.get(), c -> new CustomSlimeRenderer(c, "ice"));
        event.registerEntityRenderer(ConfluenceEntities.LAVA_SLIME.get(), c -> new CustomSlimeRenderer(c, "lava"));
        event.registerEntityRenderer(ConfluenceEntities.LUMINOUS_SLIME.get(), c -> new CustomSlimeRenderer(c, "luminous"));
        event.registerEntityRenderer(ConfluenceEntities.CRIMSON_SLIME.get(), c -> new CustomSlimeRenderer(c, "crimson"));
        event.registerEntityRenderer(ConfluenceEntities.PURPLE_SLIME.get(), c -> new CustomSlimeRenderer(c, "purple"));
        event.registerEntityRenderer(ConfluenceEntities.RED_SLIME.get(), c -> new CustomSlimeRenderer(c, "red"));
        event.registerEntityRenderer(ConfluenceEntities.TROPIC_SLIME.get(), c -> new CustomSlimeRenderer(c, "tropic"));
        event.registerEntityRenderer(ConfluenceEntities.YELLOW_SLIME.get(), c -> new CustomSlimeRenderer(c, "yellow"));
        event.registerEntityRenderer(ConfluenceEntities.BLACK_SLIME.get(), c -> new CustomSlimeRenderer(c, "black"));

    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ConfluenceParticles.ITEM_BLUE_SLIME.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(ConfluenceItems.SlimeBalls.BLUE_SLIME_BALL.get()));
        event.registerSpecial(ConfluenceParticles.ITEM_PINK_SLIME.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(ConfluenceItems.SlimeBalls.PINK_SLIME_BALL.get()));
    }

    public static final ColorResolver HOLY_WATER_RESOLVER = (biome, x, z) -> 0x39C5BB;

    @SubscribeEvent
    public static void registerColorHandlers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(HOLY_WATER_RESOLVER);
    }
}
