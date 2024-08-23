package org.confluence.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LightsBaneFadeParticle extends TextureSheetParticle {

    protected LightsBaneFadeParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet){
        super(pLevel, pX, pY, pZ);
        lifetime = 16;
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
            return new LightsBaneFadeParticle(pLevel, pX, pY, pZ, spriteSet);
        }
    }

}
