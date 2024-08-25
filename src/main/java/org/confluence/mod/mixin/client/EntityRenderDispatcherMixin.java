package org.confluence.mod.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.mixin.client.accessor.LevelRendererAccessor;
import org.confluence.mod.util.IOriented;
import org.confluence.mod.util.OBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Inject(method = "renderHitbox",at = @At("RETURN"))
    private static void renderObb(PoseStack pPoseStack, VertexConsumer pBuffer, Entity pEntity, float pPartialTicks, CallbackInfo ci){
        if(pEntity instanceof IOriented oe){
            OBB obb = oe.getOrientedBoundingBox();
            Vec3 pos = pEntity.position();
            AABB border = obb.getBorder().move(pos.scale(-1));
            LevelRendererAccessor.callRenderShape(pPoseStack, pBuffer, obb, -pos.x, -pos.y, -pos.z, 1, 1, 0, 1);
            LevelRenderer.renderLineBox(pPoseStack, pBuffer, border, 0, 1, 1, 1);
        }
    }
}
