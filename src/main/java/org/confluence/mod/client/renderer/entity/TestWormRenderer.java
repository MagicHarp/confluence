package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.DemonEyeModel;
import org.confluence.mod.client.model.entity.TestWormModel;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.worm.test.TestWormEntity;
import org.confluence.mod.entity.worm.test.TestWormPart;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestWormRenderer extends GeoEntityRenderer<TestWormPart> {
    public TestWormRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TestWormModel());
    }

    @Override
    protected void applyRotations(TestWormPart animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick){
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick,animatable.xRotO,animatable.getXRot())));
    }

    @Override
    protected float getDeathMaxRotation(TestWormPart animatable){
        return 0;
    }
}
