package org.confluence.mod.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.combat.MagicQuiver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Inject(method = "shootProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void modifyProjectile(Level pLevel, LivingEntity pShooter, InteractionHand pHand, ItemStack pCrossbowStack, ItemStack pAmmoStack, float pSoundPitch, boolean pIsCreativeMode, float pVelocity, float pInaccuracy, float pProjectileAngle, CallbackInfo ci, boolean flag, Projectile projectile) {
        if (projectile instanceof AbstractArrow abstractArrow) {
            MagicQuiver.applyToArrow(pShooter, abstractArrow);
        }
        // todo 其它射弹
    }

    @WrapWithCondition(method = "onCrossbowShot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;clearChargedProjectiles(Lnet/minecraft/world/item/ItemStack;)V"))
    private static boolean canClear(ItemStack pCrossbowStack, @Local LivingEntity pShooter) {
        if (pShooter.level().isClientSide) return true;
        boolean consume = MagicQuiver.shouldConsume(pShooter);
        pCrossbowStack.getOrCreateTag().putBoolean("canceled", !consume);
        return consume;
    }

    @WrapWithCondition(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;setCharged(Lnet/minecraft/world/item/ItemStack;Z)V"))
    private boolean whetherCanceled(ItemStack pCrossbowStack, boolean pIsCharged) {
        return pCrossbowStack.getTag() == null || !pCrossbowStack.getTag().getBoolean("canceled");
    }
}
