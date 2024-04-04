package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;

public interface ITallyCounter {
    static Component getInfo(int amount, Component info) {
        return Component.translatable("info.confluence.tally_counter").append(info).append("': " + amount);
    }
}
