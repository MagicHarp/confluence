package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.confluence.mod.item.common.CellPhone;

public interface IWatch {
    static String format(long time) {
        return (time < 10 ? "0" : "") + time;
    }

    static boolean isMinuteWatch(Item item) {
        return item instanceof MinuteWatch || item instanceof GPS || item instanceof PDA || item instanceof CellPhone;
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.watch");
    byte INDEX = 0;
}
