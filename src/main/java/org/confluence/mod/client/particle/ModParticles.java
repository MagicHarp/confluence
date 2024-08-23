package org.confluence.mod.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.options.BloodParticleOptions;
import org.confluence.mod.client.particle.options.CurrentDustOptions;
import org.confluence.mod.client.particle.options.LightsBaneParticleOptions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Confluence.MODID);

    public static final RegistryObject<SimpleParticleType> ITEM_GEL = PARTICLES.register("item_gel", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ITEM_PINK_GEL = PARTICLES.register("item_pink_gel", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RUBY_BULLET = PARTICLES.register("ruby_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AMBER_BULLET = PARTICLES.register("amber_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TOPAZ_BULLET = PARTICLES.register("topaz_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> EMERALD_BULLET = PARTICLES.register("emerald_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SAPPHIRE_BULLET = PARTICLES.register("sapphire_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DIAMOND_BULLET = PARTICLES.register("diamond_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AMETHYST_BULLET = PARTICLES.register("amethyst_bullet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FLAMEFLOWER_BLOOM = PARTICLES.register("flameflower_bloom", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<CurrentDustOptions>> CURRENT_DUST = register("current_dust", false, CurrentDustOptions.DESERIALIZER, (p_123819_) -> CurrentDustOptions.CODEC);
    public static final RegistryObject<ParticleType<BloodParticleOptions>> BLOOD = register("blood", false, BloodParticleOptions.DESERIALIZER, type-> BloodParticleOptions.CODEC);
    public static final RegistryObject<ParticleType<LightsBaneParticleOptions>> LIGHTS_BANE = register("lights_bane", true, LightsBaneParticleOptions.DESERIALIZER, type-> LightsBaneParticleOptions.CODEC);
    public static final RegistryObject<SimpleParticleType> LIGHTS_BANE_DUST = PARTICLES.register("lights_bane_dust", () -> new SimpleParticleType(true));


    @SuppressWarnings("all")
    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String pKey, boolean pOverrideLimiter, ParticleOptions.Deserializer<T> pDeserializer, final Function<ParticleType<T>, Codec<T>> pCodecFactory) {
        return PARTICLES.register(pKey, () -> new ParticleType<T>(pOverrideLimiter, pDeserializer) {
            public @NotNull Codec<T> codec() {
                return pCodecFactory.apply(this);
            }
        });
    }
}
