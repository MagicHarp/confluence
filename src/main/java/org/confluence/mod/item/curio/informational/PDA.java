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

public class PDA extends AbstractInfoCurio implements ICompass, IDepthMeter, IDPSMeter, IFishermansPocketGuide,
        ILifeFormAnalyzer, IMetalDetector, IRadar, ISextant, IStopwatch, ITallyCounter, IWatch, IWeatherRadio, CustomName {
    public PDA() {
        super(ModRarity.PINK);
    }

    @Override
    public String getGenName() {
        return "PDA";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(ICompass.TOOLTIP);
        list.add(IDepthMeter.TOOLTIP);
        list.add(IDPSMeter.TOOLTIP);
        list.add(IFishermansPocketGuide.TOOLTIP);
        list.add(ILifeFormAnalyzer.TOOLTIP);
        list.add(IMetalDetector.TOOLTIP);
        list.add(IRadar.TOOLTIP);
        list.add(ISextant.TOOLTIP);
        list.add(IStopwatch.TOOLTIP);
        list.add(ITallyCounter.TOOLTIP);
        list.add(IWatch.TOOLTIP);
        list.add(IWeatherRadio.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.pda.info"),
                Component.translatable("item.confluence.pda.info2")
        };
    }
}
