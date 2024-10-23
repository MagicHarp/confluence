package org.confluence.mod.mixin.item;

import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
//    @WrapWithCondition(method = "performShooting", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;set(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
//    private <T> boolean canClear(ItemStack instance, DataComponentType<? super T> p_331064_, T p_330775_, @Local(argsOnly = true, ordinal = 0) LivingEntity pShooter) {
//        if (pShooter.level().isClientSide) return true;
//        return MagicQuiver.shouldConsume(pShooter);
//    }
}
