package org.confluence.mod.client.renderer.entity.boss;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.confluence.mod.client.model.entity.boss.GeoNormalModel;
import org.confluence.mod.entity.boss.geoEntity.EaterOfWorldSegment;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EaterOfWorldSegmentRenderer extends GeoEntityRenderer<EaterOfWorldSegment> {
    static GeoNormalModel<EaterOfWorldSegment> tailModel = new GeoNormalModel<>("eater_of_world_tail");
    public EaterOfWorldSegmentRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GeoNormalModel<>("eater_of_world_segment"));
    }

    public GeoModel<EaterOfWorldSegment> getGeoModel() {
        if(animatable.ifTail)
            return tailModel;
//        return animatable.head.segments.size() == animatable.segmentIndex?tailModel:this.model;
        return this.model;
    }

    @Override
    public void preRender(PoseStack poseStack, EaterOfWorldSegment animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay,  red,  green, blue, alpha);
        poseStack.scale(3.5f,3.5f,3.5f);
        poseStack.translate(0,0,0);

        //poseStack.mulPose(Axis.YN.rotationDegrees(-90));
        //poseStack.mulPose(Axis.XN.rotationDegrees(animatable.xRotO));
        poseStack.mulPose(Axis.ZN.rotationDegrees(-(float) (Math.sin(Math.toRadians(animatable.yRotO)) * animatable.xRotO)));
        poseStack.mulPose(Axis.XN.rotationDegrees(-(float) (Math.cos(Math.toRadians(animatable.yRotO)) * animatable.xRotO)));

//        poseStack.mulPose(Axis.ZN.rotationDegrees(animatable.xRotO));

    }



/*
    @Override
    public @Nullable RenderType getRenderType(kulouwang animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.energySwirl(ResourceLocation.fromNamespaceAndPath(NeoMod.MODID,"texture/entity/creeper.png"), (float) Math.sin( partialTick),(float) Math.sin( partialTick));
    }
    */
}