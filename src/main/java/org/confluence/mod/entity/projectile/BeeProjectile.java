package org.confluence.mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;

public class BeeProjectile extends AbstractHurtingProjectile {
    private int blockHitCount;
    private int lifeTime;
    private LivingEntity target;
    private BlockPos targetPos;
    private boolean isGiant;

    public BeeProjectile(EntityType<BeeProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public BeeProjectile(Level level, LivingEntity owner, boolean isGiant) {
        this(ModEntities.BEE_PROJECTILE.get(), level);
        setOwner(owner);
        this.blockHitCount = 0;
        this.lifeTime = 0;
        this.isGiant = isGiant;
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount % 20 == 2) {
            level().getEntitiesOfClass(Monster.class, new AABB(getOnPos()).inflate(8.0))
                .stream().min((a, b) -> Math.round(a.distanceTo(this) - b.distanceTo(this)))
                .ifPresent(monster -> this.target = monster);
        }
        if (target != null) {
            if (target.isSpectator() || target.isDeadOrDying()) this.target = null;
            if (target == null) {
                if (targetPos != null && (!level().isEmptyBlock(targetPos) || targetPos.getY() <= level().getMinBuildHeight())) {
                    this.targetPos = null;
                }
                if (targetPos == null || random.nextInt(30) == 0 || targetPos.closerToCenterThan(position(), 2.0)) {
                    this.targetPos = BlockPos.containing(getX() + random.nextInt(7) - random.nextInt(7), getY() + random.nextInt(6) - 2.0D, getZ() + random.nextInt(7) - random.nextInt(7));
                }
                Vec3 motion = getDeltaMovement();
                double x = (Math.signum(targetPos.getX() + 0.5 - getX()) * 0.5 - motion.x) * 0.1;
                double y = (Math.signum(targetPos.getY() + 0.5 - getY()) * 0.5 - motion.y) * 0.1;
                double z = (Math.signum(targetPos.getZ() + 0.5 - getZ()) * 0.5 - motion.z) * 0.1;
                setDeltaMovement(motion.add(x, y, z));
            } else {
                Vec3 vec3 = new Vec3(target.getX() - getX(), target.getY() + target.getEyeHeight() / 2.0 - getY(), target.getZ() - getZ());
                double lengthSqr = vec3.lengthSqr();
                if (lengthSqr < 64.0) {
                    double factor = 1.0 - Math.sqrt(lengthSqr) / 8.0;
                    addDeltaMovement(vec3.normalize().scale(factor * factor * (isGiant ? 0.3 : 0.1)));
                }
            }
        }
        move(MoverType.SELF, getDeltaMovement());
        if (lifeTime++ > (isGiant ? 220 : 200)) discard();
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
        if (blockHitCount++ > (isGiant ? 2 : 1)) discard();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity != getOwner()) {
            float damage = 5.0F + (isGiant ? random.nextInt(1, 4) : (random.nextBoolean() ? 1 : 0));
            entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), damage);
            if (isGiant) {
                Vec3 motion = position().subtract(entity.position()).normalize().scale(0.5);
                entity.push(motion.x, motion.y, motion.z);
            }
        }
    }
}
