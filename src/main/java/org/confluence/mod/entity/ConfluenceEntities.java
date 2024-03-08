package org.confluence.mod.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.entity.slime.BlackSlime;

@SuppressWarnings("unused")
public class ConfluenceEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<EntityType<BaseSlime>> BLUE_SLIME = registerSlime("blue", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> GREEN_SLIME = registerSlime("green", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> PINK_SLIME = registerSlime("pink", ConfluenceParticles.ITEM_PINK_SLIME.get(), 1);
    public static final RegistryObject<EntityType<BaseSlime>> CORRUPTED_SLIME = registerSlime("corrupted", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> DESERT_SLIME = registerSlime("desert", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> EVIL_SLIME = registerSlime("evil", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> ICE_SLIME = registerSlime("ice", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> LAVA_SLIME = registerSlime("lava", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> LUMINOUS_SLIME = registerSlime("luminous", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> CRIMSON_SLIME = registerSlime("crimson", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> PURPLE_SLIME = registerSlime("purple", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> RED_SLIME = registerSlime("red", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> TROPIC_SLIME = registerSlime("tropic", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BaseSlime>> YELLOW_SLIME = registerSlime("yellow", ConfluenceParticles.ITEM_BLUE_SLIME.get(), 2);
    public static final RegistryObject<EntityType<BlackSlime>> BLACK_SLIME = ENTITIES.register("black_slime", () ->
        EntityType.Builder.of(BlackSlime::new, MobCategory.MONSTER)
            .sized(2.04F, 2.04F)
            .clientTrackingRange(10)
            .build("confluence:black_slime"));

    private static RegistryObject<EntityType<BaseSlime>> registerSlime(String i, ParticleOptions p, int s) {
        return ENTITIES.register(i + "_slime", () ->
            EntityType.Builder.of((EntityType.EntityFactory<BaseSlime>) (e, l) -> new BaseSlime(e, l, p, s), MobCategory.MONSTER)
                .sized(2.04F, 2.04F)
                .clientTrackingRange(10)
                .build("confluence:" + i + "_slime"));
    }

}
