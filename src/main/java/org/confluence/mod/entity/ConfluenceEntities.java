package org.confluence.mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.slime.BlueSlime;

@SuppressWarnings("unused")
public class ConfluenceEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<EntityType<BlueSlime>> BLUE_SLIME = ENTITIES.register("blue_slime", () ->
        EntityType.Builder.of(BlueSlime::new, MobCategory.MONSTER)
            .sized(2.04F, 2.04F)
            .clientTrackingRange(10)
            .build("confluence:blue_slime"));
}
