package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.combat.IStarCloak;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaCloak extends ManaFlower implements IStarCloak {
    public ManaCloak() {
        super(ModRarity.PINK);
    }

    @Override
    public boolean hasMana() {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(IStarCloak.TOOLTIP);
        list.add(Component.translatable("item.confluence.mana_cloak.tooltip"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.mana_cloak.info"),
            Component.translatable("item.confluence.mana_cloak.info2"),
            Component.translatable("item.confluence.mana_cloak.info3"),
            Component.translatable("item.confluence.mana_cloak.info4"),
            Component.translatable("item.confluence.mana_cloak.info5")
        };
    }
}
