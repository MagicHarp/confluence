package org.confluence.mod.item.curio.cosmetic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BaseCosmeticItem extends Item implements ICurioItem { // todo 如果有时装栏了就换
    public BaseCosmeticItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    public BaseCosmeticItem(Rarity rarity) {
        this(new Properties().rarity(rarity));
    }

    @Override
    public boolean hasCurioCapability(ItemStack stack) {
        return false;
    }
}
