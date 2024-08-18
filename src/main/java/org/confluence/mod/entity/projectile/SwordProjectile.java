package org.confluence.mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SwordProjectile extends Projectile { // todo 修改
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(SwordProjectile.class, EntityDataSerializers.INT);
    private static final float DAMAGE = 9.0F;
    private static final int TIME_EXISTENCE = 40;

    public SwordProjectile(EntityType<? extends SwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_VARIANT_ID, 0);
    }

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
        Vec3 start = getPosition(1.0F);
        Vec3 end = start.add(getDeltaMovement());

        AABB boundingBox = getBoundingBox().expandTowards(getDeltaMovement()).inflate(0.1);

        List<Entity> entities = level().getEntities(this, boundingBox, Entity::isAlive);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != this.getOwner()) {
                this.onHitEntity(new EntityHitResult(entity));
                break;
            }
        }


        BlockPos minPos = new BlockPos((int) boundingBox.minX, (int) boundingBox.minY, (int) boundingBox.minZ);
        BlockPos maxPos = new BlockPos((int) boundingBox.maxX, (int) boundingBox.maxY, (int) boundingBox.maxZ);

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (!level().getBlockState(pos).isAir() && !level().getBlockState(pos).is(Blocks.GRASS) && !level().getBlockState(pos).is(Blocks.TALL_GRASS)
                    && !level().getBlockState(pos).is(Blocks.WATER)) {
                this.remove(RemovalReason.KILLED);
                break;
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        if (!this.level().isClientSide) {
            super.onHitEntity(entityHitResult);
            Entity entity = entityHitResult.getEntity();
            if (entity.hurt(damageSources().thrown(this, this.getOwner()), DAMAGE)) {
                LivingEntity living = (LivingEntity) entity;
                living.knockback(0.5F, Mth.sin(getYRot() * Mth.DEG_TO_RAD), -Mth.cos(getYRot() * Mth.DEG_TO_RAD));
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }
}
