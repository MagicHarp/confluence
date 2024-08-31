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

public class EaterOfWorldPart extends BaseWormPart<EaterOfWorldEntity> implements GeoEntity, IBossFSM {
    // 吐口水间隔
    public static final int SPIT_INTERVAL = 20;
    // 口水速度
    public static final double SPIT_SPEED = 1d;
    // 冲刺时加速度大小
    public static final double DASH_ACCELERATION_MAG = 0.5d;
    // 冲刺刚结束时向下移动的效率
    public static final double GRADUAL_DESCEND_FACTOR = 0.025d;
    // 停止冲刺变向的距离
    public static final double DASH_END_DISTANCE = 8d;
    // 冲刺变向结束后多少刻从过渡期转到下一次冲刺的准备期
    public static final int DASH_HOLD_DURATION = 25;
    // 准备下一次冲刺前加速度大小
    public static final double WINDUP_ACCELERATION_MAG = 0.35d;
    // 准备下一次冲刺前向下移动的效率
    public static final double AGGRESSIVE_DESCEND_FACTOR = 0.05d;
    // 准备下一次冲刺的时长
    public static final int DASH_WINDUP_DURATION = 40;

    // 吐口水；所有体节都从这里开始，头节会改为冲撞行为模式
    public static final State<EaterOfWorldPart> STATE_SPIT = new State<>() {
        @Override
        public void tick(EaterOfWorldPart boss) {
            if (boss.getSegmentIndex() < 2)
                ModUtils.testMessage(boss.getSegmentIndex() + ", " + boss.level().isClientSide + ", " + boss.getDeltaMovement() + boss.position());
            // 如果变成头的话直接开创
            if (boss.getSegmentType() == SegmentType.HEAD) {
                boss.toState(STATE_RUSH);
                return;
            }
            // TODO: 平衡一下发射间隔
            int shootInterval = SPIT_INTERVAL;
            if (boss.indexAI % shootInterval == 0) {
                boss.spit();
            }
            boss.indexAI ++;
        }
    };
    // 冲向玩家
    public static final State<EaterOfWorldPart> STATE_RUSH = new State<>() {
        @Override
        public void tick(EaterOfWorldPart boss) {
            LivingEntity target = boss.getParentMob().target;

            ModUtils.testMessage(boss.getSegmentIndex() + ", " + boss.level().isClientSide + ", " + boss.getDeltaMovement() + boss.position());

            // 注：indexAI = 0时为冲刺，后续则为过渡时期

            double distSqr = boss.position().distanceToSqr(target.position());
            // 速度
            boolean shouldIncreaseIdx = true;
            // 还没靠近过玩家，冲刺，冲刺，冲！冲！
            if (boss.indexAI == 0) {
                // 在足够接近前不要停止冲刺
                if (distSqr > DASH_END_DISTANCE * DASH_END_DISTANCE) {
                    Vec3 targetLoc = target.getEyePosition();
                    boss.acc = ModUtils.getDirection(boss.position(), targetLoc, DASH_ACCELERATION_MAG);
                    shouldIncreaseIdx = false;
                }
            }
            // 冲刺结束后略微向下加速
            else {
                boss.acc = boss.acc.scale(1 - GRADUAL_DESCEND_FACTOR).subtract(0, GRADUAL_DESCEND_FACTOR, 0);
            }
            // 不久后进入缩地阶段
            if (boss.indexAI >= DASH_HOLD_DURATION) {
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
    public static final State<EaterOfWorldPart> STATE_WINDUP = new State<>() {
        @Override
        public void tick(EaterOfWorldPart boss) {
            LivingEntity target = boss.getParentMob().target;

            boolean shouldIncreaseIdx = true;
            // 没有下落到比玩家更低时，不要增加index
            if (boss.getY() > target.getY()) {
                shouldIncreaseIdx = false;
            }
            // 比玩家更低之后快速向玩家脚下移动（实际上不会真的到脚下）
            else {
                Vec3 targetLoc = target.position().subtract(0, 20, 0);
                boss.acc = ModUtils.getDirection(boss.position(), targetLoc, WINDUP_ACCELERATION_MAG);
            }
            // 更快地获得向下的加速度
            boss.acc = boss.acc.scale(1 - AGGRESSIVE_DESCEND_FACTOR).subtract(0, AGGRESSIVE_DESCEND_FACTOR, 0);
            // 不久后进入冲刺阶段
            if (boss.indexAI >= DASH_WINDUP_DURATION) {
                boss.toState(STATE_RUSH);
            }
            // 更新速度
            boss.updateVel();
            // 需要时增加一刻
            if (shouldIncreaseIdx)
                boss.indexAI ++;
        }
    };
    private State<EaterOfWorldPart> AIState = STATE_SPIT;
    // 随机化初始indexAI防止吐口水过于同步
    int indexAI = level().random.nextInt(SPIT_INTERVAL);
    Vec3 pooledVel = Vec3.ZERO;
    Vec3 acc = Vec3.ZERO;

    public EaterOfWorldPart(EntityType<? extends EaterOfWorldPart> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // 体节不要因为距离消失
    @Override
    public boolean requiresCustomPersistence() {
        return true;
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
//        if (! level().isClientSide()) {
            // 没有目标：速度减缓
            if (getParentMob().target == null) {
                if (segmentType == SegmentType.HEAD)
                    setDeltaMovement(getDeltaMovement().scale(0.5));
            }
            // 有目标：tick
            else {
                this.AIState.tick(this);
            }
//        }
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
        Vec3 projVel = ModUtils.getDirection(getEyePosition(), getParentMob().target.getEyePosition(), SPIT_SPEED);
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

//        if (!level().isClientSide() && getSegmentIndex() < 5)
//            ModUtils.testMessage(level(), getSegmentIndex() + ", " + getDeltaMovement() + "|||" + position());
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
