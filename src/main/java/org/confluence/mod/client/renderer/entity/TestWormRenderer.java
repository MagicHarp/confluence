package org.confluence.mod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.confluence.mod.client.model.entity.WormOverallModel;
import org.confluence.mod.entity.monster.worm.AbstractWormEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestWormRenderer extends GeoEntityRenderer<AbstractWormEntity> {
    public TestWormRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WormOverallModel());
    }

    @Override
    protected void applyRotations(AbstractWormEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick){
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick,animatable.xRotO,animatable.getXRot())));
    }

    @Override
    protected float getDeathMaxRotation(AbstractWormEntity animatable){
        return 0;
    }
}
