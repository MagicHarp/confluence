package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class REK3000 extends AbstractInfoCurio implements ILifeFormAnalyzer, IRadar, ITallyCounter, CustomName {
    public REK3000() {
        super(ModRarity.ORANGE);
    }

    @Override
    public String getName() {
        return "R.E.K.3000";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(ILifeFormAnalyzer.TOOLTIP);
        list.add(IRadar.TOOLTIP);
        list.add(ITallyCounter.TOOLTIP);
    }
}
