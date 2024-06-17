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

public class RocketBoots extends BaseCurioItem implements IMayFly {
    @Override
    public int getFlyTicks() {
        return ModConfigs.ROCKET_BOOTS_FLY_TICKS.get();
    }

    @Override
    public double getFlySpeed() {
        return ModConfigs.ROCKET_BOOTS_FLY_SPEED.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMayFly.TOOLTIP);
    }
}
