package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaFlower extends BaseCurioItem implements IManaReduce, IAutoGetMana {
    public ManaFlower() {
        super(ModRarity.LIGHT_RED);
    }

    public ManaFlower(Rarity rarity) {
        super(rarity);
    }

    @Override
    public double getManaReduce() {
        return 0.08;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.mana_flower.tooltip"));
        list.add(Component.translatable("item.confluence.mana_flower.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.mana_flower.info"),
                Component.translatable("item.confluence.mana_flower.info2")
        };
    }
}
