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
import org.confluence.mod.client.model.entity.ShurikenProjectileModel;
import org.confluence.mod.entity.projectile.ShurikenProjectile;
import org.jetbrains.annotations.NotNull;

public class ShurikenProjectileRenderer extends EntityRenderer<ShurikenProjectile> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/shuriken_projectile.png");
    private final ShurikenProjectileModel model;

    public ShurikenProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new ShurikenProjectileModel(pContext.bakeLayer(ShurikenProjectileModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(ShurikenProjectile pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(ShurikenProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.00F, 0.125F, -0.125F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YP.rotation(entity.level().getGameTime() + partialTick));
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
