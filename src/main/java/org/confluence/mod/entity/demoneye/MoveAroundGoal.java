package org.confluence.mod.entity.demoneye;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MoveAroundGoal extends Goal {
    private final DemonEye mob;
    private final RandomSource random;
    private final LivingEntity target;
    private Vec3 targetPos;

    public MoveAroundGoal(DemonEye mob, LivingEntity target){
        this.mob = mob;
        random = mob.getRandom();
        this.target = target;
    }

    @Override
    public boolean canUse(){
        mob.setNoGravity(true);
        mob.noPhysics=true;
        return true;
    }

    @Override
    public void start(){
        Vec3 dir;
        if(target == null){
            targetPos = mob.position();
        }else {
            dir = target.position().subtract(mob.position()).normalize();
            targetPos = target.position().add(dir.scale(8));
            targetPos = new Vec3(targetPos.x+(random.nextInt(8)-4), target.position().y+(random.nextInt(8)-4), targetPos.z+(random.nextInt(8)-4));
        }
    }

    @Override
    public boolean canContinueToUse(){
        if(target == null){
            return true;
        }
        return mob.position().distanceTo(targetPos) > 1 && mob.position().distanceTo(targetPos) < 6;
    }

    @Override
    public void tick(){
        Vec3 motion = mob.getDeltaMovement();
        BlockHitResult hitResult = mob.level().clip(new ClipContext(mob.position(), motion.add(mob.position()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
        if(hitResult.getType()!= HitResult.Type.MISS){
            System.out.println(hitResult.getDirection());
            mob.setDeltaMovement(motion.x,-motion.y,motion.z);
            motion = mob.getDeltaMovement();
            targetPos = new Vec3(targetPos.x, mob.position().y+random.nextInt(2), targetPos.z);
        }
        mob.setDeltaMovement(motion.add(mob.position().vectorTo(targetPos).normalize().scale(0.1)));
    }
}
