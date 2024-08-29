package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public class CopperWatch extends HourWatch {
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.copper_watch.info")
        };
    }
}
