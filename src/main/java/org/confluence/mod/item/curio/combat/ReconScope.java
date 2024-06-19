package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReconScope extends SniperScope implements IAggroAttach {
    public ReconScope() {
        super(ModRarity.PINK);
    }

    @Override
    public double getChance() {
        return 0.1;
    }

    @Override
    public float getProjectileBonus() {
        return 0.1F;
    }

    @Override
    public int getAggro() {
        return -400;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.putrid_scent.tooltip"));
        list.add(Component.translatable("item.confluence.recon_scope.tooltip"));
    }
}
