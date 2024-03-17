package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.BulletEntity;
import org.jetbrains.annotations.NotNull;

public class BulletRenderer extends EntityRenderer<BulletEntity> {
    private ResourceLocation texture;

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity bulletEntity) {
        if (texture == null) {
            texture = new ResourceLocation(Confluence.MODID, "textures/entity/bullet/" + bulletEntity.getBulletName() + "_bullet.png");
        }
        return texture;
    }

    @Override
    public void render(BulletEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, multiBufferSource, packedLight);
    }
}
