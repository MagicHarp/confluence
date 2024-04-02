package org.confluence.mod.item.curio.informational;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class MinuteWatch extends BaseCurioItem implements IWatch {
    @Override
    public String wrapTime(long dayTime) {
        long hour = dayTime / 1000 + 6;
        if (hour > 23) hour -= 24;
        long minute = (long) ((dayTime % 1000) * 0.06F);
        return "[" + IWatch.format(hour) + ":" + IWatch.format(minute) + "]";
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IWatch.class);
    }
}
