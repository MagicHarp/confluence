package org.confluence.mod.mixin.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.mixinauxiliary.IEntity;
import org.confluence.mod.mixinauxiliary.ILivingEntityRenderer;
import org.confluence.mod.util.DeathAnimUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> implements ILivingEntityRenderer {
    @Shadow protected M model;

    @Unique private LivingEntity confluence$rendering;
    @Unique private final List<ModelPart> confluence$partsCache = new ArrayList<>();

    @Inject(method = "isEntityUpsideDown", at = @At("RETURN"), cancellable = true)
    private static void upsideDown(LivingEntity living, CallbackInfoReturnable<Boolean> cir) {
        if ((living instanceof LocalPlayer && GravitationHandler.isShouldRot()) || (living instanceof RemotePlayer && ((IEntity)living).confluence$isShouldRot())) {
            cir.setReturnValue(true);
        }
    }

    // 有潜在的兼容性问题，如果别的mod加了旋转可能ordinal会变
    @ModifyArg(method = "setupRotations", at = @At(value = "INVOKE", target = "Lcom/mojang/math/Axis;rotationDegrees(F)Lorg/joml/Quaternionf;", ordinal = 1))
    private float rot(float pDegrees){
        if(DeathAnimUtils.hasDeathAnimOptions(confluence$rendering)){
            return 0;
        }
        return pDegrees;
    }

    @ModifyArg(method = "getOverlayCoords",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;v(Z)I"))
    private static boolean v(boolean pHurt){
        return false;
    }

    @Override
    public void confluence$setRendering(LivingEntity living){
        confluence$rendering = living;
    }

    @Override
    public List<ModelPart> confluence$getPartsCache(){
        return confluence$partsCache;
    }
}
