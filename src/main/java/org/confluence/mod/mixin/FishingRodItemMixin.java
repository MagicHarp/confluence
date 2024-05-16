package org.confluence.mod.mixin;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.confluence.mod.item.curio.fishing.ILavaproofFishingHook;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.IFishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {
    @Inject(
        method = "use",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"),
        locals = LocalCapture.CAPTURE_FAILSOFT,
        cancellable = true
    )
    private void replaceHook(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, ItemStack itemstack, int k, int j) {
        if (CuriosUtils.hasCurio(pPlayer, ILavaproofFishingHook.class)) {
            FishingHook fishingHook = new FishingHook(pPlayer, pLevel, j, k);
            ((IFishingHook) fishingHook).c$setIsLavaHook();
            pLevel.addFreshEntity(fishingHook);
            pPlayer.awardStat(Stats.ITEM_USED.get((FishingRodItem) (Object) this));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START);
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemstack, false));
        }
    }
}
