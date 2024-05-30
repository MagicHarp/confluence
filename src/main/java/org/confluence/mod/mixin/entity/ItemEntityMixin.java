package org.confluence.mod.mixin.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.fluid.ShimmerTransmutationEvent;
import org.confluence.mod.util.IItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements IItemEntity {
    @Unique
    private int c$coolDown = 0;
    @Unique
    private int c$transforming = 0;

    @Override
    public void c$setCoolDown(int ticks) {
        this.c$coolDown = ticks;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void endTick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.level().isClientSide || self.isRemoved()) return;
        if (c$coolDown < 0) this.c$coolDown = 0;
        FluidType fluidType = self.getEyeInFluidType();
        if (fluidType == ForgeMod.EMPTY_TYPE.get()) {
            if (c$coolDown > 0) this.c$coolDown--;
        } else if (c$coolDown == 0 && fluidType == ModFluids.SHIMMER.fluidType().get()) {
            ShimmerTransmutationEvent.Pre pre = new ShimmerTransmutationEvent.Pre(self);
            if (MinecraftForge.EVENT_BUS.post(pre)) {
                self.getItem().shrink(pre.getShrink());
                c$setup(self, pre.getCoolDown());
                return;
            }
            if (c$transforming < pre.getTransformTime()) {
                this.c$transforming++;
                self.addDeltaMovement(new Vec3(0.0, -5.0E-4F, 0.0));
            } else {
                ShimmerTransmutationEvent.Post post = new ShimmerTransmutationEvent.Post(self);
                self.getItem().shrink(post.getShrink());
                List<ItemStack> targets = post.getTargets();
                if (targets == null) {
                    c$setup(self, post.getCoolDown());
                    return;
                }
                for (ItemStack target : targets) {
                    ItemEntity itemEntity = new ItemEntity(self.level(), self.getX(), self.getY(), self.getZ(), target);
                    c$setup(itemEntity, post.getCoolDown());
                    self.level().addFreshEntity(itemEntity);
                }
            }
        }
    }

    @Unique
    private static void c$setup(ItemEntity entity, int coolDown) {
        entity.setNoGravity(true);
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x, 0.1, motion.z);
        ((IItemEntity) entity).c$setCoolDown(coolDown);
    }
}
