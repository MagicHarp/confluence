package org.confluence.mod.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.curio.HermesBootsModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HermesBootsRenderer implements ICurioRenderer {
    private static final RenderType CUTOUT = RenderType.entityCutout(Confluence.asResource("textures/curio/hermes_boots.png"));

    private final HermesBootsModel model;

    public HermesBootsRenderer() {
        this.model = new HermesBootsModel(Minecraft.getInstance().getEntityModels().bakeLayer(HermesBootsModel.LAYER_LOCATION));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followBodyRotations(slotContext.entity(), model);
        model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(CUTOUT), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
