package org.confluence.mod.item.curio.datadriven;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ErrorDumpedCurio extends BaseCurioItem {
    private static final Component NAME = Component.literal("Error Dumped Curio");

    public ErrorDumpedCurio() {
        super(ModRarity.RED);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        return NAME;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {}
}
