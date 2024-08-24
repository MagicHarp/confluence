package org.confluence.mod.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LightsBaneDustParticle extends TextureSheetParticle {
    protected LightsBaneDustParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet){
        super(pLevel, pX, pY, pZ);
        lifetime = 16;
        setSprite(spriteSet.get(0, 16));
        quadSize = 0.5f;
        xd = 0.05;
        yd = 0.05;
        zd = 0.05;
        roll = 45f;
        oRoll = 45f;  // TODO: 临时的
    }

    @Override
    public void tick(){
        level.addParticle(ModParticles.LIGHTS_BANE_FADE.get(), x, y, z, 0, 0, 0);
        xd -= (1f / lifetime * 0.05);
        yd -= ( 1f / lifetime * 0.05);
        zd -= ( 1f / lifetime * 0.05);
        if(xd < 0.001){
            remove();  // TODO: 临时的
        }
        super.tick();
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
