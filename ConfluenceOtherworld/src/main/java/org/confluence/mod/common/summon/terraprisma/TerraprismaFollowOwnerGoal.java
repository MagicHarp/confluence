package org.confluence.mod.common.summon.terraprisma;

import net.minecraft.world.phys.Vec3;
import org.confluence.mod.common.summon.SummonGoal;

/// 让泰拉棱镜在没有目标时回到主人背后。
final class TerraprismaFollowOwnerGoal extends SummonGoal<TerraprismaSummon> {
    TerraprismaFollowOwnerGoal(TerraprismaSummon summon) {
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
        int sequence = summon.order() + 1;
        Vec3 forward = Vec3.directionFromRotation(0.0F, summon.owner().yBodyRot).multiply(1.0, 0.0, 1.0).normalize();
        Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
        double backDistance = 0.6F - 0.05F * (sequence - 1);
        Vec3 targetPosition = summon.owner().position().subtract(forward.scale(backDistance))
                .add(0.0, 1.0, 0.0)
                .add(right.scale(0.2F * (sequence / 2) * ((sequence & 1) == 0 ? 1.0F : -1.0F)));
        Vec3 direction = targetPosition.subtract(summon.position());
        if (direction.lengthSqr() < 1.0E-4) {
            summon.moveTo(summon.followPose(summon.position(), targetPosition));
            return;
        }
        Vec3 ownerVelocity = summon.owner().getDeltaMovement();
        if (summon.owner().onGround()) ownerVelocity = ownerVelocity.multiply(1.0, 0.0, 1.0);
        Vec3 desiredVelocity = ownerVelocity.add(direction.scale(0.45));
        if (desiredVelocity.lengthSqr() > 1.0) desiredVelocity = desiredVelocity.normalize();
        Vec3 nextVelocity = summon.velocity().scale(0.35).add(desiredVelocity.scale(0.65));
        Vec3 nextPosition = summon.position().add(nextVelocity);
        summon.moveTo(summon.followPose(nextPosition, targetPosition));
    }
}
