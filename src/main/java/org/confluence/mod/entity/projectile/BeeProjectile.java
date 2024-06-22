package org.confluence.mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;

public class BeeProjectile extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Boolean> DATA_IS_GIANT = SynchedEntityData.defineId(BeeProjectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions SMALL = new EntityDimensions(0.25F, 0.25F, true);
    private static final EntityDimensions GIANT = new EntityDimensions(0.375F, 0.375F, true);
    private int blockHitCount;
    private int lifeTime;
    private Entity target;

    public BeeProjectile(EntityType<BeeProjectile> entityType, Level level) {
        super(entityType, level);
        this.blockHitCount = 0;
        this.lifeTime = 0;
    }

    public BeeProjectile(Level level, LivingEntity owner, boolean isGiant) {
        this(ModEntities.BEE_PROJECTILE.get(), level);
        setOwner(owner);
        this.blockHitCount = 0;
        this.lifeTime = 0;
        entityData.set(DATA_IS_GIANT, isGiant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_IS_GIANT, false);
    }

    public boolean isGiant() {
        return entityData.get(DATA_IS_GIANT);
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount % 20 == 2) {
            level().getEntities(getOwner(), new AABB(getOnPos()).inflate(8.0), entity -> entity instanceof Enemy)
                .stream().min((a, b) -> (int) (a.distanceToSqr(this) - b.distanceToSqr(this)))
                .ifPresent(monster -> this.target = monster);
        }
        if (target != null) {
            if (target.isSpectator() || (target instanceof LivingEntity living && living.isDeadOrDying())) this.target = null;
            if (target != null) {
                Vec3 vec3 = new Vec3(target.getX() - getX(), target.getY() + target.getEyeHeight() / 2.0 - getY(), target.getZ() - getZ());
                double lengthSqr = vec3.lengthSqr();
                if (lengthSqr < 64.0) {
                    double factor = 1.0 - Math.sqrt(lengthSqr) / 8.0;
                    addDeltaMovement(vec3.normalize().scale(factor * factor * (isGiant() ? 0.3 : 0.1)));
                }
            }
        }
        Vec3 motion = getDeltaMovement();
        setYRot((float) (Mth.atan2(motion.x, motion.z) * Mth.RAD_TO_DEG));
        setXRot((float) (Mth.atan2(motion.y, motion.horizontalDistance()) * Mth.RAD_TO_DEG));
        move(MoverType.SELF, motion);
        if (lifeTime++ > (isGiant() ? 220 : 200)) discard();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        Vec3 motion = getDeltaMovement();
        double x = motion.x;
        double y = motion.y;
        double z = motion.z;
        switch (blockHitResult.getDirection().getAxis()) {
            case X -> x = -x;
            case Y -> y = -y;
            case Z -> z = -z;
        }
        setDeltaMovement(x, y, z);
        if (blockHitCount++ > (isGiant() ? 2 : 1)) discard();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity != getOwner()) {
            float damage = 5.0F + (isGiant() ? random.nextInt(1, 4) : (random.nextBoolean() ? 1 : 0));
            entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), damage);
            if (isGiant()) {
                Vec3 motion = position().subtract(entity.position()).normalize().scale(0.5);
                entity.push(motion.x, motion.y, motion.z);
            }
        }
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return isGiant() ? GIANT : SMALL;
    }
}
