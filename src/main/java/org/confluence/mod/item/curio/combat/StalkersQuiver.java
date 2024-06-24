package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StalkersQuiver extends MagicQuiver implements IAggroAttach, CustomName {
    public StalkersQuiver() {
        super(ModRarity.PINK);
    }

    @Override
    public float getProjectileBonus() {
        return ModConfigs.STALKER_QUIVER_PROJECTILE_BONUS.get().floatValue();
    }

    @Override
    public int getAggro() {
        return ModConfigs.STALKERS_QUIVER_AGGRO.get();
    }

    @Override
    public String getGenName() {
        return "Stalker's Quiver";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.putrid_scent.tooltip"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.stalkers_quiver.info"),
            Component.translatable("item.confluence.stalkers_quiver.info2"),
            Component.translatable("item.confluence.stalkers_quiver.info3")
        };
    }
}
