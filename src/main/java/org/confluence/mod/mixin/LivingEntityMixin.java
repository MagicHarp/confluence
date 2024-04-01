package org.confluence.mod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.ThinIceBlock;
import org.confluence.mod.capability.curio.AbilityProvider;
import org.confluence.mod.util.CuriosUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void multiY(CallbackInfoReturnable<Float> cir) {
        c$getSelf().getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            cir.setReturnValue((float) (cir.getReturnValue() * playerAbility.getJumpBoost()));
        });
    }

    @Inject(method = "calculateFallDamage", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void fallDamage(float fallDistance, float multiply, CallbackInfoReturnable<Integer> cir) {
        c$getSelf().getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            if (playerAbility.getFallResistance() < 0) {
                cir.setReturnValue(0);
            } else {
                cir.setReturnValue(cir.getReturnValue() - playerAbility.getFallResistance());
            }
        });
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
        c$getSelf().getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
            time.set(playerAbility.getInvulnerableTime());
        });
        return time.get();
    }

    @Inject(method = "checkFallDamage", at = @At("HEAD"), cancellable = true)
    private void thinIceBlock(double motionY, boolean onGround, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity self = c$getSelf();
        if (self.fallDistance > 3.0F && blockState.is(ModBlocks.THIN_ICE_BLOCK.get()) && CuriosUtils.noSameCurio(self, ThinIceBlock.IceSafe.class)) {
            self.level().destroyBlock(blockPos, true, self);
            ci.cancel();
        }
    }

    @Unique
    private LivingEntity c$getSelf() {
        return (LivingEntity) (Object) this;
    }
}
