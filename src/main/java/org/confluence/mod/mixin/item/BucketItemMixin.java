package org.confluence.mod.mixin.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.misc.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @Inject(method = "getEmptySuccessItem", at = @At("RETURN"), cancellable = true)
    private static void bottomless(ItemStack pBucketStack, Player pPlayer, CallbackInfoReturnable<ItemStack> cir) {
        if (pBucketStack.is(ModTags.Items.BOTTOMLESS)) {
            cir.setReturnValue(pBucketStack);
        }
    }
}
