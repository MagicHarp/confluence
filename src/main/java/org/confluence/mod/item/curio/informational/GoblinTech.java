package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoblinTech extends AbstractInfoCurio implements IMetalDetector, IStopwatch, IDPSMeter {
    public GoblinTech() {
        super(ModRarity.ORANGE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IDPSMeter.TOOLTIP);
        list.add(IMetalDetector.TOOLTIP);
        list.add(IStopwatch.TOOLTIP);
    }
}
