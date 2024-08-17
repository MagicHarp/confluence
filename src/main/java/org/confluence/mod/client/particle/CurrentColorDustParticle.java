package org.confluence.mod.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.client.particle.opt.CurrentDustOptions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class CurrentColorDustParticle<T extends CurrentDustOptions> extends TextureSheetParticle {
    private final SpriteSet sprites;
    private final Vector3f fromColor;
    private final Vector3f toColor;
    protected CurrentColorDustParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, @NotNull T pOptions, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = pSprites;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.rCol = pOptions.getColor().x();
        this.gCol = pOptions.getColor().y();
        this.bCol = pOptions.getColor().z();
        this.quadSize *= 0.75F * pOptions.getScale();
        int i = (int)(8.0D / (this.random.nextDouble() * 0.8D + 0.2D));
        this.lifetime = (int)Math.max((float)i * pOptions.getScale() * 0.7, 1.0F);
        this.setSpriteFromAge(pSprites);

        this.fromColor = pOptions.getFromColor();
        this.toColor = pOptions.getToColor();
    }
    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    public float getQuadSize(float pScaleFactor) {
        return this.quadSize * Mth.clamp(((float)this.age + pScaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
    }
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }
    private void lerpColors(float pPartialTick) {
        float f = ((float)this.age + pPartialTick) / ((float)this.lifetime + 1.0F);
        Vector3f vector3f = (new Vector3f(this.fromColor)).lerp(this.toColor, f);
        this.rCol = vector3f.x();
        this.gCol = vector3f.y();
        this.bCol = vector3f.z();
    }

    public void render(@NotNull VertexConsumer pBuffer, @NotNull Camera pRenderInfo, float pPartialTicks) {
        this.lerpColors(pPartialTicks);
        super.render(pBuffer, pRenderInfo, pPartialTicks);
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<CurrentDustOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(@NotNull CurrentDustOptions pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CurrentColorDustParticle<>(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pType, this.sprites);
        }
    }
}
