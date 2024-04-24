package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;

public interface IAggroAttach {
    int getAggro();

    Component TOOLTIP = Component.translatable("curios.tooltip.aggro_attach");
}
