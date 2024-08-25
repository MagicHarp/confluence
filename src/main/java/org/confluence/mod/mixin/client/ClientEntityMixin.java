package org.confluence.mod.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.mixinauxiliary.IEntity;
import org.confluence.mod.mixinauxiliary.SelfGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ClientEntityMixin implements SelfGetter<Entity> {
    @Shadow
    protected abstract BlockPos getOnPos(float pYOffset);

    @Shadow
    public boolean verticalCollision;

    @Inject(method = "getEyeHeight()F", at = @At("RETURN"), cancellable = true)
    private void eyeHeight(CallbackInfoReturnable<Float> cir) {
        if (self() instanceof LocalPlayer localPlayer && GravitationHandler.isShouldRot()) {
            cir.setReturnValue(localPlayer.getDimensions(localPlayer.getPose()).height * 0.15F);
        }
    }

    @ModifyVariable(method = "setOnGroundWithKnownMovement(ZLnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"), argsOnly = true)
    private boolean checkVertical(boolean bool) {
        if (!bool) return verticalCollision && self() instanceof LocalPlayer && GravitationHandler.isShouldRot();
        return true;
    }

    @Inject(method = "getOnPosLegacy", at = @At("RETURN"), cancellable = true)
    private void getOnPosAbove(CallbackInfoReturnable<BlockPos> cir) {
        if (self() instanceof Player player) {
            if (player.isLocalPlayer() ? GravitationHandler.isShouldRot() : ((IEntity) player).confluence$isShouldRot()) {
                cir.setReturnValue(getOnPos(-2.2F));
            }
        }
    }
}
