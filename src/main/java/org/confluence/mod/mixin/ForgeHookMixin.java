package org.confluence.mod.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.mixin.accessor.ItemEntityAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.SetItemEntityPickupDelayPacketS2C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooks.class, remap = false)
public abstract class ForgeHookMixin {
    @Inject(method = "onPlayerTossEvent", at = @At(value = "RETURN", ordinal = 2), remap = false)
    private static void setClientItemEntityPickupDelay(Player player, ItemStack item, boolean includeName, CallbackInfoReturnable<ItemEntity> cir) {
        if (player.level().isClientSide) return;
        ItemEntity returnValue = cir.getReturnValue();
        NetworkHandler.CHANNEL.send(
            PacketDistributor.ALL.noArg(),
            new SetItemEntityPickupDelayPacketS2C(returnValue.getId(), ((ItemEntityAccessor) returnValue).getPickupDelay())
        );
    }
}
