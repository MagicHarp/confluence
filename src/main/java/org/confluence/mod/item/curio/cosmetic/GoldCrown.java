package org.confluence.mod.item.curio.cosmetic;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

public class GoldCrown extends BaseCosmeticItem {
    public GoldCrown() {
        super(ModRarity.WHITE);
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
