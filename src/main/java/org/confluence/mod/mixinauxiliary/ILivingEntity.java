package org.confluence.mod.mixinauxiliary;

import net.minecraft.client.model.geom.ModelPart;
import software.bernie.geckolib.cache.object.GeoBone;

public interface ILivingEntity {
    float[] confluence$getMotionsForBone(GeoBone bone);
    float[] confluence$getMotionsForPart(ModelPart bone);
}
