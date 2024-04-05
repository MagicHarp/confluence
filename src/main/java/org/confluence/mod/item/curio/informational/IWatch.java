package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public interface IWatch {
    static String format(long time) {
        return (time < 10 ? "0" : "") + time;
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.watch");
}
