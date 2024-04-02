package org.confluence.mod.item.curio.informational;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class HalfHourWatch extends BaseCurioItem implements IWatch {
    @Override
    public String wrapTime(long dayTime) {
        long hour = dayTime / 1000 + 6;
        if (hour > 23) hour -= 24;
        boolean half = dayTime % 1000 > 499;
        return "[" + IWatch.format(hour) + ":" + (half ? "30" : "00") + "]";
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IWatch.class);
    }
}
