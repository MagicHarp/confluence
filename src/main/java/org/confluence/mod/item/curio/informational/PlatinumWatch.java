package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public class PlatinumWatch extends MinuteWatch {
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.platinum_watch.info")
        };
    }
}
