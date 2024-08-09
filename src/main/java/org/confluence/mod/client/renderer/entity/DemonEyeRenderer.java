package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.DemonEyeModel;
import org.confluence.mod.entity.demoneye.DemonEye;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DemonEyeRenderer extends GeoEntityRenderer<DemonEye> {
    public DemonEyeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DemonEyeModel());
    }

    @Override
    protected void applyRotations(DemonEye animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick){
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick,animatable.xRotO,animatable.getXRot())));
    }
}
