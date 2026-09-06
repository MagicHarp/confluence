package org.confluence.mod.common.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.confluence.mod.common.data.map.CreatureDefinition;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.composite.SelectorNode;
import org.confluence.mod.common.entity.ai.bt.composite.SequenceNode;
import org.confluence.mod.common.entity.ai.bt.condition.HasTargetCondition;
import org.confluence.mod.common.entity.ai.bt.leaf.LookForwardWanderFlyAction;
import org.confluence.mod.common.entity.ai.bt.leaf.StraightFlyingPursuitAction;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

/// 飞鱼与游荡眼球怪鱼共用的飞鱼 AI 实现。
///
/// 两者都持续直追玩家，区别仅是惯性、最高速度和转向速度，因此使用不可变追击参数表达，
/// 不复用带有冲刺周期的通用飞行怪模板。
public final class FlyingFishMonster extends BaseFlyingMonster {
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("fly");
    private final PursuitProfile pursuitProfile;
    private final double wanderSpeed;

    public FlyingFishMonster(EntityType<? extends FlyingFishMonster> type, Level level, PursuitProfile pursuitProfile, double wanderSpeed) {
        super(type, level);
        if (!Double.isFinite(wanderSpeed) || wanderSpeed <= 0.0) {
            throw new IllegalArgumentException("Wander speed must be finite and positive");
        }
        this.pursuitProfile = pursuitProfile;
        this.wanderSpeed = wanderSpeed;
        setDiscardFriction(true);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return BaseFlyingMonster.createFlyingAttributes();
    }

    @Override
    protected boolean mustSeePlayerTarget() {
        return true;
    }

    @Override
    protected BTRoot createBT() {
        CreatureDefinition.BehaviorOverrides behavior = creatureDefinition().behavior();
        PursuitProfile profile = pursuitProfile.withMaxSpeed(behavior.chargeSpeedOr(pursuitProfile.maxSpeed()));
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                return SelectorNode.of(
                        SequenceNode.of(
                                new HasTargetCondition(FlyingFishMonster.this),
                                new StraightFlyingPursuitAction(
                                        FlyingFishMonster.this,
                                        profile.friction(),
                                        profile.maxSpeed(),
                                        profile.acceleration(),
                                        profile.turnSpeedDegrees())),
                        new LookForwardWanderFlyAction(FlyingFishMonster.this, behavior.wanderSpeedOr(wanderSpeed), 0.0F));
            }
        };
    }

    @Override
    protected double contactAttackInflation() {
        return 0.0;
    }

    @Override
    protected int contactDetectionInterval() {
        return 10;
    }

    @Override
    protected int contactAttackInterval() {
        return 20;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Fly", 0, state -> state.setAndContinue(FLY)));
    }

    /// 一组只描述连续直追物理的数据。
    public record PursuitProfile(double friction, double maxSpeed, double acceleration,
                                 double turnSpeedDegrees) {
        public PursuitProfile {
            if (!Double.isFinite(friction) || friction < 0.0 || friction > 1.0) {
                throw new IllegalArgumentException("Pursuit friction must be within [0, 1]");
            }
            if (!Double.isFinite(maxSpeed) || maxSpeed <= 0.0 || !Double.isFinite(acceleration) || acceleration <= 0.0) {
                throw new IllegalArgumentException("Pursuit speed and acceleration must be finite and positive");
            }
            if (!Double.isFinite(turnSpeedDegrees) || turnSpeedDegrees <= 0.0 || turnSpeedDegrees > 180.0) {
                throw new IllegalArgumentException("Pursuit turn speed must be finite and within (0, 180]");
            }
        }

        PursuitProfile withMaxSpeed(double value) {
            return value == maxSpeed ? this : new PursuitProfile(friction, value, acceleration, turnSpeedDegrees);
        }
    }
}
