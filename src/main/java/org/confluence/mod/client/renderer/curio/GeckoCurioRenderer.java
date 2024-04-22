package org.confluence.mod.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public abstract class GeckoCurioRenderer<A extends BaseCurioItem & GeoAnimatable> extends GeoObjectRenderer<A> implements ICurioRenderer {
    private ItemStack currentItemStack;

    public GeckoCurioRenderer(GeoModel<A> model, A animatable) {
        super(model);
        this.animatable = animatable;
    }

    public void modifyPoseStack(PoseStack poseStack) {
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentItemStack = stack;
        poseStack.pushPose();
        modifyPoseStack(poseStack);
        defaultRender(poseStack, animatable, renderTypeBuffer, null, null, 0, partialTicks, light);
        poseStack.popPose();
    }

    @Override
    public long getInstanceId(A animatable) {
        return GeoItem.getId(currentItemStack);
    }
}
