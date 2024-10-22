package org.confluence.mod.common.item.sword;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.init.ModDataComponentTypes;

public class ShortSwordItem extends SwordItem {
    public ShortSwordItem(Tier tier, ModRarity rarity,int rawDamage, float rawSpeed) {
        super(tier, new Item.Properties()
                //.fireResistant()
                //.component(DataComponents.UNBREAKABLE,new Unbreakable(true))
                .component(DataComponents.ATTRIBUTE_MODIFIERS,createAttributes(tier,(rawDamage - tier.getAttackDamageBonus() - 1),rawSpeed-4))
                .component(ModDataComponentTypes.MOD_RARITY, rarity)
        );
    }


/*
    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        // deny sweep
        return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }
    */
}
