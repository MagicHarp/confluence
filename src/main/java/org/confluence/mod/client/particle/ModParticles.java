package org.confluence.mod.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.options.CurrentDustOptions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Confluence.MODID);

    public static final RegistryObject<ParticleType<CurrentDustOptions>> CURRENT_DUST = register("current_dust", false, CurrentDustOptions.DESERIALIZER, (p_123819_) -> CurrentDustOptions.CODEC);

    @SuppressWarnings("all")
    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String pKey, boolean pOverrideLimiter, ParticleOptions.Deserializer<T> pDeserializer, final Function<ParticleType<T>, Codec<T>> pCodecFactory) {
        return PARTICLES.register(pKey, () -> new ParticleType<T>(pOverrideLimiter, pDeserializer) {
            public @NotNull Codec<T> codec() {
                return pCodecFactory.apply(this);
            }
        });
    }
}
