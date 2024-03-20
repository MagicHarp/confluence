package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.BulletModel;
import org.confluence.mod.entity.bullet.BaseBulletEntity;
import org.jetbrains.annotations.NotNull;

public class BulletRenderer extends EntityRenderer<BaseBulletEntity> {
    private final ResourceLocation texture;
    protected final BulletModel model;

    public BulletRenderer(EntityRendererProvider.Context context, String type) {
        super(context);
        this.texture = new ResourceLocation(Confluence.MODID, "textures/entity/bullet/" + type + "_bullet.png");
        this.model = new BulletModel(context.bakeLayer(BulletModel.RUBY_LAYER));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseBulletEntity baseBulletEntity) {
        return texture;
    }

    @Override
    public void render(BaseBulletEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(texture)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, multiBufferSource, packedLight);
    }

    @Override
    protected int getBlockLightLevel(@NotNull BaseBulletEntity bullet, @NotNull BlockPos pos) {
        return 15;
    }
}
