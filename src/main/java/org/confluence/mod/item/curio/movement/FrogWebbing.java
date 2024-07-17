package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrogWebbing extends BaseCurioItem implements IWallClimb, IJumpBoost, IFallResistance {
    public FrogWebbing() {
        super(ModRarity.PINK);
    }

    @Override
    public boolean fullyWallClimb() {
        return true;
    }

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
        list.add(IWallClimb.WALL_CLIMB);
        list.add(IJumpBoost.TOOLTIP);
        list.add(IFallResistance.TOOLTIP);
    }
}
