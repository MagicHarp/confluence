package org.confluence.mod.client.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public class ConfluenceParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Confluence.MODID);

    public static final RegistryObject<SimpleParticleType> ITEM_BLUE_SLIME = PARTICLES.register("item_blue_slime", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ITEM_PINK_SLIME = PARTICLES.register("item_pink_slime", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ITEM_HONEY_SLIME = PARTICLES.register("item_honey_slime", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RUBY_BULLET = PARTICLES.register("ruby_bullet", () -> new SimpleParticleType(false));
}
