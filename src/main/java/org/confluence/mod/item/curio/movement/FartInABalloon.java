package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FartInABalloon extends FartInABottle implements IJumpBoost {
    public FartInABalloon() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getBoost() {
        return 1.33;
    }

    @Override
    public double getJumpSpeed() {
        return 2.1;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }
}
