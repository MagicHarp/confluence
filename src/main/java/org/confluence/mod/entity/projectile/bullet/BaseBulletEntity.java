package org.confluence.mod.entity.projectile.bullet;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class BaseBulletEntity extends Projectile {
    public BaseBulletEntity(EntityType<BaseBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public abstract SimpleParticleType getParticle();

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        checkInsideBlocks();
        Vec3 vec3 = getDeltaMovement();
        HitResult.Type hitresult$type = hitresult.getType();
        if (hitresult$type == HitResult.Type.BLOCK || vec3.length() < 0.007) {
            discard();
            return;
        }
        if (hitresult$type == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitresult);
        }

        double offX = getX() + vec3.x;
        double offY = getY() + vec3.y;
        double offZ = getZ() + vec3.z;
        setDeltaMovement(vec3.scale(0.9));
        if (!isNoGravity()) {
            Vec3 vec31 = getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - getGravity(), vec31.z);
        }
        setPos(offX, offY, offZ);
        level().addParticle(getParticle(), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().hurt(damageSources().indirectMagic(this, getOwner()), getDamage());
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
    }

    protected float getDamage() {
        return 5;
    }

    protected double getGravity() {
        return 0.03;
    }
}
