package org.confluence.mod.entity.projectile.ammo;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class AmmoEntity extends Projectile {
    public AmmoEntity(EntityType<AmmoEntity> entityType, Level level) {
        super(entityType, level);
    }

    public float getAttackDamage() {
        return 5;
    }

    @Override
    protected void defineSynchedData() {
    }
}
