package org.confluence.mod.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.IHurtEvasion;
import org.confluence.mod.item.curio.expert.RoyalGel;
import org.confluence.mod.item.curio.expert.ShieldOfCthulhu;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.IEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {
    @Shadow
    public abstract DamageSources damageSources();

    @Unique
    private int c$cthulhuSprintingTime = 0;

    @Override
    public boolean c$isOnCthulhuSprinting() {
        return c$cthulhuSprintingTime > 0;
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInLava()Z"))
    private boolean resetLavaImmune(Entity instance) {
        AtomicBoolean inLava = new AtomicBoolean(instance.isInLava());
        if (((Entity) (Object) this) instanceof Player player) {
            if (player.canStandOnFluid(Fluids.LAVA.defaultFluidState())) return false;
            player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
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

    @Inject(method = "isInvulnerableTo", at = @At("RETURN"), cancellable = true)
    private void immune(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        if (((Entity) (Object) this) instanceof Player player) {
            if (IHurtEvasion.isInvul(player) ||
                IFallResistance.isInvul(player, damageSource) ||
                IFireImmune.isInvul(player, damageSource) ||
                RoyalGel.isInvul(player, damageSource) ||
                ShieldOfCthulhu.isInvul(player)
            ) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "setSprinting", at = @At("TAIL"))
    private void sprinting(boolean bool, CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof Player player) {
            if (bool && CuriosUtils.hasCurio(player, CurioItems.SHIELD_OD_CTHULHU.get())) {
                ShieldOfCthulhu.apply(player);
                this.c$cthulhuSprintingTime = 12;
            } else {
                this.c$cthulhuSprintingTime = 0;
            }
        }
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE))
    private void tickProfiler(CallbackInfo ci) {
        if (c$cthulhuSprintingTime > 0) this.c$cthulhuSprintingTime--;
    }

    @Inject(method = "playerTouch", at = @At("TAIL"))
    private void collidingCheck(Player player, CallbackInfo ci) {
        if (((IEntity) player).c$isOnCthulhuSprinting()) {
            Entity self = (Entity) (Object) this;
            self.addDeltaMovement(player.getDeltaMovement().scale(0.9F).with(Direction.Axis.Y, 0.2));
            self.hurt(damageSources().playerAttack(player), 7.8F);
        }
    }
}
