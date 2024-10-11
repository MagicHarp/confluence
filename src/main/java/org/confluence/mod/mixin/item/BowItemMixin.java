package org.confluence.mod.mixin.item;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.bow.ShortBowItem;
import org.confluence.mod.item.curio.combat.MagicQuiver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public abstract class BowItemMixin {
    @Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void modifyArrow(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft, CallbackInfo ci, Player player, boolean flag, ItemStack itemstack, int i, float f, boolean flag1, ArrowItem arrowitem, AbstractArrow abstractarrow, int j, int k) {
        ShortBowItem.applyToArrow(pStack, abstractarrow);
    }

    @WrapWithCondition(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private boolean canShrink(ItemStack instance, int pDecrement, @Local Player player) {
        if (player.level().isClientSide) return true;
        return MagicQuiver.shouldConsume(player);
    }

    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BowItem;getPowerForTime(I)F"))
    private float shortBowPower(int pCharge, Operation<Float> original, @Local(argsOnly = true) ItemStack itemStack) {
        if (itemStack.getItem() instanceof ShortBowItem shortBow) {
            return shortBow.getShortPowerForTime(pCharge);
        }
        return original.call(pCharge);
    }

    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V"))
    private void shortBowSoot(AbstractArrow instance, Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy, Operation<Void> original, @Local(argsOnly = true) ItemStack itemStack) {
        if (itemStack.getItem() instanceof ShortBowItem shortBow) {
            original.call(instance, pShooter, pX, pY, pZ, pVelocity * shortBow.getVelocityMultiplier() / 3.0F, pInaccuracy);
        } else {
            original.call(instance, pShooter, pX, pY, pZ, pVelocity, pInaccuracy);
        }
    }
}
