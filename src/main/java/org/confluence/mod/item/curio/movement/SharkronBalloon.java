package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SharkronBalloon extends TsunamiInABottle implements IJumpBoost {
    public SharkronBalloon(Rarity rarity) {
        super(rarity);
    }

    public SharkronBalloon() {
        super();
    }

    @Override
    public double getBoost() {
        return 1.33;
    }

    @Override
    public double getJumpSpeed() {
        return 1.1;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sharkron_balloon.info"),
            Component.translatable("item.confluence.sharkron_balloon.info2")
        };
    }
}
