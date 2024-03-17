package org.confluence.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BulletParticle extends SimpleAnimatedParticle {
    public BulletParticle(ClientLevel clientLevel, double x, double y, double z, SpriteSet spriteSet, float gravity) {
        super(clientLevel, x, y, z, spriteSet, gravity);
        this.lifetime = 60;
        setSpriteFromAge(spriteSet);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            return new BulletParticle(level, x, y, z, sprites, 0);
        }
    }
}
