package org.confluence.mod.item.curio.informational;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import top.theillusivec4.curios.api.SlotContext;

public abstract class AbstractInfoCurio extends BaseCurioItem {
    public AbstractInfoCurio(Rarity rarity) {
        super(rarity);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            InfoCurioCheckPacketS2C.send(serverPlayer, serverPlayer.getInventory());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            InfoCurioCheckPacketS2C.send(serverPlayer, serverPlayer.getInventory());
        }
    }
}
