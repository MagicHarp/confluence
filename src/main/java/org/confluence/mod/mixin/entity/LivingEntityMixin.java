package org.confluence.mod.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.block.natural.ThinIceBlock;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.harmful.StonedEffect;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.expert.RoyalGel;
import org.confluence.mod.item.curio.miscellaneous.IFlowerBoots;
import org.confluence.mod.item.curio.movement.IFluidWalk;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModDamageTypes;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.mixinauxiliary.IEntity;
import org.confluence.mod.mixinauxiliary.ILivingEntity;
import org.confluence.mod.mixinauxiliary.SelfGetter;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.DeathAnimUtils;
import org.confluence.mod.util.PlayerUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntity, SelfGetter<LivingEntity> {
    private LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    private boolean confluence$bled = false;  // bleed的过去式
    @Unique
    private boolean confluence$breakingEasyCrashBlock = false;

    @Shadow
    public abstract boolean isDeadOrDying();

    @Override
    public void confluence$setBreakEasyCrashBlock(boolean breaking) {
        this.confluence$breakingEasyCrashBlock = breaking;
    }

    @Override
    public boolean confluence$isBreakEasyCrashBlock() {
        return confluence$breakingEasyCrashBlock;
    }

    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void multiY(CallbackInfoReturnable<Float> cir) {
        LivingEntity self = self();
        if (self.hasEffect(ModEffects.STONED.get()) || self.hasEffect(ModEffects.SHIMMER.get())) {
            cir.setReturnValue(0.0F);
        } else if (self instanceof Player player) {
            player.getCapability(AbilityProvider.CAPABILITY)
                    .ifPresent(playerAbility -> cir.setReturnValue((float) (cir.getReturnValue() * playerAbility.getJumpBoost())));
        }
    }

    @ModifyConstant(method = "hurt", constant = @Constant(intValue = 20))
    private int invulnerable1(int constant) {
        return c$getInvulnerableTime(constant);
    }

    @ModifyConstant(method = "handleDamageEvent", constant = @Constant(intValue = 20))
    private int invulnerable2(int constant) {
        return c$getInvulnerableTime(constant);
    }

    @Unique
    private int c$getInvulnerableTime(int constant) {
        if (self() instanceof Player player) {
            AtomicInteger time = new AtomicInteger(constant);
            player.getCapability(AbilityProvider.CAPABILITY)
                    .ifPresent(playerAbility -> time.set(playerAbility.getInvulnerableTime()));
            return time.get();
        }
        return constant;
    }

    @Inject(method = "checkFallDamage", at = @At("HEAD"), cancellable = true)
    private void fall(double motionY, boolean onGround, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity self = self();
        if (self.hasEffect(ModEffects.SHIMMER.get())) self.fallDistance = 0.0F;
        else if (self.hasEffect(ModEffects.STONED.get())) self.fallDistance += 3.0F;
        if (self.fallDistance >= 2.5F && blockState.is(ModTags.Blocks.EASY_CRASH) && CuriosUtils.noSameCurio(self, ThinIceBlock.IceSafe.class)) {
            if (!level().isClientSide) {
                BlockPos.betweenClosedStream(self.getBoundingBox().move(0.0, -0.5, 0.0)).forEach(pos -> {
                    if (pos.equals(blockPos) || level().getBlockState(pos).is(ModTags.Blocks.EASY_CRASH)) {
                        level().destroyBlock(pos, true, self);
                    }
                });
            }
            this.confluence$breakingEasyCrashBlock = true;
            self.setOnGround(false);
            super.checkFallDamage(motionY, false, blockState, blockPos);
            ci.cancel();
        } else {
            this.confluence$breakingEasyCrashBlock = false;
        }
    }

    @ModifyArg(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"), index = 2)
    private double fall2(double pPosY) {
        if (self() instanceof Player player) {
            if (PlayerUtils.isServerNotFake(player) && ((IEntity) player).confluence$isShouldRot()) {
                return pPosY + getDimensions(player.getPose()).height;
            }
        }
        return pPosY;
    }

    @Inject(method = "canStandOnFluid", at = @At("RETURN"), cancellable = true)
    private void standOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (fluidState.isEmpty()) return;
        LivingEntity self = self();
        if (self.isCrouching()) {
            cir.setReturnValue(false);
        } else {
            if (self.hasEffect(ModEffects.WATER_WALKING.get())) {
                cir.setReturnValue(true);
            } else {
                CuriosUtils.findCurio(self, IFluidWalk.class).ifPresent(iFluidWalk ->
                        cir.setReturnValue(iFluidWalk.canStandOn(fluidState)));
            }
        }
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canStandOnFluid(Lnet/minecraft/world/level/material/FluidState;)Z"))
    private boolean onFluid(LivingEntity instance, FluidState fluidState) {
        return false;
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    private Vec3 waterWalk(Vec3 par1) {
        LivingEntity self = self();
        FluidState fluidstate = self.level().getFluidState(self.blockPosition());
        FluidType fluidType = fluidstate.getType().getFluidType();
        if (self.getEyeInFluidType() == ForgeMod.EMPTY_TYPE.get()) {
            if (self.canStandOnFluid(fluidstate)) {
                AttributeInstance instance = self.getAttribute(Attributes.MOVEMENT_SPEED);
                if (instance == null) return par1;
                double horizon = Math.min(0.91 * self.getSpeed() / instance.getBaseValue(), 0.93);
                return self.getDeltaMovement().multiply(horizon, 1.0, horizon);
            }
        }
        if (fluidType == ModFluids.HONEY.fluidType().get()) {
            if (!self.level().isClientSide && self instanceof Animal || self instanceof ServerPlayer) {
                self.addEffect(new MobEffectInstance(ModEffects.HONEY.get(), 600));
            }
            par1 = par1.scale(0.6);
        } else if (fluidType == ModFluids.SHIMMER.fluidType().get()) {
            if (!self.level().isClientSide && self.getEyeInFluidType() == ModFluids.SHIMMER.fluidType().get() && !self.hasEffect(ModEffects.SHIMMER.get())) {
                self.addEffect(new MobEffectInstance(ModEffects.SHIMMER.get(), MobEffectInstance.INFINITE_DURATION));
            }
            par1 = par1.add(0.0, -0.003, 0.0);
        }
        if (self.hasEffect(ModEffects.FLIPPER.get())) {
            return new Vec3(0.96, par1.y, 0.96);
        }
        return par1;
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 2))
    private Vec3 lavaJump(Vec3 par1) {
        return c$getInLavaVec(par1);
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 4))
    private Vec3 lavaWalk(Vec3 par1) {
        return c$getInLavaVec(par1);
    }

    @Unique
    private Vec3 c$getInLavaVec(Vec3 par1) {
        LivingEntity self = self();
        if (self.getEyeInFluidType() != ForgeMod.EMPTY_TYPE.get()) return par1;
        if (self.canStandOnFluid(self.level().getFluidState(self.blockPosition()))) {
            AttributeInstance instance = self.getAttribute(Attributes.MOVEMENT_SPEED);
            if (instance == null) return par1;
            double horizon = Math.min(0.91 * self.getSpeed() / instance.getBaseValue(), 0.93);
            return self.getDeltaMovement().multiply(horizon, 1.0, horizon);
        } else if (self.hasEffect(ModEffects.FLIPPER.get())) {
            return new Vec3(0.86, par1.y * 0.91, 0.86);
        }
        return par1;
    }

    // todo 改为真正的加速度
//    @Inject(method = "getFrictionInfluencedSpeed", at = @At("RETURN"), cancellable = true)
//    private void speed(float friction, CallbackInfoReturnable<Float> cir) {
//        if (CuriosUtils.hasCurio(self(), CurioItems.MAGILUMINESCENCE.get())) {
//            cir.setReturnValue(cir.getReturnValue() * 1.75F);
//        }
//    }

    @WrapOperation(method = "getDamageAfterArmorAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterAbsorb(FFF)F"))
    private float passArmor(float pDamage, float pTotalArmor, float pToughnessAttribute, Operation<Float> original, @Local(argsOnly = true) DamageSource pDamageSource) {
        if (pDamageSource.getEntity() instanceof LivingEntity attacker) {
            AttributeInstance attributeInstance = attacker.getAttribute(ModAttributes.getArmorPass());
            if (attributeInstance != null) pTotalArmor -= (float) attributeInstance.getValue();
            if (pDamageSource.is(ModDamageTypes.STAR_CLOAK)) pTotalArmor -= 3.0F;
        }
        return original.call(pDamage, Math.max(pTotalArmor, 0.0F), pToughnessAttribute);
    }

    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 confused(Vec3 vec3) {
        LivingEntity self = self();
        if (self.hasEffect(ModEffects.SHIMMER.get())) return Vec3.ZERO;
        if (self.hasEffect(ModEffects.STONED.get())) return StonedEffect.DOWN;
        return self.hasEffect(ModEffects.CONFUSED.get()) ? vec3.reverse() : vec3;
    }

    @Inject(method = "onChangedBlock", at = @At("TAIL"))
    private void onMoved(BlockPos pPos, CallbackInfo ci) {
        IFlowerBoots.apply(self());
    }

    @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"), cancellable = true)
    private void notAttack(LivingEntity pTarget, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && RoyalGel.apply(self(), pTarget)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canFreeze", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void checkFreeze(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            cir.setReturnValue(CuriosUtils.noSameCurio(self(), CurioItems.HAND_WARMER.get()));
        }
    }

    /**
     * @author voila
     */
    @Inject(method = "tickDeath", at = @At("HEAD"))
    private void tickDeath(CallbackInfo ci) {
        DeathAnimOptions options = DeathAnimUtils.getDeathAnimOptions(self());
        if (options != null) {
            self().addDeltaMovement(new Vec3(0, -options.getExtraGravity(), 0));
        }
    }

    @Inject(method = "hurt", at = @At("RETURN"))
    private void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (!confluence$bled && isDeadOrDying()) { //TODO: 开关
            DeathAnimUtils.addBloodParticles(self());
            confluence$bled = true;
        }
    }


    // 死了就别乱转了
    @Override
    public void setYRot(float pYRot) {
        if (!(self() instanceof EnderDragon) && self().isDeadOrDying()) {
            return;
        }
        super.setYRot(pYRot);
    }
}
