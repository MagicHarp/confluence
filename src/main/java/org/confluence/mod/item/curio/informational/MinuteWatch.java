package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class MinuteWatch extends AbstractInfoCurio implements IWatch {
    public MinuteWatch() {
        super(ModRarity.BLUE);
    }

    public static Component wrapTime(long dayTime) {
        long hour = dayTime / 1000 + 6;
        if (hour > 23) hour -= 24;
        long minute = (long) ((dayTime % 1000) * 0.06F);
        return Component.translatable(
            "info.confluence.time",
            IWatch.format(hour),
            IWatch.format(minute)
        );
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IWatch.class);
    }
}
