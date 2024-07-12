package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModConfigs;
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
        return ModConfigs.RECON_SCOPE_CRITICAL_CHANCE.get();
    }

    @Override
    public float getProjectileBonus() {
        return ModConfigs.RECON_SCOPE_PROJECTILE_BONUS.get().floatValue();
    }

    @Override
    public int getAggro() {
        return ModConfigs.RECON_SCOPE_AGGRO.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.putrid_scent.tooltip"));
        list.add(Component.translatable("item.confluence.recon_scope.tooltip"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.recon_scope.info"),
            Component.translatable("item.confluence.recon_scope.info2")
        };
    }
}
