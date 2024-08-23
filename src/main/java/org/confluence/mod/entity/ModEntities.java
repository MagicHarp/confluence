package org.confluence.mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.projectile.BeeProjectile;
import org.confluence.mod.entity.projectile.StarCloakEntity;

@SuppressWarnings("unused")
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<EntityType<BeeProjectile>> BEE_PROJECTILE = ENTITIES.register("bee_projectile", () -> EntityType.Builder.<BeeProjectile>of(BeeProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(6).build("confluence:bee_projectile"));
    public static final RegistryObject<EntityType<StarCloakEntity>> STAR_CLOAK = ENTITIES.register("star_cloak", () -> EntityType.Builder.<StarCloakEntity>of(StarCloakEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(16).updateInterval(20).build("confluence:star_cloak"));
    public static final RegistryObject<EntityType<StepStoolEntity>> STEP_STOOL = ENTITIES.register("step_stool", () -> EntityType.Builder.<StepStoolEntity>of(StepStoolEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).build("confluence:step_stool"));
}
