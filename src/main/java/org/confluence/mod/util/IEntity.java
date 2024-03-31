package org.confluence.mod.util;

import net.minecraft.world.entity.LivingEntity;

public interface IEntity {
    void c$freshFireImmune(LivingEntity living);

    void c$freshLavaHurtReduce(LivingEntity living);

    void c$freshLavaImmuneTicks(LivingEntity living);
}
