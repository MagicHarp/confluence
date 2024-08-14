package org.confluence.mod.mixinauxiliary;

import net.minecraft.world.entity.LivingEntity;

public interface IModelPart {
    void confluence$setRenderingLiving(LivingEntity living);
    void confluence$setRenderingPartialTick(float partialTick);
}
