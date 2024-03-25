package org.confluence.mod.client.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Confluence.MODID);

    public static final RegistryObject<SimpleParticleType> ITEM_BLUE_GEL = PARTICLES.register("item_blue_gel", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ITEM_PINK_GEL = PARTICLES.register("item_pink_gel", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ITEM_HONEY_GEL = PARTICLES.register("item_honey_gel", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ITEM_FROZEN_GEL = PARTICLES.register("item_frozen_gel", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RUBY_BULLET = PARTICLES.register("ruby_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AMBER_BULLET = PARTICLES.register("amber_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TOPAZ_BULLET = PARTICLES.register("topaz_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> EMERALD_BULLET = PARTICLES.register("emerald_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SAPPHIRE_BULLET = PARTICLES.register("sapphire_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DIAMOND_BULLET = PARTICLES.register("diamond_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AMETHYST_BULLET = PARTICLES.register("amethyst_bullet", () -> new SimpleParticleType(false));
}
