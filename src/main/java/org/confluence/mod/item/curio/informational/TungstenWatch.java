package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public class TungstenWatch extends HalfHourWatch {
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.tungsten_watch.info"),
            Component.translatable("item.confluence.tungsten_watch.info2"),
            Component.translatable("item.confluence.tungsten_watch.info3")
        };
    }
}
