package org.confluence.mod.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.capability.curio.AbilityProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void setSecondsOnFire(int p_20255_);

    @Shadow
    public abstract boolean hurt(DamageSource p_19946_, float p_19947_);

    @Shadow
    public abstract DamageSources damageSources();

    @Shadow
    public abstract void playSound(SoundEvent p_19938_, float p_19939_, float p_19940_);

    @Shadow
    @Final
    protected RandomSource random;

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    private void isFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (((Entity) (Object) this) instanceof Player player) {
            player.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
                if (playerAbility.isFireImmune()) {
                    cir.setReturnValue(true);
                }
            });
        }
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInLava()Z", ordinal = 1))
    private boolean resetLavaImmune(Entity instance) {
        AtomicBoolean inLava = new AtomicBoolean(instance.isInLava());
        if (((Entity) (Object) this) instanceof Player player) {
            player.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
                if (inLava.get()) {
                    if (playerAbility.decreaseLavaImmuneTicks()) {
                        inLava.set(false);
                    }
                } else {
                    playerAbility.increaseLavaImmuneTicks();
                }
            });
        }
        return inLava.get();
    }

    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    private void currentLavaHurt(CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof Player player) {
            player.getCapability(AbilityProvider.ABILITY_CAPABILITY).ifPresent(playerAbility -> {
                boolean reduce = playerAbility.isLavaHurtReduce();
                setSecondsOnFire(reduce ? 7 : 15);
                if (hurt(damageSources().lava(), reduce ? 2.0F : 4.0F)) {
                    playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + random.nextFloat() * 0.4F);
                }
                ci.cancel();
            });
        }
    }
}
