package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.EaterOfWorldSegmentModel;
import org.confluence.mod.entity.boss.eow.EaterOfWorldPart;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EaterOfWorldSegmentRenderer extends GeoEntityRenderer<EaterOfWorldPart> {
    public EaterOfWorldSegmentRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EaterOfWorldSegmentModel());
    }

    @Override
    protected void applyRotations(EaterOfWorldPart animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick){
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick,animatable.xRotO,animatable.getXRot())));
    }

    @Override
    protected float getDeathMaxRotation(EaterOfWorldPart animatable){
        return 0;
    }
}
