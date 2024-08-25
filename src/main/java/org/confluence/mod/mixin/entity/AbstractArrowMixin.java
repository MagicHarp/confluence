package org.confluence.mod.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.mixinauxiliary.IAbstractArrow;
import org.confluence.mod.mixinauxiliary.SelfGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements IAbstractArrow, SelfGetter<AbstractArrow> {
    @Unique
    private static final EntityDataAccessor<Boolean> FROM_SHORT_BOW = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean confluence$isShootFromShortBow() {
        return self().getEntityData().get(FROM_SHORT_BOW);
    }

    @Override
    public void confluence$setShootFromShortBow(boolean is) {
        self().getEntityData().set(FROM_SHORT_BOW, is);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineShortBow(CallbackInfo ci) {
        self().getEntityData().define(FROM_SHORT_BOW, false);
    }

    @ModifyVariable(method = "shoot", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float boost(float velocity) {
        if (((AbstractArrow) (Object) this).getOwner() instanceof LivingEntity living && living.hasEffect(ModEffects.ARCHERY.get())) {
            return velocity * 1.2F;
        }
        return velocity;
    }

    @ModifyExpressionValue(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;length()D"))
    private double shortBowLength(double original) {
        return confluence$isShootFromShortBow() ? 1.0 : original;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addShortBow(CompoundTag pCompound, CallbackInfo ci) {
        pCompound.putBoolean("confluence:fromShortBow", confluence$isShootFromShortBow());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readShortBow(CompoundTag pCompound, CallbackInfo ci) {
        confluence$setShootFromShortBow(pCompound.getBoolean("confluence:fromShortBow"));
    }
}
