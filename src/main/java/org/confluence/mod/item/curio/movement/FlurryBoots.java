package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public class FlurryBoots extends BaseSpeedBoots{
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.flurry_boots.info"),
            Component.translatable("item.confluence.flurry_boots.info2")
        };
    }
}
