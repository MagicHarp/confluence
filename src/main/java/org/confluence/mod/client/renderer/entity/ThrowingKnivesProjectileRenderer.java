package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.ThrowingKnivesProjectileModel;
import org.confluence.mod.entity.projectile.ThrowingKnivesProjectile;
import org.jetbrains.annotations.NotNull;

public class ThrowingKnivesProjectileRenderer extends EntityRenderer<ThrowingKnivesProjectile> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/throw_knives_projectile.png");
    private final ThrowingKnivesProjectileModel model;

    public ThrowingKnivesProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new ThrowingKnivesProjectileModel(pContext.bakeLayer(ThrowingKnivesProjectileModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(ThrowingKnivesProjectile pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(ThrowingKnivesProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.00F, 0.125F, 0.00F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI));
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
