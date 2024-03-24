package org.confluence.mod.entity;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.bullet.BaseBulletEntity;
import org.confluence.mod.entity.bullet.RubyBulletEntity;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.entity.slime.BlackSlime;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModEntities {
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

    public static final RegistryObject<EntityType<BaseBulletEntity>> AMBER_BULLET = registerBullet("amber", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> AMETHYST_BULLET = registerBullet("amethyst", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> DIAMOND_BULLET = registerBullet("diamond", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> EMERALD_BULLET = registerBullet("emerald", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> FROST_BULLET = registerBullet("frost", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> RUBY_BULLET = registerBullet("ruby", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> SAPPHIRE_BULLET = registerBullet("sapphire", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> SPARK_BULLET = registerBullet("spark", RubyBulletEntity::new);
    public static final RegistryObject<EntityType<BaseBulletEntity>> TOPAZ_BULLET = registerBullet("topaz", RubyBulletEntity::new);

    private static RegistryObject<EntityType<BaseSlime>> registerSlime(String i, Supplier<SimpleParticleType> p, int s) {
        return ENTITIES.register(i + "_slime", () ->
            EntityType.Builder.of((EntityType.EntityFactory<BaseSlime>) (e, l) -> new BaseSlime(e, l, p, s), MobCategory.MONSTER)
                .sized(2.04F, 2.04F)
                .clientTrackingRange(10)
                .build("confluence:" + i + "_slime"));
    }

    private static RegistryObject<EntityType<BaseBulletEntity>> registerBullet(String i, EntityType.EntityFactory<BaseBulletEntity> factory) {
        return ENTITIES.register(i + "_bullet", () ->
            EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(10)
                .build("confluence:" + i + "_bullet"));
    }
}