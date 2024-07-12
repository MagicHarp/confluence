package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModConfigs;
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
        return ModConfigs.SNIPER_SCOPE_CRITICAL_CHANCE.get();
    }

    @Override
    public float getProjectileBonus() {
        return ModConfigs.SNIPER_SCOPE_PROJECTILE_BONUS.get().floatValue();
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
