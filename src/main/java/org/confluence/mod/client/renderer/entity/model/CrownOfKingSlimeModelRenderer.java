package org.confluence.mod.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.CrownOfKingSlimeModel;
import org.confluence.mod.entity.model.CrownOfKingSlimeModelEntity;

public class CrownOfKingSlimeModelRenderer extends EntityRenderer<CrownOfKingSlimeModelEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/model/crown_of_king_slime.png");
    private final CrownOfKingSlimeModel model;

    public CrownOfKingSlimeModelRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new CrownOfKingSlimeModel(pContext.bakeLayer(CrownOfKingSlimeModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(CrownOfKingSlimeModelEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(CrownOfKingSlimeModelEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(30));
        pPoseStack.translate(0.0F, 1.0625F, 0.0F);
        pPoseStack.mulPose(Axis.ZP.rotation(Mth.PI));
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(TEXTURE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }

    @Override
    public boolean shouldRender(CrownOfKingSlimeModelEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
