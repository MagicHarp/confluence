package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.CthulhuEyeModel;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CthulhuEyeRenderer extends GeoEntityRenderer<CthulhuEye> {
    public CthulhuEyeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CthulhuEyeModel());
    }

    @Override
    protected void applyRotations(CthulhuEye animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick){
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);

        var syncRot = animatable.getRot();
        poseStack.mulPose(Axis.XP.rotationDegrees(-syncRot.x));
        poseStack.translate(0,0.5,0);
    }

    @Override
    protected float getDeathMaxRotation(CthulhuEye animatable){
        return 0;
    }
}
