package org.confluence.mod.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.combat.MagicQuiver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
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
