package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogLeg extends BaseCurioItem implements IJumpBoost, IFallResistance {
    @Override
    public double getBoost() {
        return 1.6;
    }

    @Override
    public int getFallResistance() {
        return 7;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IJumpBoost.TOOLTIP);
        list.add(IFallResistance.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.frog_leg.info"),
                Component.translatable("item.confluence.frog_leg.info2"),
                Component.translatable("item.confluence.frog_leg.info3"),
                Component.translatable("item.confluence.frog_leg.info4"),
                Component.translatable("item.confluence.frog_leg.info5")
        };
    }
}
