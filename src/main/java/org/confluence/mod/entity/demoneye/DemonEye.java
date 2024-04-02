package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class DemonEye extends FlyingMob implements Enemy {
    Vec3 moveTargetPoint;
    AttackPhase attackPhase;
    BlockPos anchorPoint;
    public static AttributeSupplier.Builder createAttributes() {
        return FlyingMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 47f)
                .add(Attributes.ATTACK_DAMAGE, 14f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f);
    }

    public DemonEye(EntityType<? extends FlyingMob> p_20806_, Level p_20807_) {
        super(p_20806_, p_20807_);
        this.moveTargetPoint = Vec3.ZERO;
        this.xpReward = 5;
    }

    private static enum AttackPhase {
        CIRCLE,
        SWOOP;

        private AttackPhase() {
        }
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new DemonEyeParabolicMovementGoal());
        this.goalSelector.addGoal(1, new DemonEyeCircleAroundAnchorGoal());
        this.goalSelector.addGoal(2, new DemonEyeAttakGoal());
        this.goalSelector.addGoal(3, new DemonEyeSweepAttackGoal());
        this.targetSelector.addGoal(1, new DemonEyeAttackPlayerTargetGoal());
    }

    public class DemonEyeAttakGoal extends Goal {
        private int nextSweepTick;

        DemonEyeAttakGoal() {
        }

        @Override
        public boolean canUse() {
            LivingEntity $$0 = DemonEye.this.getTarget();
            return $$0 != null && DemonEye.this.canAttack($$0, TargetingConditions.DEFAULT);
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(8);
            DemonEye.this.attackPhase = AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            DemonEye.this.anchorPoint = DemonEye.this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, DemonEye.this.anchorPoint).above(10 + DemonEye.this.random.nextInt(20));
        }

        public void tick() {
            if (DemonEye.this.attackPhase == AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    DemonEye.this.attackPhase = AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay(8);
                }
            }
        }

        private void setAnchorAboveTarget() {
            DemonEye.this.anchorPoint = DemonEye.this.getTarget().blockPosition().above(20 + DemonEye.this.random.nextInt(10));
            if (DemonEye.this.anchorPoint.getY() < DemonEye.this.level().getHeight()) {
                DemonEye.this.anchorPoint = new BlockPos(DemonEye.this.anchorPoint.getX(), DemonEye.this.level().getSeaLevel() + 1, DemonEye.this.anchorPoint.getZ());
            }
        }
    }
    public class DemonEyeParabolicMovementGoal extends DemonEyeMoveTargetGoal {
        private int steps; // 记录剩余步数
        private float tiltAngle; // 记录倾斜角度
        private boolean returning; // 标记是否正在返回

        DemonEyeParabolicMovementGoal() {
            super();
            this.steps = 0;
            this.tiltAngle = 0.0F;
            this.returning = false;
        }

        @Override
        public boolean canUse() {
            return DemonEye.this.getTarget() == null || DemonEye.this.attackPhase != AttackPhase.CIRCLE;
        }

        @Override
        public void start() {
            this.steps = 5; // 设置步数为5
            this.tiltAngle = DemonEye.this.random.nextFloat() * 0.3926991F; // 随机生成一个倾斜角度，约为π/8
            this.returning = false; // 初始时不处于返回状态
        }

        @Override
        public void tick() {
            if (this.steps > 0) {
                double deltaX = Math.cos(this.tiltAngle) * 0.1; // 根据倾斜角度计算水平位移
                double deltaZ = Math.sin(this.tiltAngle) * 0.1; // 根据倾斜角度计算垂直位移
                if (this.returning) {
                    deltaX *= -1; // 如果正在返回，则水平位移取反
                    deltaZ *= -1; // 如果正在返回，则垂直位移取反
                }

                if (this.touchingTarget() || DemonEye.this.level().getBlockState(DemonEye.this.blockPosition()).isCollisionShapeFullBlock(DemonEye.this.level(), DemonEye.this.blockPosition())) {
                    this.returning = true; // 如果碰撞到目标或墙体，则开始返回
                    this.tiltAngle = (float) (Math.PI - this.tiltAngle); // 更新倾斜角度为反向
                }

                if (DemonEye.this.getTarget() != null && DemonEye.this.getTarget().blockPosition().distSqr(DemonEye.this.blockPosition()) > 16) {
                    this.returning = true; // 如果目标距离过远，则开始返回
                    this.tiltAngle = (float) (Math.PI - this.tiltAngle); // 更新倾斜角度为反向
                }

                DemonEye.this.moveTargetPoint = DemonEye.this.moveTargetPoint.add(deltaX, 0, deltaZ); // 更新移动目标点
                this.steps--; // 步数减一
            }
        }
    }


    public class DemonEyeCircleAroundAnchorGoal extends DemonEyeMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        DemonEyeCircleAroundAnchorGoal() {
            super();
        }

        @Override
        public boolean canUse() {
            return DemonEye.this.getTarget() == null || DemonEye.this.attackPhase != AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + DemonEye.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + DemonEye.this.random.nextFloat() * 9.0F;
            this.clockwise = DemonEye.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        public void tick() {
            if (DemonEye.this.random.nextInt(this.adjustedTickDelay(359)) == 0) {
                this.height = -4.0F + DemonEye.this.random.nextFloat() * 9.0F;
            }
            if (DemonEye.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (DemonEye.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = DemonEye.this.random.nextFloat() * 2.0F * 3.1415927F;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (DemonEye.this.moveTargetPoint.y < DemonEye.this.getY() && !DemonEye.this.level().isEmptyBlock(DemonEye.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (DemonEye.this.moveTargetPoint.y > DemonEye.this.getY() && !DemonEye.this.level().isEmptyBlock(DemonEye.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }
        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(DemonEye.this.anchorPoint)) {
                DemonEye.this.anchorPoint = DemonEye.this.blockPosition();
            }
            this.angle += this.clockwise * 15.0F * 0.017453292F;
            DemonEye.this.moveTargetPoint = Vec3.atLowerCornerOf(DemonEye.this.anchorPoint).add((double) (this.distance * Mth.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * Mth.sin(this.angle)));
        }
    }

    public class DemonEyeAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);
        private int nextScanTick = reducedTickDelay(20);

        DemonEyeAttackPlayerTargetGoal() {
        }

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> $$0 = DemonEye.this.level().getNearbyPlayers(this.attackTargeting, DemonEye.this, DemonEye.this.getBoundingBox().inflate(16.0, 64.0, 16.0));
                if (!$$0.isEmpty()) {
                    $$0.sort(Comparator.comparing(Entity::getY));
                    Iterator<Player> var2 = $$0.iterator();

                    while (var2.hasNext()) {
                        Player $$1 = var2.next();
                        if (DemonEye.this.canAttack($$1, TargetingConditions.DEFAULT)) {
                            DemonEye.this.setTarget($$1);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public boolean canContinueToUse() {
            LivingEntity $$0 = DemonEye.this.getTarget();
            return $$0 != null ? DemonEye.this.canAttack($$0, TargetingConditions.DEFAULT) : false;
        }
    }

    class DemonEyeSweepAttackGoal extends Goal {
        DemonEyeSweepAttackGoal() {
            super();
        }

        public boolean canUse() {
            return DemonEye.this.getTarget() != null && DemonEye.this.attackPhase == DemonEye.AttackPhase.SWOOP;
            }
        }
    abstract class DemonEyeMoveTargetGoal extends Goal {
        public DemonEyeMoveTargetGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return DemonEye.this.moveTargetPoint.distanceToSqr(DemonEye.this.getX(), DemonEye.this.getY(), DemonEye.this.getZ()) < 4.0;
        }
    }
}

