package org.confluence.mod.mixin.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.fluid.ShimmerItemTransmutationEvent;
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
    private static final Vec3 ANTI_GRAVITY = new Vec3(0.0, -5.0E-4F, 0.0);
    @Unique
    private int confluence$item_coolDown = 0;
    @Unique
    private int confluence$item_transforming = 0;

    @Override
    public void confluence$item_setCoolDown(int ticks) {
        this.confluence$item_coolDown = ticks;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void endTick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.level().isClientSide || self.isRemoved()) return;
        if (confluence$item_coolDown < 0) this.confluence$item_coolDown = 0;
        boolean isInShimmer = self.getEyeInFluidType() == ModFluids.SHIMMER.fluidType().get();
        if (confluence$item_coolDown == 0 && isInShimmer) {
            ShimmerItemTransmutationEvent.Pre pre = new ShimmerItemTransmutationEvent.Pre(self);
            if (MinecraftForge.EVENT_BUS.post(pre)) {
                self.getItem().shrink(pre.getShrink());
                confluence$setup(self, pre.getCoolDown(), pre.getSpeedY());
            } else if (confluence$item_transforming < pre.getTransformTime()) {
                this.confluence$item_transforming++;
                self.addDeltaMovement(ANTI_GRAVITY);
            } else {
                ShimmerItemTransmutationEvent.Post post = new ShimmerItemTransmutationEvent.Post(self);
                MinecraftForge.EVENT_BUS.post(post);
                List<ItemStack> targets = post.getTargets();
                self.getItem().shrink(post.getShrink());
                if (targets == null) {
                    confluence$setup(self, post.getCoolDown(), post.getSpeedY());
                } else {
                    for (ItemStack target : targets) {
                        if (PrefixProvider.canInit(target)) PrefixProvider.unknown(target);
                        ItemEntity itemEntity = new ItemEntity(self.level(), self.getX(), self.getY(), self.getZ(), target);
                        confluence$setup(itemEntity, post.getCoolDown(), post.getSpeedY());
                        self.level().addFreshEntity(itemEntity);
                    }
                }
            }
        } else if (confluence$item_coolDown > 0) {
            this.confluence$item_coolDown--;
        }
    }

    @Unique
    private static void confluence$setup(ItemEntity entity, int coolDown, double y) {
        entity.setNoGravity(true);
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x, y, motion.z);
        ((IItemEntity) entity).confluence$item_setCoolDown(coolDown);
        entity.setGlowingTag(true);
    }
}
