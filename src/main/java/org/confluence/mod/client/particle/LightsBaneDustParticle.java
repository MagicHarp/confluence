package org.confluence.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LightsBaneDustParticle extends TextureSheetParticle {

    protected LightsBaneDustParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet){
        super(pLevel, pX, pY, pZ);
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
            return new LightsBaneDustParticle(pLevel, pX, pY, pZ, spriteSet);
        }
    }

}
