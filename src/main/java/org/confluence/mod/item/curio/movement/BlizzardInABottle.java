package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlizzardInABottle extends BaseCurioItem implements IOneTimeJump {
    public static final int TICKS = 16;
    public static final double SPEED = 0.4;

    public BlizzardInABottle(Rarity rarity) {
        super(rarity);
    }

    public BlizzardInABottle() {
        super();
    }

    @Override
    public int getJumpTicks() {
        return TICKS;
    }

    @Override
    public double getJumpSpeed() {
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IOneTimeJump.TOOLTIP);
    }
}
