package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogLeg extends BaseCurioItem implements IJumpBoost, IFallResistance {
    @Override
    public double getBoost() {
        return ModConfigs.FROG_LEG_JUMP_BOOST.get();
    }

    @Override
    public int getFallResistance() {
        return ModConfigs.FROG_LEG_FALL_RESISTANCE.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IJumpBoost.TOOLTIP);
        list.add(IFallResistance.TOOLTIP);
    }
}
