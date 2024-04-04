package org.confluence.mod.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Unique
    private ContainerListener c$containerListener;

    @Unique
    private ContainerListener c$getOrCreate() {
        if (c$containerListener == null) {
            ServerPlayer self = (ServerPlayer) (Object) this;
            this.c$containerListener = new ContainerListener() {
                public void slotChanged(@NotNull AbstractContainerMenu menu, int index, @NotNull ItemStack itemStack) {
                    Slot slot = menu.getSlot(index);
                    Inventory inventory = self.getInventory();
                    if (!(slot instanceof ResultSlot) && slot.container == inventory) {
                        CriteriaTriggers.INVENTORY_CHANGED.trigger(self, inventory, itemStack);
                        InfoCurioCheckPacketS2C.send(self, inventory.items);
                    }
                }

                public void dataChanged(@NotNull AbstractContainerMenu menu, int idk, int idk2) {
                }
            };
        }
        return c$containerListener;
    }

    @ModifyArg(method = "initMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;addSlotListener(Lnet/minecraft/world/inventory/ContainerListener;)V"))
    private ContainerListener change(ContainerListener listener) {
        return c$getOrCreate();
    }
}
