package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfMyths extends PhilosophersStone {
    public CharmOfMyths() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ModEffects.heal(slotContext.entity(), 1);
    }
}
