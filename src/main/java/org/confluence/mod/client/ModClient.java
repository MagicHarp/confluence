package org.confluence.mod.client;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.curio.SpectreGogglesModel;
import org.confluence.mod.client.model.entity.BulletModel;
import org.confluence.mod.client.particle.BulletParticle;
import org.confluence.mod.client.particle.ExtendedBreakingItemParticle;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.client.renderer.Color;
import org.confluence.mod.client.renderer.block.ActuatorsBlockRenderer;
import org.confluence.mod.client.renderer.curio.CurioRenderers;
import org.confluence.mod.client.renderer.entity.BulletRenderer;
import org.confluence.mod.client.renderer.entity.CustomSlimeRenderer;
import org.confluence.mod.client.renderer.entity.FallingStarRenderer;
import org.confluence.mod.client.renderer.gui.ConfluenceOverlays;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.common.Gels;


@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CurioRenderers.register();
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_LOG_BLOCKS.DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_LOG_BLOCKS.TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PALM_LOG_BLOCKS.DOOR.get(), RenderType.cutout());
        });
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelow(VanillaGuiOverlay.ITEM_NAME.id(), "mana_hud", ConfluenceOverlays.HUD_MANA);
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

        event.registerLayerDefinition(SpectreGogglesModel.LAYER_LOCATION, SpectreGogglesModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLUE_SLIME.get(), c -> new CustomSlimeRenderer(c, "blue"));
        event.registerEntityRenderer(ModEntities.GREEN_SLIME.get(), c -> new CustomSlimeRenderer(c, "green"));
        event.registerEntityRenderer(ModEntities.PINK_SLIME.get(), c -> new CustomSlimeRenderer(c, "pink"));
        event.registerEntityRenderer(ModEntities.CORRUPTED_SLIME.get(), c -> new CustomSlimeRenderer(c, "corrupted"));
        event.registerEntityRenderer(ModEntities.DESERT_SLIME.get(), c -> new CustomSlimeRenderer(c, "desert"));
        event.registerEntityRenderer(ModEntities.EVIL_SLIME.get(), c -> new CustomSlimeRenderer(c, "evil"));
        event.registerEntityRenderer(ModEntities.ICE_SLIME.get(), c -> new CustomSlimeRenderer(c, "ice"));
        event.registerEntityRenderer(ModEntities.LAVA_SLIME.get(), c -> new CustomSlimeRenderer(c, "lava"));
        event.registerEntityRenderer(ModEntities.LUMINOUS_SLIME.get(), c -> new CustomSlimeRenderer(c, "luminous"));
        event.registerEntityRenderer(ModEntities.CRIMSON_SLIME.get(), c -> new CustomSlimeRenderer(c, "crimson"));
        event.registerEntityRenderer(ModEntities.PURPLE_SLIME.get(), c -> new CustomSlimeRenderer(c, "purple"));
        event.registerEntityRenderer(ModEntities.RED_SLIME.get(), c -> new CustomSlimeRenderer(c, "red"));
        event.registerEntityRenderer(ModEntities.TROPIC_SLIME.get(), c -> new CustomSlimeRenderer(c, "tropic"));
        event.registerEntityRenderer(ModEntities.YELLOW_SLIME.get(), c -> new CustomSlimeRenderer(c, "yellow"));
        event.registerEntityRenderer(ModEntities.BLACK_SLIME.get(), c -> new CustomSlimeRenderer(c, "black"));

        event.registerEntityRenderer(ModEntities.AMBER_BULLET.get(), c -> new BulletRenderer(c, "amber"));
        event.registerEntityRenderer(ModEntities.AMETHYST_BULLET.get(), c -> new BulletRenderer(c, "amethyst"));
        event.registerEntityRenderer(ModEntities.DIAMOND_BULLET.get(), c -> new BulletRenderer(c, "diamond"));
        event.registerEntityRenderer(ModEntities.EMERALD_BULLET.get(), c -> new BulletRenderer(c, "emerald"));
        event.registerEntityRenderer(ModEntities.FROST_BULLET.get(), c -> new BulletRenderer(c, "frost"));
        event.registerEntityRenderer(ModEntities.RUBY_BULLET.get(), c -> new BulletRenderer(c, "ruby"));
        event.registerEntityRenderer(ModEntities.SAPPHIRE_BULLET.get(), c -> new BulletRenderer(c, "sapphire"));
        event.registerEntityRenderer(ModEntities.SPARK_BULLET.get(), c -> new BulletRenderer(c, "spark"));
        event.registerEntityRenderer(ModEntities.TOPAZ_BULLET.get(), c -> new BulletRenderer(c, "topaz"));

        event.registerEntityRenderer(ModEntities.FALLING_STAR_ITEM_ENTITY.get(), FallingStarRenderer::new);

        event.registerBlockEntityRenderer(ModBlocks.ACTUATORS_ENTITY.get(), ActuatorsBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticles.ITEM_BLUE_GEL.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(Gels.BLUE_GEL.get()));
        event.registerSpecial(ModParticles.ITEM_PINK_GEL.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(Gels.PINK_GEL.get()));
        event.registerSpecial(ModParticles.ITEM_HONEY_GEL.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(Gels.HONEY_GEL.get()));
        event.registerSpecial(ModParticles.ITEM_FROZEN_GEL.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(Gels.FROZEN_GEL.get()));

        event.registerSpriteSet(ModParticles.RUBY_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.AMBER_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.TOPAZ_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.EMERALD_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.DIAMOND_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.AMETHYST_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SAPPHIRE_BULLET.get(), BulletParticle.Provider::new);
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
        event.register(HALLOW_LEAVES_COLOR, ModBlocks.PEARL_LOG_BLOCKS.LEAVES.get());
    }
}
