package org.confluence.mod.mixinauxiliary;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public interface IModelPart {
    void confluence$setRenderingLiving(LivingEntity living);
    void confluence$setRenderingPartialTick(float partialTick);
    float[] confluence$parentOffset(float... offset);
    ModelPart confluence$root(ModelPart... root);
}
