package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public class HermesBoots extends BaseSpeedBoots {
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.hermes_boots.info"),
            Component.translatable("item.confluence.hermes_boots.info2"),
            Component.translatable("item.confluence.hermes_boots.info3"),
            Component.translatable("item.confluence.hermes_boots.info4"),
            Component.translatable("item.confluence.hermes_boots.info5"),
            Component.translatable("item.confluence.hermes_boots.info6"),
            Component.translatable("item.confluence.hermes_boots.info7"),
            Component.translatable("item.confluence.hermes_boots.info8"),
            Component.translatable("item.confluence.hermes_boots.info9"),
            Component.translatable("item.confluence.hermes_boots.info10")
        };
    }
}
