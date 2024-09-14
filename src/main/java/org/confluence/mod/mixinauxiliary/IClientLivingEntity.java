package org.confluence.mod.mixinauxiliary;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.cache.object.GeoBone;

public interface IClientLivingEntity {
    float[] confluence$getMotionsForBone(GeoBone bone);
    float[] confluence$getMotionsForPart(ModelPart part);
    PartPose confluence$getPoseForPart(ModelPart part);
    int confluence$landTicks();
    Vec3 confluence$landMotion();
}
