package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public interface IJumpBoost {
    double getBoost();

    Component TOOLTIP = Component.translatable("curios.tooltip.jump_boost");
}
