package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.IMagicAttack;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CelestialEmblem extends BaseCurioItem implements IMagicAttack, IRangePickup.Star {
    public CelestialEmblem() {
        super(ModRarity.PINK);
    }

    @Override
    public double getMagicBonus() {
        return 0.15;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.celestial_emblem.tooltip"));
        list.add(Component.translatable("item.confluence.celestial_emblem.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.celestial_emblem.info")
        };
    }
}
