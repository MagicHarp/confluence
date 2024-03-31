package org.confluence.mod.item.curio.combat;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class ObsidianSkull extends BaseCurioItem implements IFireImmune {
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        freshFireImmune(slotContext.entity());
    }
}
