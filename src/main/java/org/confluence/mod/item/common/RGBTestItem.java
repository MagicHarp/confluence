package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

public class RGBTestItem extends Item implements RainbowRarity {
    public RGBTestItem() {
        super(new Properties());
    }

    @Override
    public MutableComponent getComponent() {
        return Component.translatable(getDescriptionId());
    }
}
