package org.confluence.mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public abstract class SwordProjectile extends Projectile {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(SwordProjectile.class, EntityDataSerializers.INT);
    private static final int TIME_EXISTENCE = 40;

    public SwordProjectile(EntityType<? extends SwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_VARIANT_ID, 0);
    }

    protected abstract int getDamage();

    @Override
    public void tick() {
        super.tick();
        checkCollision();
        Vec3 vec3 = getDeltaMovement();
        double offX = getX() + vec3.x;
        double offY = getY() + vec3.y;
        double offZ = getZ() + vec3.z;
        setDeltaMovement(vec3.scale(0.93));
        setPos(offX, offY, offZ);
        if (tickCount >= TIME_EXISTENCE) this.remove(RemovalReason.KILLED);
    }

    private void checkCollision() {
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity != getOwner());
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitResult);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            onHitBlock((BlockHitResult) hitResult);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        if (!level().isClientSide) {
            Entity entity = entityHitResult.getEntity();
            if (entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), getDamage())) {
                ModUtils.knockBackA2B(this, entity, 0.5, 0.2);
                discard();
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }
}
