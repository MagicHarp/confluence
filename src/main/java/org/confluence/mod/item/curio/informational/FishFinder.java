package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FishFinder extends AbstractInfoCurio implements IFishermansPocketGuide, IWeatherRadio, ISextant {
    public FishFinder() {
        super(ModRarity.ORANGE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFishermansPocketGuide.TOOLTIP);
        list.add(ISextant.TOOLTIP);
        list.add(IWeatherRadio.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.fish_finder.info"),
        };
    }
}
