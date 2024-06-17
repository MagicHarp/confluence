package org.confluence.mod.item.curio.construction;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AncientChisel extends BaseCurioItem implements IBreakSpeedBonus {
    @Override
    public float getBreakBonus() {
        return 0.25F;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.ancient_chisel.tooltip"));
        list.add(Component.translatable("item.confluence.ancient_chisel.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.ancient_chisel.info")
        };
    }
}
