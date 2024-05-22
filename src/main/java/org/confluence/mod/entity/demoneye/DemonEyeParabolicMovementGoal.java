package org.confluence.mod.entity.demoneye;

public class DemonEyeParabolicMovementGoal extends DemonEyeMoveTargetGoal {
    private int steps; // 记录剩余步数
    private float tiltAngle; // 记录倾斜角度
    private boolean returning; // 标记是否正在返回

    DemonEyeParabolicMovementGoal(DemonEye demonEye) {
        super(demonEye);
        this.steps = 0;
        this.tiltAngle = 0.0F;
        this.returning = false;
    }

    @Override
    public boolean canUse() {
        return self.getTarget() == null || self.attackPhase != DemonEye.AttackPhase.CIRCLE;
    }

    @Override
    public void start() {
        this.steps = 5; // 设置步数为5
        this.tiltAngle = self.getRandom().nextFloat() * 0.3926991F; // 随机生成一个倾斜角度，约为π/8
        this.returning = false; // 初始时不处于返回状态
    }

    @Override
    public void tick() {
        if (steps > 0) {
            double deltaX = Math.cos(tiltAngle) * 0.1; // 根据倾斜角度计算水平位移
            double deltaZ = Math.sin(tiltAngle) * 0.1; // 根据倾斜角度计算垂直位移
            if (returning) {
                deltaX *= -1; // 如果正在返回，则水平位移取反
                deltaZ *= -1; // 如果正在返回，则垂直位移取反
            }

            if (touchingTarget() || self.level().getBlockState(self.blockPosition()).isCollisionShapeFullBlock(self.level(), self.blockPosition())) {
                this.returning = true; // 如果碰撞到目标或墙体，则开始返回
                this.tiltAngle = (float) (Math.PI - tiltAngle); // 更新倾斜角度为反向
            }

            if (self.getTarget() != null && self.getTarget().blockPosition().distSqr(self.blockPosition()) > 16) {
                this.returning = true; // 如果目标距离过远，则开始返回
                this.tiltAngle = (float) (Math.PI - tiltAngle); // 更新倾斜角度为反向
            }

            self.moveTargetPoint = self.moveTargetPoint.add(deltaX, 0, deltaZ); // 更新移动目标点
            this.steps--; // 步数减一
        }
    }
}
