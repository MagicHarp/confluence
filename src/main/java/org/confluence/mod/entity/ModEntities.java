package org.confluence.mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.boss.KingSlime;
import org.confluence.mod.entity.chair.ChairEntity;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.fishing.BaseFishingHook;
import org.confluence.mod.entity.fishing.BloodyFishingHook;
import org.confluence.mod.entity.fishing.CurioFishingHook;
import org.confluence.mod.entity.fishing.HotlineFishingHook;
import org.confluence.mod.entity.hook.*;
import org.confluence.mod.entity.projectile.*;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.confluence.mod.entity.projectile.bombs.BouncyBombEntity;
import org.confluence.mod.entity.projectile.bombs.ScarabBombEntity;
import org.confluence.mod.entity.projectile.bombs.StickyBombEntity;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.entity.slime.BlackSlime;
import org.confluence.mod.entity.slime.HoneySlime;
import org.confluence.mod.entity.worm.TestWormEntity;
import org.confluence.mod.entity.worm.TestWormPart;

@SuppressWarnings("unused")
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<EntityType<BaseSlime>> BLUE_SLIME = registerSlime("blue", 0x73bcf4, 2);
    public static final RegistryObject<EntityType<BaseSlime>> GREEN_SLIME = registerSlime("green", 0x48E920, 2);
    public static final RegistryObject<EntityType<BaseSlime>> PINK_SLIME = registerSlime("pink", 0xFF87B3, 1);
    public static final RegistryObject<EntityType<BaseSlime>> CORRUPTED_SLIME = registerSlime("corrupted", 0xC91717, 2);
    public static final RegistryObject<EntityType<BaseSlime>> DESERT_SLIME = registerSlime("desert", 0xDCC59a, 2);
    public static final RegistryObject<EntityType<BaseSlime>> JUNGLE_SLIME = registerSlime("jungle", 0x9ae920, 2);
    public static final RegistryObject<EntityType<BaseSlime>> EVIL_SLIME = registerSlime("evil", 0xFF00FF, 2);
    public static final RegistryObject<EntityType<BaseSlime>> ICE_SLIME = registerSlime("ice", 0xB3F0EA, 2);
    public static final RegistryObject<EntityType<BaseSlime>> LAVA_SLIME = ENTITIES.register("lava_slime", () -> EntityType.Builder.<BaseSlime>of((entityType, level) -> new BaseSlime(entityType, level, 0xFFB150, 2), MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).fireImmune().build("confluence:lava_slime"));
    public static final RegistryObject<EntityType<BaseSlime>> LUMINOUS_SLIME = registerSlime("luminous", 0xFFFFFF, 2);
    public static final RegistryObject<EntityType<BaseSlime>> CRIMSON_SLIME = registerSlime("crimson", 0x8B4949, 2);
    public static final RegistryObject<EntityType<BaseSlime>> PURPLE_SLIME = registerSlime("purple", 0xf334f8, 2);
    public static final RegistryObject<EntityType<BaseSlime>> RED_SLIME = registerSlime("red", 0xf83434, 2);
    public static final RegistryObject<EntityType<BaseSlime>> TROPIC_SLIME = registerSlime("tropic", 0x73bcf4, 2);
    public static final RegistryObject<EntityType<BaseSlime>> YELLOW_SLIME = registerSlime("yellow", 0xf8e234, 2);
    public static final RegistryObject<EntityType<HoneySlime>> HONEY_SLIME = ENTITIES.register("honey_slime", () -> EntityType.Builder.<HoneySlime>of((entityType, level) -> new HoneySlime(entityType, level, 0xf8e234), MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build("confluence:honey_slime"));
    /* todo honey slime  */
    public static final RegistryObject<EntityType<BlackSlime>> BLACK_SLIME = ENTITIES.register("black_slime", () -> EntityType.Builder.of(BlackSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build("confluence:black_slime"));
    public static final RegistryObject<EntityType<DemonEye>> DEMON_EYE = ENTITIES.register("demon_eye", () -> EntityType.Builder.of(DemonEye::new, MobCategory.MONSTER).sized(0.6F, 0.6F).clientTrackingRange(10).build("confluence:demon_eye"));

    public static final RegistryObject<EntityType<TestWormEntity>> TEST_WORM = ENTITIES.register("test_worm", () -> EntityType.Builder.of(TestWormEntity::new, MobCategory.MONSTER).sized(0.1F, 0.1F).clientTrackingRange(10).build("confluence:test_worm"));
    public static final RegistryObject<EntityType<TestWormPart>> TEST_WORM_PART = ENTITIES.register("test_worm_segment", () -> EntityType.Builder.<TestWormPart>of(TestWormPart::new, MobCategory.MONSTER).sized(0.6F, 0.6F).clientTrackingRange(10).build("confluence:test_worm_segment"));

    public static final RegistryObject<EntityType<BaseBulletEntity>> BASE_BULLET = ENTITIES.register("base_bullet", () -> EntityType.Builder.<BaseBulletEntity>of(BaseBulletEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(10).build("confluence:base_bullet"));

    public static final RegistryObject<EntityType<BaseHookEntity>> BASE_HOOK = registerHook("base_hook", BaseHookEntity::new);
    public static final RegistryObject<EntityType<WebSlingerEntity>> WEB_SLINGER = registerHook("web_slinger", WebSlingerEntity::new);
    public static final RegistryObject<EntityType<SkeletronHandEntity>> SKELETRON_HAND = registerHook("skeletron_hand", SkeletronHandEntity::new);
    public static final RegistryObject<EntityType<SlimeHookEntity>> SLIME_HOOK = registerHook("slime_hook", SlimeHookEntity::new);
    public static final RegistryObject<EntityType<FishHookEntity>> FISH_HOOK = registerHook("fish_hook", FishHookEntity::new);
    public static final RegistryObject<EntityType<IvyWhipEntity>> IVY_WHIP = registerHook("ivy_whip", IvyWhipEntity::new);
    public static final RegistryObject<EntityType<BatHookEntity>> BAT_HOOK = registerHook("bat_hook", BatHookEntity::new);
    public static final RegistryObject<EntityType<CandyCaneHookEntity>> CANDY_CANE_HOOK = registerHook("candy_cane_hook", CandyCaneHookEntity::new);
    public static final RegistryObject<EntityType<DualHookEntity>> DUAL_HOOK = registerHook("dual_hook", DualHookEntity::new);
    public static final RegistryObject<EntityType<HookOfDissonanceEntity>> HOOK_OF_DISSONANCE = registerHook("hook_of_dissonance", HookOfDissonanceEntity::new);
    public static final RegistryObject<EntityType<ThornHookEntity>> THORN_HOOK = registerHook("thorn_hook", ThornHookEntity::new);
    public static final RegistryObject<EntityType<MimicHookEntity>> MIMIC_HOOK = registerHook("mimic_hook", MimicHookEntity::new);
    public static final RegistryObject<EntityType<AntiGravityHookEntity>> ANTI_GRAVITY_HOOK = registerHook("anti_gravity_hook", AntiGravityHookEntity::new);
    public static final RegistryObject<EntityType<SpookyHookEntity>> SPOOKY_HOOK = registerHook("spooky_hook", SpookyHookEntity::new);
    public static final RegistryObject<EntityType<ChristmasHookEntity>> CHRISTMAS_HOOK = registerHook("christmas_hook", ChristmasHookEntity::new);
    public static final RegistryObject<EntityType<LunarHookEntity>> LUNAR_HOOK = registerHook("lunar_hook", LunarHookEntity::new);
    /* todo 静止钩 */

    public static final RegistryObject<EntityType<FallingStarItemEntity>> FALLING_STAR_ITEM_ENTITY = ENTITIES.register("falling_star", () -> EntityType.Builder.<FallingStarItemEntity>of(FallingStarItemEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(16).updateInterval(20).build("confluence:falling_star"));
    public static final RegistryObject<EntityType<BeeProjectile>> BEE_PROJECTILE = ENTITIES.register("bee_projectile", () -> EntityType.Builder.<BeeProjectile>of(BeeProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(6).build("confluence:bee_projectile"));
    public static final RegistryObject<EntityType<BaseFishingHook>> BASE_FISHING_HOOK = ENTITIES.register("base_fishing_hook", () -> EntityType.Builder.<BaseFishingHook>of(BaseFishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("confluence:base_fishing_hook"));
    public static final RegistryObject<EntityType<HotlineFishingHook>> HOTLINE_FISHING_HOOK = ENTITIES.register("hotline_fishing_hook", () -> EntityType.Builder.<HotlineFishingHook>of(HotlineFishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("confluence:hotline_fishing_hook"));
    public static final RegistryObject<EntityType<CurioFishingHook>> CURIO_FISHING_HOOK = ENTITIES.register("curio_fishing_hook", () -> EntityType.Builder.<CurioFishingHook>of(CurioFishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("confluence:curio_fishing_hook"));
    public static final RegistryObject<EntityType<BloodyFishingHook>> BLOODY_FISHING_HOOK = ENTITIES.register("bloody_fishing_hook", () -> EntityType.Builder.<BloodyFishingHook>of(BloodyFishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("confluence:bloody_fishing_hook"));
    public static final RegistryObject<EntityType<EffectThrownPotion>> EFFECT_THROWN_POTION = ENTITIES.register("effect_thrown_potion", () -> EntityType.Builder.<EffectThrownPotion>of(EffectThrownPotion::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("confluence:effect_thrown_potion"));
    public static final RegistryObject<EntityType<BaseAmmoEntity>> BASE_AMMO = ENTITIES.register("base_ammo", () -> EntityType.Builder.<BaseAmmoEntity>of(BaseAmmoEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(6).build("confluence:base_ammo"));
    public static final RegistryObject<EntityType<StarCloakEntity>> STAR_CLOAK = ENTITIES.register("star_cloak", () -> EntityType.Builder.<StarCloakEntity>of(StarCloakEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(16).updateInterval(20).build("confluence:star_cloak"));
    public static final RegistryObject<EntityType<BoulderEntity>> BOULDER = ENTITIES.register("boulder", () -> EntityType.Builder.<BoulderEntity>of(BoulderEntity::new, MobCategory.MISC).sized(BoulderEntity.DIAMETER, BoulderEntity.DIAMETER).clientTrackingRange(6).build("confluence:boulder"));
    public static final RegistryObject<EntityType<MoneyHoleEntity>> MONEY_HOLE = ENTITIES.register("money_hole", () -> EntityType.Builder.<MoneyHoleEntity>of(MoneyHoleEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(6).build("confluence:money_hole"));
    public static final RegistryObject<EntityType<FlailEntity>> FLAIL = ENTITIES.register("flail", () -> EntityType.Builder.<FlailEntity>of((pEntityType, pLevel) -> new FlailEntity(pLevel, null), MobCategory.MISC).sized(0.5F, 2F)/*.clientTrackingRange(6)*/.noSave().build("confluence:flail"));
    public static final RegistryObject<EntityType<ChairEntity>> CHAIR = ENTITIES.register("chair", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.05F, 0.02F).build("confluence:chair"));
    public static final RegistryObject<EntityType<EnchantedSwordProjectile>> ENCHANTED_SWORD_PROJECTILE = ENTITIES.register("enchanted_sword_projectile", () -> EntityType.Builder.<EnchantedSwordProjectile>of(EnchantedSwordProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).build("confluence:enchanted_sword_projectile"));
    public static final RegistryObject<EntityType<IceBladeSwordProjectile>> ICE_BLADE_SWORD_PROJECTILE = ENTITIES.register("ice_blade_sword_projectile", () -> EntityType.Builder.<IceBladeSwordProjectile>of(IceBladeSwordProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).build("confluence:ice_blade_sword_projectile"));
    public static final RegistryObject<EntityType<ThrowingKnivesProjectile>> THROW_KNIVES_PROJECTILE = ENTITIES.register("throw_knives_projectile", () -> EntityType.Builder.<ThrowingKnivesProjectile>of(ThrowingKnivesProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).build("confluence:throw_knives_projectile"));
    public static final RegistryObject<EntityType<ShurikenProjectile>> SHURIKEN_PROJECTILE = ENTITIES.register("shuriken_projectile", () -> EntityType.Builder.<ShurikenProjectile>of(ShurikenProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).build("confluence:shuriken_projectile"));

    public static final RegistryObject<EntityType<BaseBombEntity>> BOMB_ENTITY = ENTITIES.register("bomb_entity", () -> EntityType.Builder.<BaseBombEntity>of(BaseBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:bomb_entity"));
    public static final RegistryObject<EntityType<BouncyBombEntity>> BOUNCY_BOMB_ENTITY = ENTITIES.register("bouncy_bomb_entity", () -> EntityType.Builder.<BouncyBombEntity>of(BouncyBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:bouncy_bomb_entity"));
    public static final RegistryObject<EntityType<ScarabBombEntity>> SCARAB_BOMB_ENTITY = ENTITIES.register("scarab_bomb_entity", () -> EntityType.Builder.<ScarabBombEntity>of(ScarabBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:scarab_bomb_entity"));
    public static final RegistryObject<EntityType<StickyBombEntity>> STICKY_BOMB_ENTITY = ENTITIES.register("sticky_bomb_entity", () -> EntityType.Builder.<StickyBombEntity>of(StickyBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:sticky_bomb_entity"));
    public static final RegistryObject<EntityType<StepStoolEntity>> STEP_STOOL = ENTITIES.register("step_stool", () -> EntityType.Builder.<StepStoolEntity>of(StepStoolEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).build("confluence:step_stool"));

    public static final RegistryObject<EntityType<KingSlime>> KING_SLIME = ENTITIES.register("king_slime", () -> EntityType.Builder.<KingSlime>of(KingSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build("confluence:king_slime"));

    private static RegistryObject<EntityType<BaseSlime>> registerSlime(String prefix, int color, int size) {
        return ENTITIES.register(prefix + "_slime", () -> EntityType.Builder.<BaseSlime>of((entityType, level) -> new BaseSlime(entityType, level, color, size), MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build("confluence:" + prefix + "_slime"));
    }

    private static <E extends AbstractHookEntity> RegistryObject<EntityType<E>> registerHook(String id, EntityType.EntityFactory<E> supplier) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(supplier, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("confluence:" + id));
    }
}
