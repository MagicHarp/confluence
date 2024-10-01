package org.confluence.mod.client;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.color.IntegerRGB;
import org.confluence.mod.client.connected.ModConnectives;
import org.confluence.mod.client.model.entity.*;
import org.confluence.mod.client.model.entity.bomb.BaseBombEntityModel;
import org.confluence.mod.client.model.entity.bomb.BouncyBombEntityModel;
import org.confluence.mod.client.model.entity.bomb.ScarabBombEntityModel;
import org.confluence.mod.client.model.entity.bomb.StickyBombEntityModel;
import org.confluence.mod.client.model.entity.fishing.BaseFishingHookModel;
import org.confluence.mod.client.model.entity.fishing.BloodyFishingHookModel;
import org.confluence.mod.client.model.entity.fishing.GlowingFishingHookModel;
import org.confluence.mod.client.model.entity.fishing.HotlineFishingHookModel;
import org.confluence.mod.client.model.entity.hook.BaseHookModel;
import org.confluence.mod.client.model.entity.hook.SkeletronHandModel;
import org.confluence.mod.client.model.entity.hook.WebSlingerModel;
import org.confluence.mod.client.particle.*;
import org.confluence.mod.client.renderer.AchievementToast;
import org.confluence.mod.client.renderer.block.*;
import org.confluence.mod.client.renderer.entity.*;
import org.confluence.mod.client.renderer.entity.bomb.BaseBombEntityRenderer;
import org.confluence.mod.client.renderer.entity.bomb.BouncyBombEntityRenderer;
import org.confluence.mod.client.renderer.entity.bomb.ScarabBombEntityRenderer;
import org.confluence.mod.client.renderer.entity.bomb.StickyBombEntityRenderer;
import org.confluence.mod.client.renderer.entity.fishing.BaseFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.fishing.BloodyFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.fishing.GlowingFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.fishing.HotlineFishingHookRenderer;
import org.confluence.mod.client.renderer.entity.hook.*;
import org.confluence.mod.client.renderer.gui.*;
import org.confluence.mod.entity.boss.geoRenderer.KingSkullHandRenderer;
import org.confluence.mod.entity.boss.geoRenderer.KingSkullRenderer;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.bow.Bows;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.potion.VanillaPotionItem;
import org.confluence.mod.menu.ModMenus;
import org.confluence.mod.misc.ModArmPoses;

import static org.confluence.mod.entity.ModEntities.*;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {

    public static final BlockColor HALLOW_LEAVES_COLOR = (blockState, getter, pos, tint) -> {
        if (pos == null) return -1;

        IntegerRGB x = hallowMixture(Math.abs(pos.getX()) % 12);
        IntegerRGB y = hallowMixture(Math.abs(pos.getY()) % 12);
        IntegerRGB z = hallowMixture(Math.abs(pos.getZ()) % 12);

        return x.mixture(y, 0.5F).mixture(z, 0.5F).get();
    };
    public static final ItemColor SIMPLE = (pStack, pTintIndex) -> ColoredItem.getColor(pStack);
    public static final ItemColor POTION = (pStack, pTintIndex) -> VanillaPotionItem.getColor(pStack);

    private static IntegerRGB hallowMixture(int m) {
        if (m <= 4) return IntegerRGB.HALLOW_A.mixture(IntegerRGB.HALLOW_B, m * 0.25F);
        if (m <= 8) return IntegerRGB.HALLOW_B.mixture(IntegerRGB.HALLOW_C, (m - 4) * 0.25F);
        return IntegerRGB.HALLOW_C.mixture(IntegerRGB.HALLOW_A, (m - 8) * 0.25F);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientConfigs.onClientLoad();
            ModArmPoses.initialize();
            CuriosClient.registerRenderers();

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_ICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURPLE_ICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_ICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.THIN_ICE_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_LOG_BLOCKS.DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_LOG_BLOCKS.TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PALM_LOG_BLOCKS.DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SHIMMER.fluid().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SHIMMER.flowingFluid().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HONEY.fluid().get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.HONEY.flowingFluid().get(), RenderType.translucent());

            ItemProperties.register(CurioItems.SPECTRE_GOGGLES.get(), Confluence.asResource("enable"), (itemStack, level, living, speed) ->
                    itemStack.getTag() != null && itemStack.getTag().getBoolean("enable") ? 1.0F : 0.0F);
            ItemProperties.register(CurioItems.MECHANICAL_LENS.get(), Confluence.asResource("enable"), (itemStack, level, living, speed) ->
                    itemStack.getTag() != null && itemStack.getTag().getBoolean("enable") ? 1.0F : 0.0F);
            ItemProperties.register(ModBlocks.BASE_CHEST_BLOCK.get().asItem(), Confluence.asResource("variant"), (itemStack, level, living, speed) ->
                    itemStack.getTag() == null ? 0 : itemStack.getTag().getInt("VariantId"));
            ItemProperties.register(ModBlocks.DEATH_CHEST_BLOCK.get().asItem(), Confluence.asResource("variant"), (itemStack, level, living, speed) ->
                    itemStack.getTag() == null ? 0 : itemStack.getTag().getInt("VariantId"));
            FishingPoles.registerCast();
            Bows.registerProperties();

            // 开局


            // Collector
            AchievementToast.registerToast("timber");
            AchievementToast.registerToast("benched");
            AchievementToast.registerToast("hammer_time");
            AchievementToast.registerToast("heavy_metal");
            AchievementToast.registerToast("matching_attire");
            AchievementToast.registerToast("star_power");
            AchievementToast.registerToast("hold_on_tight");
            AchievementToast.registerToast("miner_for_fire");
            AchievementToast.registerToast("head_in_the_clouds");
            AchievementToast.registerToast("like_a_boss");
            AchievementToast.registerToast("feast_of_midas");
            AchievementToast.registerToast("drax_attax");
            AchievementToast.registerToast("fashion_statement");
            AchievementToast.registerToast("sword_of_the_hero");
            AchievementToast.registerToast("dye_hard");
            AchievementToast.registerToast("sick_throw");
            AchievementToast.registerToast("the_cavalry");
            AchievementToast.registerToast("completely_awesome");
            AchievementToast.registerToast("prismancer");
            AchievementToast.registerToast("glorious_golden_pole");
            AchievementToast.registerToast("boots_of_the_hero");
            AchievementToast.registerToast("infinity_1_sword");
            AchievementToast.registerToast("black_mirror");
            AchievementToast.registerToast("ankhumulation_complete");

            // Explorer
            AchievementToast.registerToast("new_world");
            AchievementToast.registerToast("you_can_do_it");
            AchievementToast.registerToast("no_hobo");
            AchievementToast.registerToast("ooo_shinny");
            AchievementToast.registerToast("heart_breaker");
            AchievementToast.registerToast("i_am_loot");
            AchievementToast.registerToast("quiet_neighborhood");
            AchievementToast.registerToast("hey_listen");
            AchievementToast.registerToast("smashing_poppet");
            AchievementToast.registerToast("wheres_my_honey");
            AchievementToast.registerToast("dungeon_heist");
            AchievementToast.registerToast("its_getting_hot_in_here");
            AchievementToast.registerToast("its_hard");
            AchievementToast.registerToast("begone_evil");
            AchievementToast.registerToast("extra_shiny");
            AchievementToast.registerToast("photosynthesis");
            AchievementToast.registerToast("get_a_life");
            AchievementToast.registerToast("temple_raider");
            AchievementToast.registerToast("robbing_the_grave");
            AchievementToast.registerToast("big_booty");
            AchievementToast.registerToast("jeepers_creepers");
            AchievementToast.registerToast("funkytown");
            AchievementToast.registerToast("into_orbit");
            AchievementToast.registerToast("rock_bottom");
            AchievementToast.registerToast("it_can_talk");
            AchievementToast.registerToast("watch_your_step");
            AchievementToast.registerToast("rock_bottom");
            AchievementToast.registerToast("a_rare_realm");
            AchievementToast.registerToast("a_shimmer_in_the_dark");

            // Slayer boss
            AchievementToast.registerToast("slippery_shinobi");
            AchievementToast.registerToast("eye_on_you");
            AchievementToast.registerToast("worm_fodder");
            AchievementToast.registerToast("mastermind");
            AchievementToast.registerToast("sting_operation");
            AchievementToast.registerToast("boned");
            AchievementToast.registerToast("an_eye_for_an_eye");
            AchievementToast.registerToast("still_hungry");
            AchievementToast.registerToast("just_desserts");
            AchievementToast.registerToast("buckets_of_bolts");
            AchievementToast.registerToast("the_great_southern_plantkill");
            AchievementToast.registerToast("lihzahrdian_idol");
            AchievementToast.registerToast("fish_out_of_water");
            AchievementToast.registerToast("fae_flayer");
            AchievementToast.registerToast("obsessive_devotion");
            AchievementToast.registerToast("star_destroyer");
            AchievementToast.registerToast("champion_of_terraria");
            // Slayer event
            AchievementToast.registerToast("bloodbath");
            AchievementToast.registerToast("sticky_situation");
            AchievementToast.registerToast("goblin_punter");
            AchievementToast.registerToast("hero_of_etheria");
            AchievementToast.registerToast("walk_the_plank");
            AchievementToast.registerToast("dont_dread_on_me");
            AchievementToast.registerToast("kill_the_sun");
            AchievementToast.registerToast("do_you_want_to_slay_a_snowman");
            AchievementToast.registerToast("tin_foil_hatter");
            AchievementToast.registerToast("baleful_harvest");
            AchievementToast.registerToast("ice_scream");

            AchievementToast.registerToast("vehicular_manslaughter");
            AchievementToast.registerToast("there_are_some_who_call_him");
            AchievementToast.registerToast("deceiver_of_fools");
            AchievementToast.registerToast("til_death");
            AchievementToast.registerToast("archaeologist");
            AchievementToast.registerToast("pretty_in_pink");
            AchievementToast.registerToast("archaeologist");
            AchievementToast.registerToast("torch_god");

            // Challenger
            AchievementToast.registerToast("real_estate_agent");
            AchievementToast.registerToast("not_the_bees");
            AchievementToast.registerToast("mecha_mayhem");
            AchievementToast.registerToast("gelatin_world_tour");
            AchievementToast.registerToast("bulldozer");
            AchievementToast.registerToast("lucky_break");
            AchievementToast.registerToast("throwing_lines");
            AchievementToast.registerToast("the_frequent_flyer");
            AchievementToast.registerToast("rainbows_and_unicorns");
            AchievementToast.registerToast("you_and_what_army");
            AchievementToast.registerToast("marathon_medalist");
            AchievementToast.registerToast("servant_in_training");
            AchievementToast.registerToast("good_little_slave");
            AchievementToast.registerToast("trout_monkey");
            AchievementToast.registerToast("fast_and_fishious");
            AchievementToast.registerToast("supreme_helper_minion");
            AchievementToast.registerToast("topped_off");
            AchievementToast.registerToast("slayer_of_worlds");
            AchievementToast.registerToast("marathon_medalist");
            AchievementToast.registerToast("a_rather_blustery_day");
            AchievementToast.registerToast("hot_reels");
            AchievementToast.registerToast("heliophobia");
            AchievementToast.registerToast("leading_landlord");
            AchievementToast.registerToast("feeling_petty");
            AchievementToast.registerToast("jolly_jamboree");
            AchievementToast.registerToast("dead_men_tell_no_tales");
            AchievementToast.registerToast("unusual_survival_strategies");
            AchievementToast.registerToast("the_great_slime_mitosis");
            AchievementToast.registerToast("and_good_riddance");
            AchievementToast.registerToast("to_infinity_and_beyond");
            MenuScreens.register(ModMenus.SKY_MILL.get(), SkyMillScreen::new);
            MenuScreens.register(ModMenus.WORKSHOP.get(), WorkshopScreen::new);

            ModConnectives.register(FMLJavaModLoadingContext.get().getModEventBus());
        });
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelow(VanillaGuiOverlay.ITEM_NAME.id(), "mana_hud", new ManaHudOverlay());
        event.registerAboveAll("info_hud", new InfoHudOverlay());
        event.registerAboveAll("health_hud", new HealthHudOverlay());
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CuriosClient.registerLayers(event::registerLayerDefinition);

        event.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        event.registerLayerDefinition(BeeProjectileModel.LAYER_LOCATION, BeeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(BoulderModel.LAYER_LOCATION, BoulderModel::createBodyLayer);
        event.registerLayerDefinition(MoneyHoleModel.LAYER_LOCATION, MoneyHoleModel::createBodyLayer);
        event.registerLayerDefinition(AmmoModel.LAYER_LOCATION, AmmoModel::createBodyLayer);
        event.registerLayerDefinition(EnchantedSwordProjectileModel.LAYER_LOCATION, EnchantedSwordProjectileModel::createBodyLayer);
        event.registerLayerDefinition(IceBladeSwordProjectileModel.LAYER_LOCATION, IceBladeSwordProjectileModel::createBodyLayer);
        event.registerLayerDefinition(ThrowingKnivesProjectileModel.LAYER_LOCATION, ThrowingKnivesProjectileModel::createBodyLayer);
        event.registerLayerDefinition(BaseBombEntityModel.LAYER_LOCATION, BaseBombEntityModel::createBodyLayer);
        event.registerLayerDefinition(BouncyBombEntityModel.LAYER_LOCATION, BouncyBombEntityModel::createBodyLayer);
        event.registerLayerDefinition(ScarabBombEntityModel.LAYER_LOCATION, ScarabBombEntityModel::createBodyLayer);
        event.registerLayerDefinition(StickyBombEntityModel.LAYER_LOCATION, StickyBombEntityModel::createBodyLayer);
        event.registerLayerDefinition(ShurikenProjectileModel.LAYER_LOCATION, ShurikenProjectileModel::createBodyLayer);
        event.registerLayerDefinition(StepStoolModel.LAYER_LOCATION, StepStoolModel::createBodyLayer);

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
        event.registerLayerDefinition(FlailModel.LAYER_LOCATION, FlailModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BLUE_SLIME.get(), c -> new CustomSlimeRenderer(c, "blue"));
        event.registerEntityRenderer(GREEN_SLIME.get(), c -> new CustomSlimeRenderer(c, "green"));
        event.registerEntityRenderer(PINK_SLIME.get(), c -> new CustomSlimeRenderer(c, "pink"));
        event.registerEntityRenderer(CORRUPTED_SLIME.get(), c -> new CustomSlimeRenderer(c, "corrupted"));
        event.registerEntityRenderer(DESERT_SLIME.get(), c -> new CustomSlimeRenderer(c, "desert"));
        event.registerEntityRenderer(JUNGLE_SLIME.get(), c -> new CustomSlimeRenderer(c, "jungle"));
        event.registerEntityRenderer(EVIL_SLIME.get(), c -> new CustomSlimeRenderer(c, "evil"));
        event.registerEntityRenderer(ICE_SLIME.get(), c -> new CustomSlimeRenderer(c, "ice"));
        event.registerEntityRenderer(LAVA_SLIME.get(), c -> new CustomSlimeRenderer(c, "lava"));
        event.registerEntityRenderer(LUMINOUS_SLIME.get(), c -> new CustomSlimeRenderer(c, "luminous"));
        event.registerEntityRenderer(CRIMSON_SLIME.get(), c -> new CustomSlimeRenderer(c, "crimson"));
        event.registerEntityRenderer(PURPLE_SLIME.get(), c -> new CustomSlimeRenderer(c, "purple"));
        event.registerEntityRenderer(RED_SLIME.get(), c -> new CustomSlimeRenderer(c, "red"));
        event.registerEntityRenderer(TROPIC_SLIME.get(), c -> new CustomSlimeRenderer(c, "tropic"));
        event.registerEntityRenderer(YELLOW_SLIME.get(), c -> new CustomSlimeRenderer(c, "yellow"));
        event.registerEntityRenderer(HONEY_SLIME.get(), c -> new CustomSlimeRenderer(c, "honey"));
        event.registerEntityRenderer(BLACK_SLIME.get(), c -> new CustomSlimeRenderer(c, "black"));

        event.registerEntityRenderer(KING_SLIME.get(), c -> new CustomSlimeRenderer(c, "king"));
        event.registerEntityRenderer(KING_SKULL.get(), KingSkullRenderer::new);
        event.registerEntityRenderer(KING_SKULL_HAND.get(), KingSkullHandRenderer::new);


        event.registerEntityRenderer(DEMON_EYE.get(), DemonEyeRenderer::new);
        event.registerEntityRenderer(CTHULHU_EYE.get(), CthulhuEyeRenderer::new);
        event.registerEntityRenderer(TEST_WORM.get(), TestWormRenderer::new);
        event.registerEntityRenderer(TEST_WORM_PART.get(), TestWormSegmentRenderer::new);

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
        event.registerEntityRenderer(ENCHANTED_SWORD_PROJECTILE.get(), EnchantedSwordProjectileRenderer::new);
        event.registerEntityRenderer(ICE_BLADE_SWORD_PROJECTILE.get(), IceBladeSwordProjectileRenderer::new);
        event.registerEntityRenderer(THROW_KNIVES_PROJECTILE.get(), ThrowingKnivesProjectileRenderer::new);
        event.registerEntityRenderer(SHURIKEN_PROJECTILE.get(), ShurikenProjectileRenderer::new);
        event.registerEntityRenderer(STEP_STOOL.get(), StepStoolRenderer::new);
        event.registerEntityRenderer(BOMB_ENTITY.get(), BaseBombEntityRenderer::new);
        event.registerEntityRenderer(BOUNCY_BOMB_ENTITY.get(), BouncyBombEntityRenderer::new);
        event.registerEntityRenderer(SCARAB_BOMB_ENTITY.get(), ScarabBombEntityRenderer::new);
        event.registerEntityRenderer(STICKY_BOMB_ENTITY.get(), StickyBombEntityRenderer::new);

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

        event.registerBlockEntityRenderer(ModBlocks.ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.LIFE_CRYSTAL_BLOCK_ENTITY.get(), LifeCrystalBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.EXTRACTINATOR_ENTITY.get(), ExtractinatorBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.MECHANICAL_BLOCK_ENTITY.get(), MechanicalBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.BASE_CHEST_BLOCK_ENTITY.get(), BaseChestBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.DEATH_CHEST_BLOCK_ENTITY.get(), DeathChestBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.WHITE_PLASTIC_CHAIR_ENTITY.get(), WhitePlasticChairRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.ALCHEMY_TABLE_BLOCK_ENTITY.get(), AlchemyTableBlockRenderer::new);

        event.registerEntityRenderer(FLAIL.get(), FlailRenderer::new);
        event.registerEntityRenderer(CHAIR.get(), NoopRenderer::new);
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
        event.registerSpriteSet(ModParticles.CURRENT_DUST.get(), CurrentColorDustParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLOOD.get(), BloodParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTS_BANE.get(), LightsBaneParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTS_BANE_DUST.get(), LightsBaneDustParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTS_BANE_FADE.get(), LightsBaneFadeParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(HALLOW_LEAVES_COLOR, ModBlocks.PEARL_LOG_BLOCKS.LEAVES.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(SIMPLE, Materials.GEL.get());
        event.register(POTION, TerraPotions.VANILLA_POTION.get());
    }

//    @SubscribeEvent
//    public static void registerTextureAtlasSpriteLoaders(RegisterTextureAtlasSpriteLoadersEvent event) {
//        event.register("still_fluid", new ITextureAtlasSpriteLoader() {
//            @Override
//            public SpriteContents loadContents(ResourceLocation name, Resource resource, FrameSize frameSize, NativeImage image, AnimationMetadataSection animationMeta, ForgeTextureMetadata forgeMeta) {
//                return new SpriteContents(name, frameSize, image, animationMeta, forgeMeta);
//            }
//
//            @Override
//            public @NotNull TextureAtlasSprite makeSprite(ResourceLocation atlasName, SpriteContents contents, int atlasWidth, int atlasHeight, int spriteX, int spriteY, int mipmapLevel) {
//                return new TextureAtlasSprite(atlasName, contents, 16, 512, 0, 0) {
//                };
//            }
//        });
//    }

    @SubscribeEvent
    public static void registerEntitySpectatorShaders(RegisterEntitySpectatorShadersEvent event) {
        event.register(DEMON_EYE.get(), Confluence.asResource("shaders/post/red.json"));
    }
}
