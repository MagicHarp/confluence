package org.confluence.mod.entity.boss.eow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.boss.IBossFSM;
import org.confluence.mod.entity.worm.AbstractWormEntity;
import org.confluence.mod.entity.worm.BaseWormPart;
import org.confluence.mod.util.ModUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;

public class TestWormPart extends BaseWormPart<TestWormEntity> implements GeoEntity, IBossFSM {
    // 吐口水；所有体节都从这里开始，头节会改为冲撞行为模式
    public static final State<TestWormPart> STATE_SPIT = new State<>() {
        @Override
        public void tick(TestWormPart boss) {
            // 如果变成头的话直接开创
            if (boss.getSegmentType() == SegmentType.HEAD) {
                boss.toState(STATE_RUSH);
                return;
            }
            // TODO: 平衡一下发射间隔
            int shootInterval = 20;
            if (boss.indexAI % shootInterval == 0) {
                boss.spit();
            }
            boss.indexAI ++;
        }
    };
    // 冲向玩家
    public static final State<TestWormPart> STATE_RUSH = new State<>() {
        @Override
        public void tick(TestWormPart boss) {
            LivingEntity target = boss.getTarget();

            // 脱战
            if (target == null) {
                boss.getParentMob().discard();
                return;
            }

            // 注：indexAI = 0时为冲刺，后续则为过渡时期

            double distSqr = boss.position().distanceToSqr(target.position());
            // 速度
            boolean shouldIncreaseIdx = true;
            // 还没靠近过玩家
            if (boss.indexAI == 0) {
                // 在足够接近前不要停止冲刺
                if (distSqr > 64) {
                    Vec3 targetLoc = target.getEyePosition();
                    boss.acc = ModUtils.getDirection(boss.position(), targetLoc, 0.5);
                    shouldIncreaseIdx = false;
                }
            }
            // 略微向下加速使后续移动更平滑
            else {
                boss.acc = boss.acc.scale(0.975).subtract(0, 0.025, 0);
            }
            // 不久后进入缩地阶段
            if (boss.indexAI >= 25) {
                boss.toState(STATE_WINDUP);
            }
            // 更新速度
            boss.updateVel();
            // 需要时增加一刻
            if (shouldIncreaseIdx)
                boss.indexAI ++;
        }
    };
    // 转个圈再撞过来
    public static final State<TestWormPart> STATE_WINDUP = new State<>() {
        @Override
        public void tick(TestWormPart boss) {
            LivingEntity target = boss.getTarget();

            // 脱战
            if (target == null) {
                boss.getParentMob().discard();
                return;
            }

            boolean shouldIncreaseIdx = true;
            // 没有下落到比玩家更低时，不要增加index
            if (boss.getY() > target.getY()) {
                shouldIncreaseIdx = false;
            }
            // 比玩家更低之后快速向玩家脚下移动（实际上不会真的到脚下）
            else {
                Vec3 targetLoc = target.position().subtract(0, 20, 0);
                boss.acc = ModUtils.getDirection(boss.position(), targetLoc, 0.35);
            }
            // 更快地获得向下的加速度
            boss.acc = boss.acc.scale(0.95).subtract(0, 0.05, 0);
            // 不久后进入冲刺阶段
            if (boss.indexAI >= 40) {
                boss.toState(STATE_RUSH);
            }
            // 更新速度
            boss.updateVel();
            // 需要时增加一刻
            if (shouldIncreaseIdx)
                boss.indexAI ++;
        }
    };
    private State<TestWormPart> AIState = STATE_SPIT;
    // 随机化初始indexAI防止吐口水过于同步
    int indexAI = level().random.nextInt(100);
    Vec3 pooledVel = Vec3.ZERO;
    Vec3 acc = Vec3.ZERO;

    public TestWormPart(EntityType<? extends TestWormPart> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.ARMOR)
                .add(Attributes.MOVEMENT_SPEED);
    }


    @Override
    public void toState(State newState) {
        this.AIState.leave(this);
        this.AIState = newState;
        this.AIState.enter(this);
        this.indexAI = 0;
    }

    @Override
    public void AI() {
        // tick
        if (! level().isClientSide()) {
            this.AIState.tick(this);
        }
    }

    // 根据池化“速度”（主要是方向信息）和加速度更新移动方向
    private void updateVel() {
        pooledVel = pooledVel.add(acc);
        if (pooledVel.lengthSqr() > 1.2 * 1.2) {
            pooledVel = pooledVel.normalize().scale(1.2);
        }
        if (pooledVel.lengthSqr() > 1e-5) {
            // 看看还有几节身体（包括自己）
            double totalTickSeg = 0;
            ArrayList<BaseWormPart<? extends AbstractWormEntity>> wormParts = getParentMob().getWormParts();
            for (int i = getSegmentIndex(); i < wormParts.size(); i ++) {
                if (wormParts.get(i).isAlive()) totalTickSeg ++;
                else break;
            }
            // 可适当调整此处以决定不同长度蠕虫的速度；最大速度的设计可以极大地减少奇特的AI行为并使AI微调更容易达到较为满意的结果
            // 第一个min：最大速度为2
            // 第二个min：6节即可达到最大速度
            setDeltaMovement(pooledVel.normalize().scale((0.75 *
                    Math.min(2, 3 /
                            Math.min(totalTickSeg / 10, 3d)))));
        }
    }

    /** TODO:吐魔唾液的方法；默认在触发时目标存在！ */
    private void spit() {
        // TODO: 平衡弹幕速度
        Vec3 projVel = ModUtils.getDirection(getEyePosition(), getTarget().getEyePosition(), 1);
        // TODO: he~tui
    }

    @Override
    protected void tickSegment() {
        super.tickSegment();

        AI();
        if (this.segmentType == SegmentType.HEAD) {
            ModUtils.updateEntityRotation(this, getDeltaMovement());
        }

        indexAI ++;
    }





    // GeoEntity

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> state.setAndContinue(RawAnimation.begin().thenLoop("fly"))));
    }

    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
