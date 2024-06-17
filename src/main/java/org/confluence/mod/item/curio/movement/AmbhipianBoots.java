package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmbhipianBoots extends BaseSpeedBoots implements IJumpBoost, IFallResistance {
    public AmbhipianBoots(){
        super(ModRarity.PINK);
    }

    @Override
    public double getBoost() {
        return ModConfigs.AMBHIPIAN_BOOTS_JUMP_BOOST.get();
    }

    @Override
    public int getFallResistance() {
        return ModConfigs.AMBHIPIAN_BOOTS_FALL_RESISTANCE.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(IJumpBoost.TOOLTIP);
        list.add(IFallResistance.TOOLTIP);
    }
}
