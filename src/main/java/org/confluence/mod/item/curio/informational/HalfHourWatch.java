package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class HalfHourWatch extends AbstractInfoCurio implements IWatch {
    public HalfHourWatch() {
        super(ModRarity.WHITE);
    }

    public static Component wrapTime(long dayTime) {
        long hour = dayTime / 1000 + 6;
        if (hour > 23) hour -= 24;
        String half = dayTime % 1000 > 499 ? "30" : "00";
        return Component.translatable(
            "info.confluence.time",
            IWatch.format(hour),
            half
        );
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IWatch.class);
    }
}