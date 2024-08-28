package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.TestWormSegmentModel;
import org.confluence.mod.entity.boss.eow.TestWormPart;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestWormSegmentRenderer extends GeoEntityRenderer<TestWormPart> {
    public TestWormSegmentRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TestWormSegmentModel());
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
