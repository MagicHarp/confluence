package org.confluence.mod.common.entity.monster;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.leaf.CasterCycleAction;
import org.confluence.mod.common.entity.projectile.HostileParticleProjectile;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import javax.annotation.Nullable;

/// 法师怪物基类：三次远程施法后向目标方向重新选取安全落点。
///
/// 施法生成具有飞行时间和方块碰撞的真实弹幕。子类只需覆盖
/// {@link #projectileType()} 就能选择自己的法术类型，攻击节奏和瞬移流程不必复制。
public abstract class BaseCasterMonster extends BaseMonster {
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("move.walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("misc.idle");
    private final CasterCycleAction.HurtResponse hurtResponse;
    private CasterCycleAction cycleAction;

    public BaseCasterMonster(EntityType<? extends BaseCasterMonster> type, Level level) {
        this(type, level, CasterCycleAction.HurtResponse.PAUSE_THEN_TELEPORT);
    }

    public BaseCasterMonster(EntityType<? extends BaseCasterMonster> type, Level level, CasterCycleAction.HurtResponse hurtResponse) {
        super(type, level);
        this.hurtResponse = hurtResponse;
    }

    @Override
    protected boolean hasEntityContactAttack() {
        return true;
    }

    @Override
    protected BTRoot createBT() {
        if (cycleAction == null) {
            cycleAction = new CasterCycleAction(this, this::createProjectile, hurtResponse, casterTiming(), castsPerCycle(), projectilesPerVolley(), projectileIntervalTicks());
        }
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                return cycleAction;
            }
        };
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean accepted = super.hurt(source, amount);
        if (accepted && cycleAction != null && shouldInterruptCastingAfterHurt()) {
            cycleAction.interruptAfterHurt();
        }
        return accepted;
    }

    /// 决定本次有效受击是否打断施法周期。
    protected boolean shouldInterruptCastingAfterHurt() {
        return true;
    }

    /// 返回当前法师固定使用的弹幕类型。
    protected abstract EntityType<HostileParticleProjectile> projectileType();

    /// 返回一次施法动作连续生成的弹幕数量；普通法师每轮只生成一枚。
    protected int projectilesPerVolley() {
        return 1;
    }

    /// 返回每次传送之间的施法动作次数；普通法师保持三次。
    protected int castsPerCycle() {
        return 3;
    }

    /// 返回同一轮内相邻弹幕的间隔。
    protected int projectileIntervalTicks() {
        return 1;
    }

    /// 返回当前法师的完整战斗时序；只有资料明确存在独立周期的变体需要覆盖。
    protected CasterCycleAction.Timing casterTiming() {
        return CasterCycleAction.DEFAULT_TIMING;
    }

    @Nullable
    HostileParticleProjectile createProjectile(LivingEntity target) {
        HostileParticleProjectile projectile = projectileType().create(level());
        if (projectile == null) {
            return null;
        }
        projectile.configure(this, target, (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        return projectile;
    }

    @Override
    public int getCurrentSwingDuration() {
        return 20;
    }

    /// 施法挥手期间播放法术动作，其余时间按实际移动状态选择行走或待机。
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "caster_state", 5, state -> {
            if (swingTime > 0) {
                return state.setAndContinue(DefaultAnimations.ATTACK_CAST);
            }
            return state.setAndContinue(state.isMoving() ? WALK : IDLE);
        }));
    }

}
