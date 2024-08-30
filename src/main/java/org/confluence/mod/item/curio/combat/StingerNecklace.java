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

public class StingerNecklace extends HoneyComb implements IArmorPass {
    public StingerNecklace() {
        super(ModRarity.PINK);
    }

    @Override
    public int getPassValue() {
        return ModConfigs.STINGER_NECKLACE_ARMOR_PASS.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(getArmorPassToolTip());
        list.add(IHoneycomb.TOOLTIP);
    }
}
