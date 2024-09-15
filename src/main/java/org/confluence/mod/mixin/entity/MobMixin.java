package org.confluence.mod.mixin.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Zombie;
import org.confluence.mod.mixinauxiliary.SelfGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin implements SelfGetter<Mob> {
    @Inject(method = "getControllingPassenger", at = @At("RETURN"), cancellable = true)
    private void disableZombiesSlimePassenger(CallbackInfoReturnable<Entity> cir) { // 防止卡AI
        if (self() instanceof Zombie && cir.getReturnValue() instanceof Slime) cir.setReturnValue(null);
    }
}
