package org.confluence.mod.item.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.ModTiers;
import org.jetbrains.annotations.NotNull;

public class CrowbarItem extends SwordItem implements ModRarity.Master, CustomModel {
    public CrowbarItem() {
        super(ModTiers.TITANIUM, 16, -1.0F, new Properties().fireResistant().rarity(ModRarity.MASTER));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return withColor(getDescriptionId());
    }
}
