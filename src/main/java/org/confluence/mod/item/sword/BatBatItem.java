package org.confluence.mod.item.sword;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.confluence.mod.datagen.limit.Image32x;
import org.confluence.mod.datagen.limit.ReversalImage32x;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class BatBatItem extends SwordItem implements ReversalImage32x {
    public BatBatItem() {
        super(ModTiers.TITANIUM, 12, -3.7F, new Properties().rarity(ModRarity.ORANGE));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, LivingEntity pAttacker) {
        pAttacker.heal(1.0F);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
