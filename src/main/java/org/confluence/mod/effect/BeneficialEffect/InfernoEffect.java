package org.confluence.mod.effect.BeneficialEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class InfernoEffect extends MobEffect {  //狱火 点燃周围的怪物 （以玩家为中心的5×5×5范围内）
    Level level;

    private AABB BurningType(LivingEntity entity) {
        double halfSize = 2.5;
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        return new AABB(x - halfSize, y - halfSize, z - halfSize, x + halfSize, y + halfSize, z + halfSize);
    }

    public void onFire(LivingEntity entity, int burningtime) {
        for (Entity m : level.getEntitiesOfClass(Monster.class, BurningType(entity))) {
            m.setSecondsOnFire(burningtime);
        }
    }

    public InfernoEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
    }

    public void onAdd(LivingEntity entity, int burningtime) {
        onFire(entity, burningtime);
    }
}
