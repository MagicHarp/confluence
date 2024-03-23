package org.confluence.mod.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import org.confluence.mod.client.model.item.LightSaberModel;
import org.confluence.mod.item.sword.LightSaber;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LightSaberRenderer extends GeoItemRenderer<LightSaber> {
    public LightSaberRenderer(String color) {
        super(new LightSaberModel(color));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, LightSaber animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        CompoundTag nbt = getCurrentItemStack().getTag();
        if (nbt != null && nbt.getBoolean("turnOff")) {
            AnimationController<GeoAnimatable> controller = animatable
                .getAnimatableInstanceCache()
                .getManagerForId(nbt.getLong("GeckoLibID"))
                .getAnimationControllers()
                .get("light");
            if (controller != null && controller.getCurrentRawAnimation() != LightSaber.TURN_OFF) {
                model = getGeoModel().getBakedModel(LightSaberModel.barModel);
            }
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
