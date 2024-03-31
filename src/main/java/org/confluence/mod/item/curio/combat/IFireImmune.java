package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.IEntity;

public interface IFireImmune {
    default void freshFireImmune(LivingEntity living) {
        ((IEntity) living).c$freshFireImmune(living);
    }
}
