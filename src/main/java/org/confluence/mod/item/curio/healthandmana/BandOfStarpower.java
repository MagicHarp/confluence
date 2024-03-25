package org.confluence.mod.item.curio.healthandmana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.PlayerUtils;
import top.theillusivec4.curios.api.SlotContext;

public class BandOfStarpower extends BaseCurioItem {
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.increaseAdditionalMana(serverPlayer, 20);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.decreaseAdditionalMana(serverPlayer, 20);
        }
    }
}
