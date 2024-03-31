package org.confluence.mod.item.curio.combat;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.ILivingEntity;
import top.theillusivec4.curios.api.SlotContext;

public class CrossNecklace extends BaseCurioItem {
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ((ILivingEntity) slotContext.entity()).c$setInvulnerableTime(40);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ((ILivingEntity) slotContext.entity()).c$setInvulnerableTime(20);
    }
}
