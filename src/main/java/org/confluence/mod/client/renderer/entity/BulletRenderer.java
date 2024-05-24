package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.model.entity.BulletModel;
import org.confluence.mod.entity.projectile.BaseBulletEntity;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class BulletRenderer extends EntityRenderer<BaseBulletEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/bullet/amethyst_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/topaz_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/sapphire_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/emerald_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/ruby_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/amber_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/diamond_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/frost_bullet.png"),
        new ResourceLocation(MODID, "textures/entity/bullet/spark_bullet.png")
    };
    private final BulletModel model;

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BulletModel(context.bakeLayer(BulletModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseBulletEntity baseBulletEntity) {
        return TEXTURES[baseBulletEntity.getVariant().getId()];
    }

    @Override
    public void render(BaseBulletEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, multiBufferSource, packedLight);
    }

    @Override
    protected int getBlockLightLevel(@NotNull BaseBulletEntity bullet, @NotNull BlockPos pos) {
        return 15;
    }
}
