package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public class SailfishBoots extends BaseSpeedBoots{
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sailfish_boots.info"),
            Component.translatable("item.confluence.sailfish_boots.info2"),
            Component.translatable("item.confluence.sailfish_boots.info3"),
            Component.translatable("item.confluence.sailfish_boots.info4")
        };
    }
}
