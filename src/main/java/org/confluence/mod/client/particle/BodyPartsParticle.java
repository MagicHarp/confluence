package org.confluence.mod.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.particle.options.BodyPartsParticleOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BodyPartsParticle extends TextureSheetParticle {
    private final float uo;
    private final float vo;
    private final float uf;
    private final float vf;

    @Override
    public void render(@NotNull VertexConsumer pBuffer, @NotNull Camera pRenderInfo, float pPartialTicks){
        RenderSystem.setShaderTexture(0,Confluence.asResource("textures/atlas/entity.png"));
        super.render(pBuffer, pRenderInfo, pPartialTicks);
    }

    protected BodyPartsParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, ResourceLocation texture){
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
//        this.lifetime = 600;
        this.lifetime = level.getRandom().nextInt(15, 40);
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        gravity = 2;

        setSprite(Confluence.entityAtlas.getSprite(texture));
        int spriteWidth = sprite.contents().width();
        int spriteHeight = sprite.contents().height();

        // 取4个像素需要在getU1 getV1中偏移多少
        uf = 64f / spriteWidth;
        vf = 64f / spriteHeight;
        // 起始点
        vo = this.random.nextFloat() * (16 - vf);
        uo = this.random.nextFloat() * (16 - uf);
    }

    @Override
    public void tick(){
        super.tick();
    }

    // sprite.getU和getV的参数范围是0-16，不管贴图大小是多少都是0-16
    protected float getU0(){
        return this.sprite.getU(uo);
    }

    protected float getU1(){
        return this.sprite.getU(uo + uf);
    }

    protected float getV0(){
        return this.sprite.getV(vo);
    }

    protected float getV1(){
        return this.sprite.getV(vo + vf);
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.TERRAIN_SHEET;
    }

    public static class Provider implements ParticleProvider<BodyPartsParticleOptions> {
        @Nullable
        @Override
        public Particle createParticle(BodyPartsParticleOptions options, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
            int entityId = options.entityId;
            Entity entity = pLevel.getEntity(entityId);
            if(entity == null){
                return null;
            }
            ResourceLocation textureLocation = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).getTextureLocation(entity);
            String path = textureLocation.getPath();
            if(path.startsWith("textures/")){
                path = path.substring(9);
            }
            if(path.endsWith(".png")){
                path = path.substring(0, path.length() - 4);
            }

            RandomSource random = pLevel.getRandom();
            double vx = options.vx + ModUtils.nextDouble(random, -0.2, 0.2);
            double vy = options.vy + ModUtils.nextDouble(random, -0.3, 0.1);
            double vz = options.vz + ModUtils.nextDouble(random, -0.2, 0.2);
            return new BodyPartsParticle(pLevel, pX, pY, pZ, vx, vy, vz, textureLocation.withPath(path));
        }
    }
}
