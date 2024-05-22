package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.levelgen.Heightmap;

public class DemonEyeAttackGoal extends Goal {
    private final DemonEye self;
    private int nextSweepTick;

    DemonEyeAttackGoal(DemonEye demonEye) {
        this.self = demonEye;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = self.getTarget();
        return target != null && self.canAttack(target, TargetingConditions.DEFAULT);
    }

    public void start() {
        this.nextSweepTick = adjustedTickDelay(8);
        self.attackPhase = DemonEye.AttackPhase.CIRCLE;
        setAnchorAboveTarget();
    }

    public void stop() {
        self.anchorPoint = self.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, self.anchorPoint).above(10 + self.getRandom().nextInt(20));
    }

    public void tick() {
        if (self.attackPhase == DemonEye.AttackPhase.CIRCLE) {
            this.nextSweepTick--;
            if (nextSweepTick <= 0) {
                self.attackPhase = DemonEye.AttackPhase.SWOOP;
                setAnchorAboveTarget();
                this.nextSweepTick = this.adjustedTickDelay(8);
            }
        }
    }

    private void setAnchorAboveTarget() {
        if (self.getTarget() == null) return;
        self.anchorPoint = self.getTarget().blockPosition().above(20 + self.getRandom().nextInt(10));
        if (self.anchorPoint.getY() < self.level().getHeight()) {
            self.anchorPoint = new BlockPos(self.anchorPoint.getX(), self.level().getSeaLevel() + 1, self.anchorPoint.getZ());
        }
    }
}
