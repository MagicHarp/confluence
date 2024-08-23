package org.confluence.mod.mixinauxiliary;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;

public interface IModelPart {
    void confluence$setRenderingLiving(LivingEntity living);
    void confluence$setRenderingPartialTick(float partialTick);
    float[] confluence$parentOffset(float... offset);
    ModelPart confluence$root(ModelPart... root);
    /** 读/写<strong>模型</strong>落地的速度 */
    Vec2 confluence$landMotion(Vec2... root);
    boolean confluence$isSkull(boolean... isSkull);
}
