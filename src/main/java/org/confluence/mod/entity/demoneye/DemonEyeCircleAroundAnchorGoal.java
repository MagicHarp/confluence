package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class DemonEyeCircleAroundAnchorGoal extends DemonEyeMoveTargetGoal {
    private float angle;
    private float distance;
    private float height;
    private float clockwise;

    DemonEyeCircleAroundAnchorGoal(DemonEye demonEye) {
        super(demonEye);
    }

    @Override
    public boolean canUse() {
        return self.getTarget() == null || self.attackPhase != DemonEye.AttackPhase.CIRCLE;
    }

    public void start() {
        RandomSource random = self.getRandom();
        this.distance = 5.0F + random.nextFloat() * 10.0F;
        this.height = -4.0F + random.nextFloat() * 9.0F;
        this.clockwise = random.nextBoolean() ? 1.0F : -1.0F;
        selectNext();
    }

    public void tick() {
        RandomSource random = self.getRandom();
        if (random.nextInt(adjustedTickDelay(359)) == 0) {
            this.height = -4.0F + random.nextFloat() * 9.0F;
        }
        if (random.nextInt(adjustedTickDelay(250)) == 0) {
            this.distance++;
            if (distance > 15.0F) {
                this.distance = 5.0F;
                this.clockwise = -clockwise;
            }
        }

        if (random.nextInt(adjustedTickDelay(450)) == 0) {
            this.angle = random.nextFloat() * 2.0F * 3.1415927F;
            selectNext();
        }

        if (touchingTarget()) {
            selectNext();
        }

        if (self.moveTargetPoint.y < self.getY() && !self.level().isEmptyBlock(self.blockPosition().below(1))) {
            this.height = Math.max(1.0F, height);
            selectNext();
        }

        if (self.moveTargetPoint.y > self.getY() && !self.level().isEmptyBlock(self.blockPosition().above(1))) {
            this.height = Math.min(-1.0F, height);
            selectNext();
        }
    }

    private void selectNext() {
        if (BlockPos.ZERO.equals(self.anchorPoint)) {
            self.anchorPoint = self.blockPosition();
        }
        this.angle += clockwise * 15.0F * 0.017453292F;
        self.moveTargetPoint = Vec3.atLowerCornerOf(self.anchorPoint).add(distance * Mth.cos(angle), -4.0F + height, distance * Mth.sin(angle));
    }
}
