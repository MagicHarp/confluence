package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.IAggroAttach;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArcaneFlower extends BaseCurioItem implements IManaReduce, IAggroAttach {
    public ArcaneFlower() {
        super(ModRarity.PINK);
    }

    @Override
    public double getManaReduce() {
        return 0.08;
    }

    @Override
    public int getAggro() {
        return -400;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.arcane_flower.tooltip1"));
        list.add(Component.translatable("item.confluence.arcane_flower.tooltip2"));
        list.add(Component.translatable("item.confluence.arcane_flower.tooltip3"));
    }
}
