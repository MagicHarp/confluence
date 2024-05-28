package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class ExpertTestItem extends Item implements ModRarity.Expert {
    public ExpertTestItem() {
        super(new Properties());
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        return withColor(getDescriptionId(itemStack));
    }
}
