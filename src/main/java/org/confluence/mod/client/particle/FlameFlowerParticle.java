package org.confluence.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlameFlowerParticle extends TextureSheetParticle {


    protected FlameFlowerParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet){
        super(pLevel, pX, pY, pZ);
        setLifetime(8);
        hasPhysics = false;
        xd = (pLevel.random.nextDouble() - 0.5) / 20;
        zd = (pLevel.random.nextDouble() - 0.5) / 20;
        yd = 0.2;
        this.sprite = spriteSet.get(0, 8);
        scale(0.85f);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick(){
        super.tick();
        scale(0.7f);

    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
            return new FlameFlowerParticle(pLevel, pX, pY, pZ, spriteSet);
        }
    }
}
