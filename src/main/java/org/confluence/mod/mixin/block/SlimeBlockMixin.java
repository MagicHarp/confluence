package org.confluence.mod.mixin.block;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SlimeBlock.class)
public abstract class SlimeBlockMixin {
    @Inject(method = "bounceUp", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void bounceDown(Entity pEntity, CallbackInfo ci, Vec3 vec3) {
        if (vec3.y > 0.0) {
            double d0 = pEntity instanceof LivingEntity ? 1.0D : 0.8D;
            pEntity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }
}
