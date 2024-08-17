package org.confluence.mod.mixinauxiliary;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import software.bernie.geckolib.cache.object.GeoBone;

public interface ILivingEntity {
    float[] confluence$getMotionsForBone(GeoBone bone);
    float[] confluence$getMotionsForPart(ModelPart part);
    PartPose confluence$getPoseForPart(ModelPart part);
    int confluence$landTick();
    double confluence$landMotionY();
}
