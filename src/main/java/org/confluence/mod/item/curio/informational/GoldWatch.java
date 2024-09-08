package org.confluence.mod.item.curio.informational;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class GoldWatch extends MinuteWatch {

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
