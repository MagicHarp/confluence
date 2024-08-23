package org.confluence.mod.mixinauxiliary;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface ILivingEntityRenderer {
    void confluence$setRendering(LivingEntity living);

    List<ModelPart> confluence$getPartsCache();
}
