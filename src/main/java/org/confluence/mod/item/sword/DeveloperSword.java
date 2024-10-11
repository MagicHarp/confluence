package org.confluence.mod.item.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class DeveloperSword extends IceBladeSwordItem implements ModRarity.Master {
    public DeveloperSword() {
        super(ModTiers.TITANIUM, 100, 100f, new Item.Properties().rarity(ModRarity.MASTER));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return withColor(getDescriptionId());
    }
}
