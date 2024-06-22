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

public class SpectreBoots extends BaseSpeedBoots implements IMayFly {
    public SpectreBoots() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getFlyTicks() {
        return ModConfigs.SPECTRE_BOOTS_FLY_TICKS.get();
    }

    @Override
    public double getFlySpeed() {
        return ModConfigs.SPECTRE_BOOTS_FLY_SPEED.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMayFly.TOOLTIP);
        list.add(BaseSpeedBoots.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.spectre_boots.info"),
            Component.translatable("item.confluence.spectre_boots.info2")
        };
    }
}
