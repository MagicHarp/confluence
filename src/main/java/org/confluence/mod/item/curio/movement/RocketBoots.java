package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RocketBoots extends BaseCurioItem implements IMayFly {
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMayFly.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.rocket_boots.info"),
                Component.translatable("item.confluence.rocket_boots.info2"),
                Component.translatable("item.confluence.rocket_boots.info3"),
                Component.translatable("item.confluence.rocket_boots.info4"),
                Component.translatable("item.confluence.rocket_boots.info5")
        };
    }
}
