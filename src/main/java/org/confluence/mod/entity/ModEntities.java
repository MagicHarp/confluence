package org.confluence.mod.entity;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.fishing.BaseFishingHook;
import org.confluence.mod.entity.fishing.BloodyFishingHook;
import org.confluence.mod.entity.fishing.HotlineFishingHook;
import org.confluence.mod.entity.hook.*;
import org.confluence.mod.entity.projectile.*;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.entity.slime.BlackSlime;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<EntityType<BaseSlime>> BLUE_SLIME = registerSlime("blue", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> GREEN_SLIME = registerSlime("green", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> PINK_SLIME = registerSlime("pink", ModParticles.ITEM_PINK_GEL, 1);
    public static final RegistryObject<EntityType<BaseSlime>> CORRUPTED_SLIME = registerSlime("corrupted", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> DESERT_SLIME = registerSlime("desert", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> EVIL_SLIME = registerSlime("evil", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> ICE_SLIME = registerSlime("ice", ModParticles.ITEM_FROZEN_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> LAVA_SLIME = registerSlime("lava", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> LUMINOUS_SLIME = registerSlime("luminous", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> CRIMSON_SLIME = registerSlime("crimson", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> PURPLE_SLIME = registerSlime("purple", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> RED_SLIME = registerSlime("red", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> TROPIC_SLIME = registerSlime("tropic", ModParticles.ITEM_BLUE_GEL, 2);
    public static final RegistryObject<EntityType<BaseSlime>> YELLOW_SLIME = registerSlime("yellow", ModParticles.ITEM_BLUE_GEL, 2);
    /* todo honey slime  */
    public static final RegistryObject<EntityType<BlackSlime>> BLACK_SLIME = ENTITIES.register("black_slime", () -> EntityType.Builder.of(BlackSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build("confluence:black_slime"));
    public static final RegistryObject<EntityType<DemonEye>> DEMON_EYE = ENTITIES.register("demon_eye", () -> EntityType.Builder.of(DemonEye::new, MobCategory.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(10).build("confluence:demon_eye"));

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
    public static final RegistryObject<EntityType<BloodyFishingHook>> BLOODY_FISHING_HOOK = ENTITIES.register("bloody_fishing_hook", () -> EntityType.Builder.<BloodyFishingHook>of(BloodyFishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("confluence:bloody_fishing_hook"));
    public static final RegistryObject<EntityType<EffectThrownPotion>> EFFECT_THROWN_POTION = ENTITIES.register("effect_thrown_potion", () -> EntityType.Builder.<EffectThrownPotion>of(EffectThrownPotion::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("confluence:effect_thrown_potion"));
    public static final RegistryObject<EntityType<BaseAmmoEntity>> BASE_AMMO = ENTITIES.register("base_ammo", () -> EntityType.Builder.<BaseAmmoEntity>of(BaseAmmoEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(6).build("confluence:base_ammo"));
    public static final RegistryObject<EntityType<StarCloakEntity>> STAR_CLOAK = ENTITIES.register("star_cloak", () -> EntityType.Builder.<StarCloakEntity>of(StarCloakEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(16).updateInterval(20).build("confluence:star_cloak"));

    private static RegistryObject<EntityType<BaseSlime>> registerSlime(String i, Supplier<SimpleParticleType> p, int s) {
        return ENTITIES.register(i + "_slime", () -> EntityType.Builder.<BaseSlime>of((e, l) -> new BaseSlime(e, l, p, s), MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build("confluence:" + i + "_slime"));
    }

    private static <E extends AbstractHookEntity> RegistryObject<EntityType<E>> registerHook(String id, EntityType.EntityFactory<E> supplier) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(supplier, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("confluence:" + id));
    }
}
