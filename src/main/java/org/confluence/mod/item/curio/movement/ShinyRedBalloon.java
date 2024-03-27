package org.confluence.mod.item.curio.movement;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class ShinyRedBalloon extends BaseCurioItem implements IJumpBoost {
    @Override
    public double getBoost() {
        return 0.33;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        freshMaxBoost(slotContext.entity());
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        freshMaxBoost(slotContext.entity());
    }
}
