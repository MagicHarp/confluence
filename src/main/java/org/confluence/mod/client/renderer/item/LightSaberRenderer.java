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
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LightSaberRenderer extends GeoItemRenderer<LightSaber> {

    private boolean isTurningOn = false;
    AutoGlowingGeoLayer<LightSaber> autoGlowingGeoLayer = new AutoGlowingGeoLayer<>(this){
        @Override
        protected RenderType getRenderType(LightSaber animatable) {
            animatable.frame++;

            //可以修改贴图
            //return RenderType.energySwirl(((LightSaberModel)this.getGeoModel()).texture, (float) (Math.sin(frame * 0.01F)/4+Math.sin(frame * 0.02F)/2 + Math.sin(frame * 0.04F))%0.1f, (float) (Math.cos(frame * 0.01F)/4+Math.cos(frame * 0.02F)/2 + Math.cos(frame * 0.04F)));
            return RenderType.energySwirl(((LightSaberModel)this.getGeoModel()).texture, 0, (float) (Math.cos(animatable.frame * 0.001F)/4+Math.cos(animatable.frame * 0.002F)/2 + Math.cos(animatable.frame * 0.004F)));

        }

        @Override
        public void render(PoseStack poseStack, LightSaber animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            if(!isTurningOn) return;
            super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
    };
    public LightSaberRenderer(String color) {
        super(new LightSaberModel(color));
        addRenderLayer(autoGlowingGeoLayer);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, LightSaber animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        CompoundTag nbt = getCurrentItemStack().getTag();

        isTurningOn = true;
        if (nbt != null && nbt.getBoolean("turnOff")) {
            isTurningOn = false;
            AnimationController<GeoAnimatable> controller = animatable
                .getAnimatableInstanceCache()
                .getManagerForId(nbt.getLong("GeckoLibID"))
                .getAnimationControllers()
                .get("light");
            if (controller != null && controller.getCurrentRawAnimation() != LightSaber.TURN_OFF) {
                model = getGeoModel().getBakedModel(LightSaberModel.barModel);

            }
        }
        /*
        poseStack.pushPose();
        poseStack.scale(2,2,2);
        var bModel = getGeoModel().getBakedModel(LightSaberModel.model);
        for (GeoBone group : bModel.topLevelBones()) {
            this.renderRecursively(poseStack, animatable, group, barRendertype, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
        poseStack.popPose();
        */
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }


}
