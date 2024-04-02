package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import org.confluence.mod.item.ModRarity;

public class ExpertTestItem extends Item implements ModRarity.Expert {
    public ExpertTestItem() {
        super(new Properties());
    }

    @Override
    public MutableComponent getComponent() {
        return Component.translatable(getDescriptionId());
    }
}
