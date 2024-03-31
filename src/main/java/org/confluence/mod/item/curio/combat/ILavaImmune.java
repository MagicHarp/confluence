package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.IEntity;

public interface ILavaImmune {
    int getLavaImmuneTicks();

    default void freshLavaImmuneTicks(LivingEntity living) {
        ((IEntity) living).c$freshLavaImmuneTicks(living);
    }
}
