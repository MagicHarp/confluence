package org.confluence.mod.entity.demoneye;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;

public class DemonEyeAttackPlayerTargetGoal extends Goal {
    private final DemonEye self;
    private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);
    private int nextScanTick = reducedTickDelay(20);

    public DemonEyeAttackPlayerTargetGoal(DemonEye demonEye) {
        this.self = demonEye;
    }

    public boolean canUse() {
        if (nextScanTick > 0) {
            this.nextScanTick--;
        } else {
            this.nextScanTick = reducedTickDelay(60);
            List<Player> players = self.level().getNearbyPlayers(attackTargeting, self, self.getBoundingBox().inflate(16.0, 64.0, 16.0));
            if (!players.isEmpty()) {
                players.sort(Comparator.comparing(Entity::getY));

                for (Player player : players) {
                    if (self.canAttack(player, TargetingConditions.DEFAULT)) {
                        self.setTarget(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canContinueToUse() {
        LivingEntity target = self.getTarget();
        return target != null && self.canAttack(target, TargetingConditions.DEFAULT);
    }
}
