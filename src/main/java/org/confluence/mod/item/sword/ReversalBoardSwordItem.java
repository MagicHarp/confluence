package org.confluence.mod.item.sword;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.datagen.limit.ReversalImage16x;
import org.confluence.mod.datagen.limit.ReversalImage24x;
import org.jetbrains.annotations.NotNull;

public class ReversalBoardSwordItem extends SwordItem implements ReversalImage16x {
    public ReversalBoardSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
