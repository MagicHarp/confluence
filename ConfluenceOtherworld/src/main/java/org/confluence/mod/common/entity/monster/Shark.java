package org.confluence.mod.common.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Shark extends Piranha {
    public Shark(EntityType<? extends Shark> type, Level level) {
        super(type, level);
    }

    @Override
    protected Goal createStrollGoal() {
        return new SharkRandomSwimmingGoal(this, 0.6, 10);
    }

    @Override
    protected boolean flopsOnLand() {
        return false;
    }

    private static final class SharkRandomSwimmingGoal extends RandomSwimmingGoal {
        private SharkRandomSwimmingGoal(Shark mob, double speed, int interval) {
            super(mob, speed, interval);
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            for (int attempt = 0; attempt < 10; attempt++) {
                Vec3 candidate = BehaviorUtils.getRandomSwimmablePos(mob, 10, 3);
                if (candidate == null) continue;
                AABB box = mob.getBoundingBox().move(candidate.subtract(mob.position())).deflate(1.0E-4);
                if (!mob.level().noCollision(mob, box)) continue;
                boolean submerged = true;
                for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ), Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))) {
                    if (!mob.level().getFluidState(pos).is(FluidTags.WATER)) {
                        submerged = false;
                        break;
                    }
                }
                if (submerged) return candidate;
            }
            return null;
        }
    }
}
