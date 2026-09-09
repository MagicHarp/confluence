package org.confluence.mod.common.summon.sword;

import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.summon.SummonGoal;

/// 控制召唤剑在没有目标时回到玩家背后。
final class SwordFollowOwnerGoal extends SummonGoal<SummonSword> {
    SwordFollowOwnerGoal(SummonSword summon) {
        super(summon);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void start() {
        summon.setFollowingOwner(true);
    }

    @Override
    public void stop() {
        summon.setFollowingOwner(false);
    }

    @Override
    public void tick() {
        Vec3 targetPosition = summon.formationPosition(1.0, 0.35, 0.8);
        Vec3 direction = targetPosition.subtract(summon.position());
        if (direction.lengthSqr() < 1.0E-4) {
            summon.moveTo(summon.followPose(summon.position(), targetPosition));
            return;
        }
        double speed = Math.min(direction.length() * 0.35, 0.85);
        Vec3 desiredVelocity = direction.normalize().scale(speed);
        Vec3 nextVelocity = summon.velocity().scale(0.72).add(desiredVelocity.scale(0.28));
        Vec3 nextPosition = summon.position().add(nextVelocity);
        summon.moveTo(summon.followPose(nextPosition, targetPosition));
    }
}
