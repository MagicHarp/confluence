package org.confluence.mod.common.summon;

import net.minecraft.world.phys.Vec3;

/// 让飞行召唤物在空闲时保留惯性，并在距离过远时主动返航。
public final class MomentumSummonIdleGoal<T extends FlyingSummon> extends SummonGoal<T> {
    private static final double SETTLED_DISTANCE_SQR = 0.75 * 0.75;
    private final double height;
    private final double acceleration;
    private final double maximumSpeed;
    private final double spacing;
    private final double backDistance;
    private final int recalculateInterval;
    private int recalculateCooldown;
    private Vec3 followDestination;

    public MomentumSummonIdleGoal(T summon, double height, double acceleration, double maximumSpeed) {
        this(summon, height, acceleration, maximumSpeed, 0);
    }

    public MomentumSummonIdleGoal(T summon, double height, double acceleration, double maximumSpeed, int recalculateInterval) {
        this(summon, height, acceleration, maximumSpeed, recalculateInterval, 0.8, 1.4);
    }

    public MomentumSummonIdleGoal(T summon, double height, double acceleration, double maximumSpeed,
                                  int recalculateInterval, double spacing, double backDistance) {
        super(summon);
        this.height = height;
        this.acceleration = acceleration;
        this.maximumSpeed = maximumSpeed;
        this.recalculateInterval = recalculateInterval;
        this.spacing = spacing;
        this.backDistance = backDistance;
    }

    @Override
    public boolean canUse() {
        return summon.target() == null;
    }

    @Override
    public void tick() {
        if (followDestination == null || recalculateInterval == 0 || --recalculateCooldown <= 0) {
            followDestination = summon.formationPosition(height, spacing, backDistance);
            recalculateCooldown = recalculateInterval;
        }
        if (summon.position().distanceToSqr(followDestination) > SETTLED_DISTANCE_SQR) {
            summon.moveToward(followDestination, acceleration, maximumSpeed);
        } else {
            summon.moveBy(summon.idleVelocity());
        }
    }

    @Override
    public void stop() {
        followDestination = null;
    }
}
