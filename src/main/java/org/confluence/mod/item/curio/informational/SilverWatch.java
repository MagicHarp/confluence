package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public class SilverWatch extends HalfHourWatch{
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.silver_watch.info"),
            Component.translatable("item.confluence.silver_watch.info2"),
            Component.translatable("item.confluence.silver_watch.info3")
        };
    }
}
