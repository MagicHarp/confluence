package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalDetector extends AbstractInfoCurio implements IMetalDetector {
    public MetalDetector() {
        super(ModRarity.BLUE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.metal_detector.info"),
            Component.translatable("item.confluence.metal_detector.info2"),
            Component.translatable("item.confluence.metal_detector.info3"),
            Component.translatable("item.confluence.metal_detector.info4"),
            Component.translatable("item.confluence.metal_detector.info5")
        };
    }
}
