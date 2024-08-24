package org.confluence.mod.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.confluence.mod.client.particle.options.LightsBaneParticleOptions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class LightsBaneParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private final Vector3f direction;

    protected LightsBaneParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet sprites, Vector3f dir){
        super(pLevel, pX, pY, pZ);
        this.spriteSet = sprites;
        this.direction = dir;
        lifetime = 16;
        setSpriteFromAge(spriteSet);
        scale(5);
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks){
        // TODO: 倾斜方向
        super.render(pBuffer, pRenderInfo, pPartialTicks);
    }

    @Override
    public void tick(){
        setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<LightsBaneParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(LightsBaneParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
            return new LightsBaneParticle(level, x, y, z, spriteSet, type.direction);
        }
    }
}
