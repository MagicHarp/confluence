package org.confluence.mod.common.item.sword;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.confluence.mod.terra_curio.common.component.ModRarity;
import org.confluence.mod.terra_curio.common.init.ModDataComponentTypes;
import org.jetbrains.annotations.NotNull;

public class ShortSwordItem extends SwordItem {
    public ShortSwordItem(Tier tier, ModRarity rarity, int rawDamage, float rawSpeed) {
        super(tier, new Item.Properties()
                //.fireResistant()
                //.component(DataComponents.UNBREAKABLE,new Unbreakable(true))
                .component(DataComponents.ATTRIBUTE_MODIFIERS,createAttributes(tier,(rawDamage - tier.getAttackDamageBonus() - 1),rawSpeed-4))
                .component(ModDataComponentTypes.MOD_RARITY, rarity)
        );
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        // deny sweep
        return itemAbility == ItemAbilities.SWORD_DIG;
    }
}
