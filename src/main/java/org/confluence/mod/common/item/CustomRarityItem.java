package org.confluence.mod.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.common.component.RarityComponent;
import org.confluence.mod.common.init.ModDataComponentTypes;
import org.confluence.mod.common.init.ModRarities;
import org.jetbrains.annotations.NotNull;

public class CustomRarityItem extends Item {
    public CustomRarityItem(Properties properties) {
        super(properties.component(ModDataComponentTypes.RARITY.get(), ModRarities.GRAY));
    }

    public CustomRarityItem(Properties properties, RarityComponent rarity) {
        super(properties.component(ModDataComponentTypes.RARITY.get(), rarity));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return Component.translatable(getDescriptionId()).withStyle(style -> style.withColor(pStack.get(ModDataComponentTypes.RARITY.get()).color()));
    }
}
