package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public class GoldWatch extends MinuteWatch{
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.gold_watch.info"),
            Component.translatable("item.confluence.gold_watch.info2"),
            Component.translatable("item.confluence.gold_watch.info3")
        };
    }
}
