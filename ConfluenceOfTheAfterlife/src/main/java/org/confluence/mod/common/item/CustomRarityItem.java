package org.confluence.mod.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.init.ModDataComponentTypes;
import org.jetbrains.annotations.NotNull;

public class CustomRarityItem extends Item {
    public CustomRarityItem(Properties properties) {
        super(properties.component(ModDataComponentTypes.MOD_RARITY.get(), ModRarity.GRAY));
    }

    public CustomRarityItem(ModRarity rarity) {
        super(new Properties().component(ModDataComponentTypes.MOD_RARITY.get(), rarity));
    }

    public CustomRarityItem(Properties properties, ModRarity rarity) {
        super(properties.component(ModDataComponentTypes.MOD_RARITY.get(), rarity));
    }

    @Override
    public @NotNull MutableComponent getName(@NotNull ItemStack pStack) {
        return Component.translatable(getDescriptionId()).withStyle(style -> style.withColor(pStack.get(ModDataComponentTypes.MOD_RARITY.get()).getColor()));
    }
}
