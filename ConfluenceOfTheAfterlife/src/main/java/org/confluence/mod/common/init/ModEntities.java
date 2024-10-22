package org.confluence.mod.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.entity.projectile.bombs.*;
import org.confluence.mod.common.entity.projectile.BaseArrowEntity;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Confluence.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseBombEntity>> BOMB_ENTITY = ENTITIES.register("bomb_entity", () -> EntityType.Builder.<BaseBombEntity>of(BaseBombEntity::new, MobCategory.MISC).sized(BaseBombEntity.DIAMETER, BaseBombEntity.DIAMETER).clientTrackingRange(4).updateInterval(10).build("confluence:bomb_entity"));
    public static final DeferredHolder<EntityType<?>, EntityType<BouncyBombEntity>> BOUNCY_BOMB_ENTITY = ENTITIES.register("bouncy_bomb_entity", () -> EntityType.Builder.<BouncyBombEntity>of(BouncyBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:bouncy_bomb_entity"));
    public static final DeferredHolder<EntityType<?>, EntityType<ScarabBombEntity>> SCARAB_BOMB_ENTITY = ENTITIES.register("scarab_bomb_entity", () -> EntityType.Builder.<ScarabBombEntity>of(ScarabBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:scarab_bomb_entity"));
    public static final DeferredHolder<EntityType<?>, EntityType<StickyBombEntity>> STICKY_BOMB_ENTITY = ENTITIES.register("sticky_bomb_entity", () -> EntityType.Builder.<StickyBombEntity>of(StickyBombEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:sticky_bomb_entity"));
    public static final DeferredHolder<EntityType<?>, EntityType<BombFishEntity>> BOMB_FISH_ENTITY = ENTITIES.register("bomb_fish_entity", () -> EntityType.Builder.<BombFishEntity>of(BombFishEntity::new, MobCategory.MISC).sized(0.375F, 0.375F).clientTrackingRange(4).updateInterval(10).build("confluence:bomb_fish_entity"));
    public static final DeferredHolder<EntityType<?>, EntityType<BaseArrowEntity>> ARROW_PROJECTILE = ENTITIES.register("arrow_projectile", () -> EntityType.Builder.<BaseArrowEntity>of(BaseArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("confluence:arrow_projectile"));


//    @SubscribeEvent
//    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
//
//    }
}
