package org.confluence.mod.entity.demoneye;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.mixin.accessor.DistanceManagerAccessor;

/** 白天的AI
 * @author voila */
public class LeaveGoal extends Goal {
    protected final DemonEye mob;
    private Vec3 targetMotion;

    public LeaveGoal(DemonEye mob){
        this.mob = mob;
    }

    @Override
    public void start(){
        RandomSource random = mob.getRandom();
        double x = random.nextDouble() - 0.5;
        double y = 0.1 + (0.6 - 0.1) * random.nextDouble();  // 0.1-0.6
        double z = random.nextDouble() - 0.5;
        targetMotion = new Vec3(x, y, z).normalize().scale(0.25);
    }

    @Override
    public boolean canUse(){
        return mob.level().isDay();
    }

    @Override
    public void tick(){
        ServerLevel level = (ServerLevel) mob.level();
        // 当前区块加载等级
        int loadLevel = ((DistanceManagerAccessor) level.getChunkSource().chunkMap.getDistanceManager()).getTickingTicketsTracker().getLevel(new ChunkPos(mob.blockPosition()));
        if(loadLevel >= 30){ // 假如区块加载等级的设定变了，这里也得改
            mob.discard();
            return;
        }

        Vec3 motion = mob.getDeltaMovement();
        if(motion.length() < 0.5){
            mob.addDeltaMovement(targetMotion);
        }
        float[] angle = SurroundTargetGoal.dirToRot(mob.getDeltaMovement());
        mob.setXRot(angle[1]);
        mob.setYRot(angle[0]);
        // 如果大于这个速度也不用管，不管哪个方向
    }
}
