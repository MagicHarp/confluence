package org.confluence.mod.common.summon.flying;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.api.summon.SummonTargetCache;
import org.confluence.mod.common.summon.*;

/// 飞雀召唤物的运行实例。
public final class FinchSummon extends FlyingSummon {
    public static final int SLOT_COST = 1;
    public static final float BASE_DAMAGE = 2.0F;
    private int attackPhaseTicks;
    private int hitMovementCooldown;
    private boolean followingOwner;

    public FinchSummon(ServerPlayer owner, int slotCost, SummonStats stats, SummonPose initialPose) {
        super(Confluence.asResource("finch_baby"), owner, slotCost, stats, initialPose, 0.5, 0.5);
        addGoal(1, new AttackGoal(this));
        addGoal(9, new PerchOwnerGoal(this));
    }

    @Override
    protected LivingEntity findTarget() {
        LivingEntity target = SummonTargetCache.acquire(owner().serverLevel(), owner(), uuid(), owner().position(), 50.0);
        return target != null && position().distanceToSqr(owner().position()) <= 50.0 * 50.0 && SummonTargetCache.hasVisibleTarget(owner().serverLevel(), owner(), position(), Double.MAX_VALUE, target) ? target : null;
    }

    @Override
    public int confluence$getImmunityDuration(DamageSource damageSource) {
        return 15;
    }

    @Override
    protected void beforeGoalTick() {
        hitMovementCooldown--;
    }

    @Override
    protected void afterPathAdvance(SummonPose previousPreviousPose, SummonPose previousPose, SummonPose currentPose) {
        if (target() == null) {
            return;
        }
        AABB bounds = AABB.ofSize(position().add(0.0, 0.25, 0.0), 0.5, 0.5, 0.5).inflate(0.75);
        boolean touching = !owner().level().getEntitiesOfClass(LivingEntity.class, bounds,
                candidate -> candidate == target() || SummonTargetCache.isValidTarget(owner(), candidate, position(), 32.0, false)).isEmpty();
        hurtTouchingTargets(bounds, 32.0, 1.0F);
        if (touching && hitMovementCooldown <= -5) hitMovementCooldown = 10;
    }

    private void attack(LivingEntity target) {
        Vec3 direction = targetPosition().subtract(position());
        double distanceSqr = Math.max(0.001, direction.lengthSqr());
        if (--attackPhaseTicks <= 0) {
            Rotation rotation = turnToward(direction, 90.0F, 85.0F);
            Vec3 movement = velocity().scale(0.91).add(0.0, previousVerticalBob(), 0.0);
            Vec3 look = Vec3.directionFromRotation(rotation.pitch(), rotation.yaw());
            if (angleBetween(look, direction) < 0.5 && movement.length() < 1.0) {
                movement = movement.add(direction.normalize().scale(0.1));
            }
            moveBy(addOwnerFollow(movement), rotation.yaw(), rotation.pitch());
            if (distanceSqr < 3.0 && hitMovementCooldown < 0) attackPhaseTicks = 20;
        } else {
            Vec3 forward = Vec3.directionFromRotation(currentPose().pitch(), currentPose().yaw()).normalize();
            Rotation rotation = turnToward(direction, 10.0F, 85.0F);
            Vec3 movement = velocity().scale(0.91).add(forward.scale(0.03)).add(0.0, Math.min(0.02, 1.0 / distanceSqr), 0.0)
                    .add(0.0, previousVerticalBob(), 0.0);
            moveBy(addOwnerFollow(movement), rotation.yaw(), rotation.pitch());
        }
    }

    private Vec3 addOwnerFollow(Vec3 movement) {
        if (position().distanceToSqr(owner().position()) < 32.0 * 32.0) return movement;
        Vec3 direction = owner().position().add(0.0, 1.8, 0.0).subtract(position());
        return direction.lengthSqr() < 1.0E-8 ? movement : movement.add(direction.normalize().scale(0.035));
    }

    private double previousVerticalBob() {
        double current = Math.sin(tickCount() * 0.5F) * 0.06F;
        double previous = Math.sin((tickCount() - 1) * 0.5F) * 0.06F;
        return current - previous;
    }

    @Override
    protected Vec3 idleVelocity() {
        return super.idleVelocity().add(0.0, previousVerticalBob(), 0.0);
    }

    @Override
    public SummonVisualState visualState() {
        return followingOwner ? new SummonVisualState(true, SummonAnimation.NONE, 0, 0, 0.0F, 1.0F, 1.0F) : SummonVisualState.DEFAULT;
    }

    public static Vec3 perchPosition(Vec3 ownerPosition, float bodyYaw, int order) {
        Vec3 forward = Vec3.directionFromRotation(0.0F, bodyYaw).multiply(1.0, 0.0, 1.0).normalize();
        Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
        return order == 0
                ? ownerPosition.add(0.0, 1.42, 0.0).add(right.scale(0.32)).subtract(forward.scale(0.08))
                : ownerPosition.add(0.0, 1.78 + (order - 1) * 0.16, 0.0);
    }

    private Rotation turnToward(Vec3 direction, float maximumYawChange, float maximumPitchChange) {
        Vec3 normalized = direction.normalize();
        float desiredYaw = (float) Math.toDegrees(Math.atan2(-normalized.x, normalized.z));
        float desiredPitch = (float) Math.toDegrees(Math.asin(-normalized.y));
        float yaw = currentPose().yaw() + Mth.clamp(Mth.wrapDegrees(desiredYaw - currentPose().yaw()), -maximumYawChange, maximumYawChange);
        float pitch = currentPose().pitch() + Mth.clamp(Mth.wrapDegrees(desiredPitch - currentPose().pitch()), -maximumPitchChange, maximumPitchChange);
        return new Rotation(yaw, pitch);
    }

    private static double angleBetween(Vec3 first, Vec3 second) {
        double denominator = Math.sqrt(first.lengthSqr() * second.lengthSqr());
        if (denominator < 1.0E-8) {
            return Math.PI;
        }
        return Math.acos(Mth.clamp(first.dot(second) / denominator, -1.0, 1.0));
    }

    private static final class AttackGoal extends SummonGoal<FinchSummon> {
        private AttackGoal(FinchSummon summon) {
            super(summon);
        }

        @Override
        public boolean canUse() {
            return summon.target() != null;
        }

        @Override
        public void tick() {
            summon.attack(summon.target());
        }
    }

    private static final class PerchOwnerGoal extends SummonGoal<FinchSummon> {
        private PerchOwnerGoal(FinchSummon summon) {
            super(summon);
        }

        @Override
        public boolean canUse() {
            return summon.target() == null;
        }

        @Override
        public void start() {
            summon.followingOwner = true;
        }

        @Override
        public void stop() {
            summon.followingOwner = false;
        }

        @Override
        public void tick() {
            Vec3 destination = perchPosition(summon.owner().position(), summon.owner().yBodyRot, summon.order());
            Vec3 ownerVelocity = summon.owner().getDeltaMovement();
            if (summon.owner().onGround()) ownerVelocity = ownerVelocity.multiply(1.0, 0.0, 1.0);
            Vec3 offset = destination.add(ownerVelocity).subtract(summon.position());
            Vec3 desiredVelocity = ownerVelocity.add(offset.scale(0.45));
            if (desiredVelocity.lengthSqr() > 1.0)
                desiredVelocity = desiredVelocity.normalize();
            Vec3 movement = summon.velocity().scale(0.35).add(desiredVelocity.scale(0.65));
            if (offset.lengthSqr() < 0.25 * 0.25) {
                summon.moveBy(movement, summon.owner().yBodyRot, 0.0F);
                return;
            }
            float yaw = movement.horizontalDistanceSqr() < 1.0E-8 ? summon.owner().yBodyRot
                    : (float) Math.toDegrees(Math.atan2(-movement.x, movement.z));
            float pitch = movement.lengthSqr() < 1.0E-8 ? 0.0F
                    : Mth.clamp((float) Math.toDegrees(Math.asin(-movement.normalize().y)), -35.0F, 35.0F);
            summon.moveBy(movement, yaw, pitch);
        }
    }

    private record Rotation(float yaw, float pitch) {}
}
