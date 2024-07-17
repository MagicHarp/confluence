package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.IHurtEvasion;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MasterNinjaGear extends BaseCurioItem implements IWallClimb, IHurtEvasion, ITabi {
    public MasterNinjaGear() {
        super(ModRarity.YELLOW);
    }

    @Override
    public boolean fullyWallClimb() {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IWallClimb.WALL_CLIMB);
        list.add(ITabi.TOOLTIP);
        list.add(IHurtEvasion.TOOLTIP);
    }
}
