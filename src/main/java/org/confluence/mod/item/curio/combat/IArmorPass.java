package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;

public interface IArmorPass {
    int getPassValue();

    default Component getArmorPassToolTip() {
        return Component.translatable("curios.tooltip.armor_pass", getPassValue());
    }
}
