package org.confluence.mod.util;

import net.minecraft.world.entity.LivingEntity;

public interface ILivingEntity {
    void c$freshJumpBoost(LivingEntity living);

    void c$freshFallResistance(LivingEntity living);

    void c$setInvulnerableTime(int time);

    void c$freshCriticalChance(LivingEntity living);

    float c$getCriticalChance();
}
