package org.confluence.mod.client.renderer.entity.Bombs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.bomb.BouncyBombEntityModel;
import org.confluence.mod.entity.projectile.bombs.BouncyBombEntity;
import org.jetbrains.annotations.NotNull;

public class BouncyBombEntityRenderer extends EntityRenderer<BouncyBombEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/bomb/bouncy_bomb_entity.png");
    private final BouncyBombEntityModel model;

    public BouncyBombEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BouncyBombEntityModel(pContext.bakeLayer(BouncyBombEntityModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BouncyBombEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(BouncyBombEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.00F, 0.125F, 0.00F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI));
        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
