package org.confluence.mod.client;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.client.model.entity.BulletModel;
import org.confluence.mod.client.particle.BulletParticle;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.confluence.mod.client.particle.ExtendedBreakingItemParticle;
import org.confluence.mod.client.renderer.Color;
import org.confluence.mod.client.renderer.block.ActuatorsBlockRenderer;
import org.confluence.mod.client.renderer.entity.BulletRenderer;
import org.confluence.mod.client.renderer.entity.CustomSlimeRenderer;
import org.confluence.mod.client.renderer.gui.ConfluenceOverlays;
import org.confluence.mod.entity.ConfluenceEntities;
import org.confluence.mod.item.common.SlimeBalls;


@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ConfluenceClient {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mana_hud", ConfluenceOverlays.HUD_MANA);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BulletModel.AMBER_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.AMETHYST_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.DIAMOND_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.EMERALD_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.FROST_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.RUBY_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.SAPPHIRE_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.SPARK_LAYER, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.TOPAZ_LAYER, BulletModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
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

        event.registerEntityRenderer(ConfluenceEntities.AMBER_BULLET.get(), c -> new BulletRenderer(c, "amber"));
        event.registerEntityRenderer(ConfluenceEntities.AMETHYST_BULLET.get(), c -> new BulletRenderer(c, "amethyst"));
        event.registerEntityRenderer(ConfluenceEntities.DIAMOND_BULLET.get(), c -> new BulletRenderer(c, "diamond"));
        event.registerEntityRenderer(ConfluenceEntities.EMERALD_BULLET.get(), c -> new BulletRenderer(c, "emerald"));
        event.registerEntityRenderer(ConfluenceEntities.FROST_BULLET.get(), c -> new BulletRenderer(c, "frost"));
        event.registerEntityRenderer(ConfluenceEntities.RUBY_BULLET.get(), c -> new BulletRenderer(c, "ruby"));
        event.registerEntityRenderer(ConfluenceEntities.SAPPHIRE_BULLET.get(), c -> new BulletRenderer(c, "sapphire"));
        event.registerEntityRenderer(ConfluenceEntities.SPARK_BULLET.get(), c -> new BulletRenderer(c, "spark"));
        event.registerEntityRenderer(ConfluenceEntities.TOPAZ_BULLET.get(), c -> new BulletRenderer(c, "topaz"));

        event.registerBlockEntityRenderer(ConfluenceBlocks.ACTUATORS_ENTITY.get(), ActuatorsBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ConfluenceParticles.ITEM_BLUE_SLIME.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(SlimeBalls.BLUE_SLIME_BALL.get()));
        event.registerSpecial(ConfluenceParticles.ITEM_PINK_SLIME.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(SlimeBalls.PINK_SLIME_BALL.get()));
        event.registerSpecial(ConfluenceParticles.ITEM_HONEY_SLIME.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(SlimeBalls.HONEY_SLIME_BALL.get()));

        event.registerSpriteSet(ConfluenceParticles.RUBY_BULLET.get(), BulletParticle.Provider::new);
    }

    public static final ColorResolver HALLOW_WATER_RESOLVER = (biome, x, z) -> 0x39C5BB;

    @SubscribeEvent
    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(HALLOW_WATER_RESOLVER);
    }

    public static final BlockColor HALLOW_LEAVES_COLOR = (blockState, getter, pos, tint) -> {
        if (pos == null) return Color.HALLOW_B.get();

        int i = Math.abs(pos.getX()) % 12;
        int k = Math.abs(pos.getZ()) % 12;
        Color x;
        Color z;

        if (i <= 4) x = Color.HALLOW_A.mixture(Color.HALLOW_B, i * 0.25F);
        else if (i <= 8) x = Color.HALLOW_B.mixture(Color.HALLOW_C, (i - 4) * 0.25F);
        else x = Color.HALLOW_C.mixture(Color.HALLOW_A, (i - 8) * 0.25F);

        if (k <= 4) z = Color.HALLOW_A.mixture(Color.HALLOW_B, k * 0.25F);
        else if (k <= 8) z = Color.HALLOW_B.mixture(Color.HALLOW_C, (k - 4) * 0.25F);
        else z = Color.HALLOW_C.mixture(Color.HALLOW_A, (k - 8) * 0.25F);

        return x.mixture(z, 0.5F).get();
    };

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(HALLOW_LEAVES_COLOR, ConfluenceBlocks.PEARL_LOG_BLOCKS.LEAVES.get());
    }
}
