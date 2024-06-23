package org.confluence.mod.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.minecraftforge.client.textures.ITextureAtlasSpriteLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.color.IntegerRGB;
import org.confluence.mod.client.model.entity.*;
import org.confluence.mod.client.model.entity.fishing.BaseFishingHookModel;
import org.confluence.mod.client.model.entity.fishing.BloodyFishingHookModel;
import org.confluence.mod.client.model.entity.fishing.GlowingFishingHookModel;
import org.confluence.mod.client.model.entity.fishing.HotlineFishingHookModel;
import org.confluence.mod.client.model.entity.hook.BaseHookModel;
import org.confluence.mod.client.model.entity.hook.SkeletronHandModel;
import org.confluence.mod.client.model.entity.hook.WebSlingerModel;
import org.confluence.mod.client.particle.BulletParticle;
import org.confluence.mod.client.particle.ExtendedBreakingItemParticle;
import org.confluence.mod.client.particle.FlameFlowerParticle;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.client.renderer.block.ActuatorsBlockRenderer;
import org.confluence.mod.client.renderer.block.AltarBlockRenderer;
import org.confluence.mod.client.renderer.block.LifeCrystalBlockRenderer;
import org.confluence.mod.client.renderer.entity.*;
import org.confluence.mod.client.renderer.entity.fishing.BaseFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.fishing.BloodyFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.fishing.GlowingFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.fishing.HotlineFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.hook.*;
import org.confluence.mod.client.renderer.gui.ConfluenceOverlays;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.misc.ModArmPoses;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.entity.ModEntities.*;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModArmPoses.initialize();
            CuriosClient.registerRenderers();

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_LOG_BLOCKS.DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_LOG_BLOCKS.TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PALM_LOG_BLOCKS.DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SHIMMER.fluid().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SHIMMER.flowingFluid().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HONEY.fluid().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HONEY.flowingFluid().get(), RenderType.translucent());

            ItemProperties.register(CurioItems.SPECTRE_GOGGLES.get(), new ResourceLocation(MODID, "enable"), (itemStack, level, living, speed) ->
                itemStack.getTag() != null && itemStack.getTag().getBoolean("enable") ? 1.0F : 0.0F);
            ItemProperties.register(CurioItems.MECHANICAL_LENS.get(), new ResourceLocation(MODID, "enable"), (itemStack, level, living, speed) ->
                itemStack.getTag() != null && itemStack.getTag().getBoolean("enable") ? 1.0F : 0.0F);
            FishingPoles.registerCast();
        });
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelow(VanillaGuiOverlay.ITEM_NAME.id(), "mana_hud", ConfluenceOverlays.HUD_MANA);
        event.registerAboveAll("info_hud", ConfluenceOverlays.INFO_HUD);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CuriosClient.registerLayers(event::registerLayerDefinition);

        event.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BeeProjectileModel.LAYER_LOCATION, BeeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(BoulderModel.LAYER_LOCATION, BoulderModel::createBodyLayer);
        event.registerLayerDefinition(MoneyHoleModel.LAYER_LOCATION, MoneyHoleModel::createBodyLayer);
        event.registerLayerDefinition(AmmoModel.LAYER_LOCATION, AmmoModel::createBodyLayer);

        event.registerLayerDefinition(BaseHookModel.LAYER_LOCATION, BaseHookModel::createBodyLayer);
        event.registerLayerDefinition(WebSlingerModel.LAYER_LOCATION, WebSlingerModel::createBodyLayer);
        event.registerLayerDefinition(SkeletronHandModel.LAYER_LOCATION, SkeletronHandModel::createBodyLayer);

        event.registerLayerDefinition(BaseFishingHookModel.WOOD, BaseFishingHookModel::createWoodLayer);
        event.registerLayerDefinition(BaseFishingHookModel.REINFORCED, BaseFishingHookModel::createReinforcedLayer);
        event.registerLayerDefinition(BaseFishingHookModel.FISHER_OF_SOULS, BaseFishingHookModel::createFisherOfSoulsLayer);
        event.registerLayerDefinition(BaseFishingHookModel.FLESHCATCHER, BaseFishingHookModel::createFleshcatcherLayer);
        event.registerLayerDefinition(BaseFishingHookModel.SCARAB, BaseFishingHookModel::createScarabLayer);
        event.registerLayerDefinition(BloodyFishingHookModel.LAYER_LOCATION, BloodyFishingHookModel::createBodyLayer);
        event.registerLayerDefinition(BaseFishingHookModel.FIBERGLASS, BaseFishingHookModel::createFiberglassLayer);
        event.registerLayerDefinition(BaseFishingHookModel.MECHANICS, BaseFishingHookModel::createMechanicsLayer);
        event.registerLayerDefinition(BaseFishingHookModel.SITTING_DUCKS, BaseFishingHookModel::createSittingDucksLayer);
        event.registerLayerDefinition(HotlineFishingHookModel.LAYER_LOCATION, HotlineFishingHookModel::createBodyLayer);
        event.registerLayerDefinition(BaseFishingHookModel.GOLDEN, BaseFishingHookModel::createGoldenLayer);
        event.registerLayerDefinition(GlowingFishingHookModel.MOSS, GlowingFishingHookModel::createMossLayer);
        event.registerLayerDefinition(GlowingFishingHookModel.COMMON, GlowingFishingHookModel::createCommonLayer);
        event.registerLayerDefinition(GlowingFishingHookModel.GLOWING, GlowingFishingHookModel::createGlowingLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BLUE_SLIME.get(), c -> new CustomSlimeRenderer(c, "blue"));
        event.registerEntityRenderer(GREEN_SLIME.get(), c -> new CustomSlimeRenderer(c, "green"));
        event.registerEntityRenderer(PINK_SLIME.get(), c -> new CustomSlimeRenderer(c, "pink"));
        event.registerEntityRenderer(CORRUPTED_SLIME.get(), c -> new CustomSlimeRenderer(c, "corrupted"));
        event.registerEntityRenderer(DESERT_SLIME.get(), c -> new CustomSlimeRenderer(c, "desert"));
        event.registerEntityRenderer(EVIL_SLIME.get(), c -> new CustomSlimeRenderer(c, "evil"));
        event.registerEntityRenderer(ICE_SLIME.get(), c -> new CustomSlimeRenderer(c, "ice"));
        event.registerEntityRenderer(LAVA_SLIME.get(), c -> new CustomSlimeRenderer(c, "lava"));
        event.registerEntityRenderer(LUMINOUS_SLIME.get(), c -> new CustomSlimeRenderer(c, "luminous"));
        event.registerEntityRenderer(CRIMSON_SLIME.get(), c -> new CustomSlimeRenderer(c, "crimson"));
        event.registerEntityRenderer(PURPLE_SLIME.get(), c -> new CustomSlimeRenderer(c, "purple"));
        event.registerEntityRenderer(RED_SLIME.get(), c -> new CustomSlimeRenderer(c, "red"));
        event.registerEntityRenderer(TROPIC_SLIME.get(), c -> new CustomSlimeRenderer(c, "tropic"));
        event.registerEntityRenderer(YELLOW_SLIME.get(), c -> new CustomSlimeRenderer(c, "yellow"));
        event.registerEntityRenderer(BLACK_SLIME.get(), c -> new CustomSlimeRenderer(c, "black"));

        event.registerEntityRenderer(DEMON_EYE.get(), DemonEyeRenderer::new);

        event.registerEntityRenderer(BASE_BULLET.get(), BulletRenderer::new);
        event.registerEntityRenderer(FALLING_STAR_ITEM_ENTITY.get(), FallingStarRenderer::new);
        event.registerEntityRenderer(BEE_PROJECTILE.get(), BeeProjectileRenderer::new);
        event.registerEntityRenderer(HOTLINE_FISHING_HOOK.get(), HotlineFishingHookRenderer::new);
        event.registerEntityRenderer(BASE_FISHING_HOOK.get(), BaseFishingHookRenderer::new);
        event.registerEntityRenderer(BLOODY_FISHING_HOOK.get(), BloodyFishingHookRenderer::new);
        event.registerEntityRenderer(CURIO_FISHING_HOOK.get(), GlowingFishingHookRenderer::new);
        event.registerEntityRenderer(EFFECT_THROWN_POTION.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(STAR_CLOAK.get(), StarCloakEntityRenderer::new);
        event.registerEntityRenderer(BOULDER.get(), BoulderRenderer::new);
        event.registerEntityRenderer(MONEY_HOLE.get(), MoneyHoleRenderer::new);

        event.registerEntityRenderer(BASE_HOOK.get(), BaseHookRenderer::new);
        event.registerEntityRenderer(WEB_SLINGER.get(), WebSlingerRenderer::new);
        event.registerEntityRenderer(SKELETRON_HAND.get(), SkeletronHandRenderer::new);
        event.registerEntityRenderer(SLIME_HOOK.get(), SlimeHookRenderer::new);
        event.registerEntityRenderer(FISH_HOOK.get(), FishHookRenderer::new);
        event.registerEntityRenderer(IVY_WHIP.get(), IvyWhipRenderer::new);
        event.registerEntityRenderer(BAT_HOOK.get(), BatHookRenderer::new);
        event.registerEntityRenderer(CANDY_CANE_HOOK.get(), CandyCaneHookRenderer::new);
        event.registerEntityRenderer(DUAL_HOOK.get(), DualHookRenderer::new);
        event.registerEntityRenderer(HOOK_OF_DISSONANCE.get(), HookOfDissonanceRenderer::new);
        event.registerEntityRenderer(THORN_HOOK.get(), ThornHookRenderer::new);
        event.registerEntityRenderer(MIMIC_HOOK.get(), MimicHookRenderer::new);
        event.registerEntityRenderer(ANTI_GRAVITY_HOOK.get(), AntiGravityHookRenderer::new);
        event.registerEntityRenderer(SPOOKY_HOOK.get(), SpookyHookRenderer::new);
        event.registerEntityRenderer(CHRISTMAS_HOOK.get(), ChristmasHookRenderer::new);
        event.registerEntityRenderer(LUNAR_HOOK.get(), LunarHookRenderer::new);
        /* todo 静止钩 */

        event.registerEntityRenderer(BASE_AMMO.get(), AmmoRenderer::new);

        event.registerBlockEntityRenderer(ModBlocks.ACTUATORS_ENTITY.get(), ActuatorsBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.LIFE_CRYSTAL_BLOCK_ENTITY.get(), LifeCrystalBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticles.ITEM_GEL.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(Materials.GEL.get()));
        event.registerSpecial(ModParticles.ITEM_PINK_GEL.get(), new ExtendedBreakingItemParticle.SlimeBallProvider(Materials.PINK_GEL.get()));

        event.registerSpriteSet(ModParticles.RUBY_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.AMBER_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.TOPAZ_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.EMERALD_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.DIAMOND_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.AMETHYST_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SAPPHIRE_BULLET.get(), BulletParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FLAMEFLOWER_BLOOM.get(), FlameFlowerParticle.Provider::new);
    }

    public static final ColorResolver HALLOW_WATER_RESOLVER = (biome, x, z) -> 0xFF96FF;

    @SubscribeEvent
    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(HALLOW_WATER_RESOLVER);
    }

    public static final BlockColor HALLOW_LEAVES_COLOR = (blockState, getter, pos, tint) -> {
        if (pos == null) return -1;

        int i = Math.abs(pos.getX()) % 12;
        int k = Math.abs(pos.getZ()) % 12;
        IntegerRGB x;
        IntegerRGB z;

        if (i <= 4) x = IntegerRGB.HALLOW_A.mixture(IntegerRGB.HALLOW_B, i * 0.25F);
        else if (i <= 8) x = IntegerRGB.HALLOW_B.mixture(IntegerRGB.HALLOW_C, (i - 4) * 0.25F);
        else x = IntegerRGB.HALLOW_C.mixture(IntegerRGB.HALLOW_A, (i - 8) * 0.25F);

        if (k <= 4) z = IntegerRGB.HALLOW_A.mixture(IntegerRGB.HALLOW_B, k * 0.25F);
        else if (k <= 8) z = IntegerRGB.HALLOW_B.mixture(IntegerRGB.HALLOW_C, (k - 4) * 0.25F);
        else z = IntegerRGB.HALLOW_C.mixture(IntegerRGB.HALLOW_A, (k - 8) * 0.25F);

        return x.mixture(z, 0.5F).get();
    };

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(HALLOW_LEAVES_COLOR, ModBlocks.PEARL_LOG_BLOCKS.LEAVES.get());
    }

    public static final ItemColor SIMPLE = (pStack, pTintIndex) -> ColoredItem.getColor(pStack);

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(SIMPLE, Materials.GEL.get());
    }

    @SubscribeEvent
    public static void registerTextureAtlasSpriteLoaders(RegisterTextureAtlasSpriteLoadersEvent event) {
        event.register("still_fluid", new ITextureAtlasSpriteLoader() {
            @Override
            public SpriteContents loadContents(ResourceLocation name, Resource resource, FrameSize frameSize, NativeImage image, AnimationMetadataSection animationMeta, ForgeTextureMetadata forgeMeta) {
                return new SpriteContents(name, frameSize, image, animationMeta, forgeMeta);
            }

            @Override
            public @NotNull TextureAtlasSprite makeSprite(ResourceLocation atlasName, SpriteContents contents, int atlasWidth, int atlasHeight, int spriteX, int spriteY, int mipmapLevel) {
                return new TextureAtlasSprite(atlasName, contents, 16, 512, 0, 0) {};
            }
        });
    }
}
