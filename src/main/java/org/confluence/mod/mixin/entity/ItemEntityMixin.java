package org.confluence.mod.mixin.entity;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.fluid.ShimmerItemTransmutationEvent;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.mixinauxiliary.IEntity;
import org.confluence.mod.mixinauxiliary.IItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements IItemEntity {
    @Shadow
    public abstract ItemStack getItem();

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

        if (confluence$item_coolDown == 0 && ((IEntity) self).confluence$isInShimmer()) {
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
                        self.level().playSound(null, self.getX(), self.getY(), self.getZ(), ModSoundEvents.SHIMMER_EVOLUTION.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
                    }
                }
            }
        } else if (confluence$item_coolDown > 0) {
            this.confluence$item_coolDown--;
        }
    }

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    public void highRarityForbiddenBurn(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        Item item = getItem().getItem();
        Rarity rarity = item.getRarity(getItem());
        if (rarity != ModRarity.WHITE &&
            rarity != ModRarity.GRAY &&
            rarity != Rarity.COMMON) {
            cir.setReturnValue(true);
        } else if (item == Blocks.OBSIDIAN.asItem() || item == Blocks.CRYING_OBSIDIAN.asItem() ||
            item == ModItems.FLAMEFLOWERS.get() || item == ModItems.FLAMEFLOWERS_SEED.get()) {
            cir.setReturnValue(true);
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
