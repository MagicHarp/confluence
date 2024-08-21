package org.confluence.mod.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.curio.StepStoolModel;
import org.joml.Matrix4f;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class StepStoolRenderer implements ICurioRenderer {
    private static final RenderType CUTOUT = RenderType.entityCutout(new ResourceLocation(Confluence.MODID, "textures/curio/step_stool.png"));

    private final StepStoolModel model;

    public StepStoolRenderer() {
        this.model = new StepStoolModel((Minecraft.getInstance().getEntityModels().bakeLayer(StepStoolModel.LAYER_LOCATION)));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int light, float v, float v1, float v2, float v3, float v4, float v5) {
        int step = itemStack.getTag() == null ? 0 : itemStack.getTag().getInt("step");
        if (step > 0) {
            Matrix4f pose = poseStack.last().pose();
            poseStack.pushPose();
            //poseStack.mulPoseMatrix(pose.getUnnormalizedRotation(new Quaternionf()).get(new Matrix4f()).cofactor3x3());
            for (int i = 0; i < step; i++) {
                poseStack.translate(0.0F, 1.0F, 0.0F);
                model.renderToBuffer(poseStack, multiBufferSource.getBuffer(CUTOUT), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            poseStack.popPose();
        }
    }
}
