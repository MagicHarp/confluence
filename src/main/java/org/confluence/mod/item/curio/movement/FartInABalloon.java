package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FartInABalloon extends FartInAJar implements IJumpBoost {
    public FartInABalloon() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public double getBoost() {
        return 1.33;
    }

    @Override
    public float getJumpSpeed() {
        return 2.1F;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.fart_in_a_balloon.info"),
            Component.translatable("item.confluence.fart_in_a_balloon.info2"),
            Component.translatable("item.confluence.fart_in_a_balloon.info3")
        };
    }
}
