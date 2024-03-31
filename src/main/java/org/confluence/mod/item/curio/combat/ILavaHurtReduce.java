package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.IEntity;

public interface ILavaHurtReduce {
    default void freshLavaReduce(LivingEntity living) {
        ((IEntity) living).c$freshLavaHurtReduce(living);
    }
}
