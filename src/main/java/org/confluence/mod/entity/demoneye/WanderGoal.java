package org.confluence.mod.entity.demoneye;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/** 周围没有目标的AI，乱逛
 * @author voila */
public class WanderGoal extends SurroundTargetGoal {
    private double anchorY=Double.NaN;

    public WanderGoal(DemonEye mob){
        super(mob);
        maxSpeed=0.2;
    }

    @Override
    public boolean canUse(){
        return mob.getTarget() == null && mob.level().isNight();
    }

    @Override
    public boolean canContinueToUse(){
        return canUse() && ticksLeft > 0 && mob.position().distanceTo(targetPos) > 1.5;
    }

    @Override
    public void start(){
        locateCount++;
        mob.setDeltaMovement(mob.getDeltaMovement().with(Direction.Axis.Y, 0));
        if(Double.isNaN(anchorY)){
            anchorY = mob.position().y;
        }
        double x = random.nextDouble() * 10 - 5;
        double y = getOffsetY() + 5;
        double z = random.nextDouble() * 10 - 5;
        targetPos = new Vec3(x, 0, z).normalize().scale(15).add(mob.position()).with(Direction.Axis.Y, y + anchorY);
        ticksLeft = 30;
    }
}
