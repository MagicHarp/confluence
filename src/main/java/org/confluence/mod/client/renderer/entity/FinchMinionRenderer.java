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
import org.confluence.mod.entity.minion.FinchMinionEntity;

public class FinchMinionRenderer extends EntityRenderer<FinchMinionEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/bee_projectile.png");
    private final BeeProjectileModel model;

    public FinchMinionRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BeeProjectileModel(pContext.bakeLayer(BeeProjectileModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(FinchMinionEntity finchMinionEntity) {
        return TEXTURE;
    }

    @Override
    public void render(FinchMinionEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.00F, 0.125F, -0.125F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
        pPoseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI));
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
