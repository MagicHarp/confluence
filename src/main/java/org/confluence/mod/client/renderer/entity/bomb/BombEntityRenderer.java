package org.confluence.mod.client.renderer.entity.bomb;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.jetbrains.annotations.NotNull;

public abstract class BombEntityRenderer<E extends BaseBombEntity> extends EntityRenderer<E> {
    public BombEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public abstract @NotNull ResourceLocation getTextureLocation(@NotNull E pEntity);

    public abstract EntityModel<E> getModel(E pEntity);

    @Override
    public void render(@NotNull E pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(-0.0625, 0.1875, -0.0625);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getYRot() - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotation(-Mth.lerp(pPartialTick, pEntity.rotateO, pEntity.rotate)));
        pPoseStack.translate(0.0625, -0.1875, 0.0625);
        EntityModel<E> model = getModel(pEntity);
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
