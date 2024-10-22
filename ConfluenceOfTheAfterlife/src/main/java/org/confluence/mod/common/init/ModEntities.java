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
import org.confluence.mod.common.entity.projectile.BaseArrowEntity;

//@EventBusSubscriber(modid = Confluence.MODID, bus = EventBusSubscriber.Bus.MOD)

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Confluence.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BaseArrowEntity>> ARROW_PROJECTILE = ENTITIES.register("arrow_projectile", () -> EntityType.Builder.<BaseArrowEntity>of(BaseArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("confluence:arrow_projectile"));


//    @SubscribeEvent
//    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
//
//    }
}