package org.confluence.mod.mixin.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.fluid.ShimmerTransformEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Unique
    private int c$coolDown = 0;
    @Unique
    private int c$transforming = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void endTick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.level().isClientSide || self.isRemoved()) return;
        if (c$coolDown < 0) this.c$coolDown = 0;
        FluidType fluidType = self.getEyeInFluidType();
        if (fluidType == ForgeMod.EMPTY_TYPE.get()) {
            if (c$coolDown > 0) this.c$coolDown--;
        } else if (c$coolDown == 0 && fluidType == ModFluids.SHIMMER.fluidType().get()) {
            ShimmerTransformEvent event = new ShimmerTransformEvent(self);
            if (MinecraftForge.EVENT_BUS.post(event)) return;
            if (c$transforming < event.getTransformTime()) {
                this.c$transforming++;
            } else {
                ItemStack target = event.getTarget();
                if (target != null) self.setItem(target);
                self.setNoGravity(true);
                self.setDeltaMovement(0.0, 0.1, 0.0);
                this.c$coolDown = event.getCoolDown();
            }
        }
    }
}
