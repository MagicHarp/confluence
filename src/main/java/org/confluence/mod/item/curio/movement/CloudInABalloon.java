package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CloudInABalloon extends CloudInABottle implements IJumpBoost {
    public static final double SPEED = 1.1;

    public CloudInABalloon(Rarity rarity) {
        super(rarity);
    }

    public CloudInABalloon() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getBoost() {
        return 1.33;
    }

    @Override
    public double getJumpSpeed() {
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }
}
