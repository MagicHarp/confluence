package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public abstract class ChainingEntity extends Projectile {
    public float lastDelta = 0.0F;
    protected ChainingEntity(EntityType<? extends Projectile> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }
}
