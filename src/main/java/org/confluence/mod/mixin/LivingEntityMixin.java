package org.confluence.mod.mixin;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.ThinIceBlock;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.GravitationEffect;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.combat.IArmorPass;
import org.confluence.mod.item.curio.movement.IFluidWalk;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.FallDistancePacketC2S;
import org.confluence.mod.util.CuriosUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void multiY(CallbackInfoReturnable<Float> cir) {
        c$getSelf().getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> cir.setReturnValue((float) (cir.getReturnValue() * playerAbility.getJumpBoost())));
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
        AtomicInteger time = new AtomicInteger(constant);
        c$getSelf().getCapability(AbilityProvider.CAPABILITY)
            .ifPresent(playerAbility -> time.set(playerAbility.getInvulnerableTime()));
        return time.get();
    }

    @Inject(method = "checkFallDamage", at = @At("HEAD"), cancellable = true)
    private void thinIceBlock(double motionY, boolean onGround, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity self = c$getSelf();
        if (motionY > 0.0 && self instanceof LocalPlayer && GravitationEffect.isShouldRot()) {
            self.fallDistance += motionY;
            NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(self.fallDistance));
        }
        if (self.hasEffect(ModEffects.STONED.get())) self.fallDistance += 3.0F;
        if (self.fallDistance >= 3.0F && blockState.is(ModBlocks.THIN_ICE_BLOCK.get()) && CuriosUtils.noSameCurio(self, ThinIceBlock.IceSafe.class)) {
            self.level().destroyBlock(blockPos, true, self);
            ci.cancel();
        }
    }

    @Inject(method = "canStandOnFluid", at = @At("RETURN"), cancellable = true)
    private void standOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        CuriosUtils.findCurio(c$getSelf(), IFluidWalk.class)
            .ifPresent(iFluidWalk -> cir.setReturnValue(iFluidWalk.canStandOn(fluidState)));
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canStandOnFluid(Lnet/minecraft/world/level/material/FluidState;)Z"))
    private boolean onFluid(LivingEntity instance, FluidState fluidState) {
        return false;
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    private Vec3 waterWalk(Vec3 par1) {
        return c$getWalkVec(par1);
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 2))
    private Vec3 lavaJump(Vec3 par1) {
        return c$getWalkVec(par1);
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 4))
    private Vec3 lavaWalk(Vec3 par1) {
        return c$getWalkVec(par1);
    }

    @Unique
    private Vec3 c$getWalkVec(Vec3 par1) {
        LivingEntity self = c$getSelf();
        if (self.getEyeInFluidType() != ForgeMod.EMPTY_TYPE.get()) return par1;
        if (self.canStandOnFluid(self.level().getFluidState(self.blockPosition()))) {
            AttributeInstance instance = self.getAttribute(Attributes.MOVEMENT_SPEED);
            if (instance == null) return par1;
            double horizon = Math.min(0.91 * self.getSpeed() / instance.getBaseValue(), 0.93);
            return self.getDeltaMovement().multiply(horizon, 1.0, horizon);
        }
        return par1;
    }

    @Inject(method = "getFrictionInfluencedSpeed", at = @At("RETURN"), cancellable = true)
    private void speed(float friction, CallbackInfoReturnable<Float> cir) {
        if (CuriosUtils.hasCurio(c$getSelf(), CurioItems.MAGILUMINESCENCE.get())) {
            cir.setReturnValue(cir.getReturnValue() * 1.75F);
        }
    }

    @ModifyArg(method = "getDamageAfterArmorAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterAbsorb(FFF)F"), index = 1)
    private float passArmor(float armor) {
        LivingEntity self = c$getSelf();
        AtomicDouble atomic = new AtomicDouble(armor);
        CuriosUtils.findCurio(self, IArmorPass.class)
            .ifPresent(iArmorPass -> atomic.addAndGet(-iArmorPass.getPassValue()));
        if (self.hasEffect(ModEffects.BROKEN_ARMOR.get())) {
            atomic.set(atomic.get() / 2.0);
        }
        return atomic.floatValue();
    }

    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 confused(Vec3 vec3) {
        LivingEntity self = c$getSelf();
        if (self.hasEffect(ModEffects.STONED.get())) return Vec3.ZERO;
        if (self.level().isClientSide && GravitationEffect.isShouldRot()) {
            return new Vec3(vec3.x * -1.0, vec3.y, vec3.z);
        }
        return self.hasEffect(ModEffects.CONFUSED.get()) ? vec3.reverse() : vec3;
    }

    @Unique
    private LivingEntity c$getSelf() {
        return (LivingEntity) (Object) this;
    }
}
