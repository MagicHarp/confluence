package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlizzardInABalloon extends BlizzardInABottle implements IJumpBoost {
    public BlizzardInABalloon() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getJumpTicks() {
        return ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_TICKS.get();
    }

    @Override
    public double getJumpSpeed() {
        return ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_SPEED.get();
    }

    @Override
    public double getBoost() {
        return ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_BOOST.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IOneTimeJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.blizzard_in_a_balloon.info"),
            Component.translatable("item.confluence.blizzard_in_a_balloon.info2"),
            Component.translatable("item.confluence.blizzard_in_a_balloon.info3")
        };
    }
}
