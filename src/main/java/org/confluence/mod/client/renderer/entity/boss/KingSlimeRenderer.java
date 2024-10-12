package org.confluence.mod.client.renderer.entity.boss;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;
import org.confluence.mod.client.model.entity.CrownOfKingSlimeModel;
import org.confluence.mod.client.renderer.entity.CustomSlimeRenderer;
import org.confluence.mod.client.renderer.entity.model.CrownOfKingSlimeModelRenderer;
import org.joml.Quaternionf;

public class KingSlimeRenderer extends CustomSlimeRenderer {
    private final CrownOfKingSlimeModel model;

    public KingSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, "king");
        this.model = new CrownOfKingSlimeModel(context.bakeLayer(CrownOfKingSlimeModel.LAYER_LOCATION));
    }

    @Override
    public void render(Slime pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, 1.0625F + pEntity.getDimensions(pEntity.getPose()).height, 0.0F);
        pPoseStack.mulPose(CrownOfKingSlimeModelRenderer.FLIP_Y.rotateY(pEntity.getYHeadRot() * Mth.DEG_TO_RAD + Mth.PI, new Quaternionf()));
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(CrownOfKingSlimeModel.RENDER_TYPE), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
