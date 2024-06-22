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
import org.confluence.mod.client.model.entity.BeeProjectileModel;
import org.confluence.mod.entity.projectile.BeeProjectile;
import org.jetbrains.annotations.NotNull;

public class BeeProjectileRenderer extends EntityRenderer<BeeProjectile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/bee_projectile.png");
    private final BeeProjectileModel model;

    public BeeProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BeeProjectileModel(pContext.bakeLayer(BeeProjectileModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BeeProjectile pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(BeeProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.125F, -0.125F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI));
        if (entity.isGiant()) poseStack.scale(2.0F, 2.0F, 2.0F);
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
