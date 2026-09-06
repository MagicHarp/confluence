package org.confluence.mod.common.entity.monster;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.data.map.CreatureDefinition;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.BTStatus;
import org.confluence.mod.common.entity.ai.bt.composite.SelectorNode;
import org.confluence.mod.common.entity.ai.bt.composite.SequenceNode;
import org.confluence.mod.common.entity.ai.bt.condition.HasTargetCondition;
import org.confluence.mod.common.entity.ai.bt.leaf.DirectFloatingPursuitAction;
import org.confluence.mod.common.entity.ai.bt.leaf.LookForwardWanderFlyAction;
import org.confluence.mod.common.entity.ai.bt.leaf.SpawnProjectileAction;
import org.confluence.mod.common.entity.ai.bt.leaf.WaitAction;
import org.confluence.mod.common.entity.projectile.HostileParticleProjectile;
import org.confluence.mod.common.init.entity.ModEntities;

/// 发射粉色能量弹幕的腹足怪。
public final class Gastropod extends RangedFlyingMonster {
    private static final double MAX_HOVER_HEIGHT = 4.0;
    private static final int PURSUIT_TICKS = 80;
    private static final int SHOT_COOLDOWN = 55;
    private static final int WINDUP_TICKS = 8;
    private boolean firingWindup;
    private boolean shotCancelled;

    public Gastropod(EntityType<? extends Gastropod> type, Level level) {
        super(type, level, SHOT_COOLDOWN, 0.8);
    }

    @Override
    protected BTRoot createBT() {
        CreatureDefinition.BehaviorOverrides behavior = creatureDefinition().behavior();
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                return SelectorNode.of(
                        SequenceNode.of(new HasTargetCondition(Gastropod.this),
                                new DirectFloatingPursuitAction(Gastropod.this, MAX_HOVER_HEIGHT, PURSUIT_TICKS),
                                new FiringWindupAction(),
                                new SpawnProjectileAction(Gastropod.this, Gastropod.this::createProjectile),
                                new WaitAction(behavior.shotCooldownOr(SHOT_COOLDOWN))),
                        new LookForwardWanderFlyAction(Gastropod.this, behavior.wanderSpeedOr(0.18), 0.0F));
            }
        };
    }

    @Override
    protected Projectile createProjectile(LivingEntity target) {
        HostileParticleProjectile projectile = ModEntities.GASTROPOD_PROJECTILE.get().create(level());
        if (projectile == null) {
            return null;
        }
        projectile.configure(this, target, (float) (getAttributeValue(Attributes.ATTACK_DAMAGE) * shotMultiplier()));
        return projectile;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean damaged = super.hurt(source, amount);
        if (damaged && firingWindup) shotCancelled = true;
        return damaged;
    }

    /// 开壳蓄力期间受到有效伤害会取消本次激光，随后重新进入盘旋冷却。
    private final class FiringWindupAction extends BTNode {
        private int ticks;

        @Override
        public void start() {
            ticks = 0;
            firingWindup = true;
            shotCancelled = false;
            getNavigation().stop();
        }

        @Override
        public BTStatus execute() {
            LivingEntity target = getTarget();
            if (shotCancelled || target == null || !target.isAlive() || !hasLineOfSight(target))
                return BTStatus.FAILURE;
            faceCombatPosition(target.getEyePosition(), 30.0F, 30.0F);
            return ++ticks >= WINDUP_TICKS ? BTStatus.SUCCESS : BTStatus.RUNNING;
        }

        @Override
        public void stop() {
            firingWindup = false;
        }
    }
}
