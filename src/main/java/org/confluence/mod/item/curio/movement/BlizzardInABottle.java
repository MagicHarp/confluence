package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlizzardInABottle extends BaseCurioItem implements IOneTimeJump {
    public BlizzardInABottle(Rarity rarity) {
        super(rarity);
    }

    public BlizzardInABottle() {
        super();
    }

    @Override
    public int getJumpTicks() {
        return ModConfigs.BLIZZARD_IN_A_BOTTLE_JUMP_TICKS.get();
    }

    @Override
    public double getJumpSpeed() {
        return ModConfigs.BLIZZARD_IN_A_BOTTLE_JUMP_SPEED.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IOneTimeJump.TOOLTIP);
    }
}
