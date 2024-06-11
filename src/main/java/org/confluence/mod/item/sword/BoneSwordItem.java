package org.confluence.mod.item.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.datagen.limit.Image32x;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class BoneSwordItem extends SwordItem implements Image32x {
    public BoneSwordItem() {
        super(ModTiers.TITANIUM, 5, -0.2F, new Properties().rarity(ModRarity.ORANGE));
    }


}
