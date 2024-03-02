package org.confluence.mod.item.sword;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class ShortSwordItem extends SwordItem {
    public ShortSwordItem(Tier tier, int rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public ShortSwordItem(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, (int) (rawDamage - tier.getAttackDamageBonus() - 1), rawSpeed - 4, properties);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
        // deny sweep
        return toolAction == ToolActions.SWORD_DIG;
    }
}
