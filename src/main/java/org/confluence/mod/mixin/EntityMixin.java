package org.confluence.mod.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.capability.ability.PlayerAbilityProvider;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.IHurtEvasion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInLava()Z"))
    private boolean resetLavaImmune(Entity instance) {
        AtomicBoolean inLava = new AtomicBoolean(instance.isInLava());
        if (((Entity) (Object) this) instanceof Player player) {
            if (player.canStandOnFluid(Fluids.LAVA.defaultFluidState())) return false;
            player.getCapability(PlayerAbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
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
        if (((Entity) (Object) this) instanceof Player player) {
            if (IHurtEvasion.apply(player) || IFireImmune.apply(player, damageSource)) {
                cir.setReturnValue(true);
            }
        }
    }
}
