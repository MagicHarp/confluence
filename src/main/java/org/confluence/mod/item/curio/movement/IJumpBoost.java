package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.ILivingEntity;

public interface IJumpBoost {
    double getBoost();

    default void freshJumpBoost(LivingEntity living) {
        ((ILivingEntity) living).c$freshJumpBoost();
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.jump_boost");
}
