package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.AmmoModel;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class AmmoRenderer extends EntityRenderer<BaseAmmoEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/ammo/base_ammo.png"),
        new ResourceLocation(MODID, "textures/entity/ammo/crystal_ammo.png"),
        new ResourceLocation(MODID, "textures/entity/ammo/golden_ammo.png"),
        new ResourceLocation(MODID, "textures/entity/ammo/party_ammo.png"),
        new ResourceLocation(MODID, "textures/entity/ammo/luminous_ammo.png")
    };
    private final AmmoModel model;

    public AmmoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new AmmoModel(pContext.bakeLayer(AmmoModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseAmmoEntity pEntity) {
        return TEXTURES[0];
    }

    @Override
    public void render(BaseAmmoEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getYRot() - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntity.getXRot()));
        pPoseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI));
        pPoseStack.scale(2.0F, 2.0F, 2.0F);
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(pEntity))), 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
