package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.ILivingEntity;

public interface IFallResistance {
    int getFallResistance();

    default void freshFallResistance(LivingEntity living) {
        ((ILivingEntity) living).c$freshFallResistance();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.fall_resistance");
}
