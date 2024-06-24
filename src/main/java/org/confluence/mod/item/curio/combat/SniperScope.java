package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SniperScope extends RifleScope implements IProjectileAttack, ICriticalHit {
    public SniperScope() {
        super(ModRarity.LIME);
    }

    public SniperScope(Rarity rarity) {
        super(rarity);
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
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.sniper_scope.tooltip"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sniper_scope.info"),
            Component.translatable("item.confluence.sniper_scope.info2")
        };
    }
}
