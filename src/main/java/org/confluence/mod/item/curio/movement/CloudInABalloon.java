package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
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
        return ModConfigs.CLOUD_IN_A_BALLOON_JUMP_BOOST.get();
    }

    @Override
    public double getJumpSpeed() {
        return ModConfigs.CLOUD_IN_A_BALLOON_JUMP_SPEED.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
        list.add(IJumpBoost.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.cloud_in_a_balloon.info"),
            Component.translatable("item.confluence.cloud_in_a_balloon.info2"),
            Component.translatable("item.confluence.cloud_in_a_balloon.info3"),
            Component.translatable("item.confluence.cloud_in_a_balloon.info4")
        };
    }
}
