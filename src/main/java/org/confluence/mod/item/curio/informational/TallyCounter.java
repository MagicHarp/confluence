package org.confluence.mod.item.curio.informational;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class TallyCounter extends AbstractInfoCurio implements ITallyCounter {
    public TallyCounter() {
        super(ModRarity.BLUE);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), ITallyCounter.class);
    }
}
