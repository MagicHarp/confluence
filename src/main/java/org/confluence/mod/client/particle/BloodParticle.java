package org.confluence.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.RandomSource;
import org.confluence.mod.client.particle.options.BloodParticleOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class BloodParticle extends TextureSheetParticle {

    protected BloodParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float r, float g, float b){
        super(level, x, y, z);
        this.lifetime = level.getRandom().nextInt(15, 40);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        gravity = 2;

        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        scale(0.8f);
    }

    @Override
    public void tick(){
        super.tick();
//        float gravity = 0.08f;
//        this.yd -= gravity;
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<BloodParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BloodParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
            RandomSource random = level.getRandom();
            double vx = type.vx + ModUtils.nextDouble(random, -0.2, 0.2);
            double vy = type.vy + ModUtils.nextDouble(random, -0.3, 0.1);
            double vz = type.vz + ModUtils.nextDouble(random, -0.2, 0.2);
            BloodParticle particle = new BloodParticle(level, x, y, z, vx, vy, vz, type.r, type.g, type.b);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
