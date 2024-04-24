package org.confluence.mod.item.curio;

import net.minecraft.network.chat.Component;

public interface ILavaImmune {
    default int getLavaImmuneTicks() {
        return 140;
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.lava_immune");
}
