package org.confluence.mod.common.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModSoundEvents;
import org.mesdag.portlib.wrapper.world.entity.ai.attributes.PortAttributeModifier;

/// 逐步加速、越障并踩踏玩家的独角兽。
///
/// 独角兽不会像李小骨一样停下蓄力后锁定一次冲刺；它会在持续追逐期间逐渐提高速度，
/// 停下或失去目标后再快速减速，因此使用独立状态而不是复用通用冲锋循环。
public final class Unicorn extends BaseWarriorMonster {
    /**
     * 追逐期间每 tick 增加的移动速度，约两秒达到最高加成。
     */
    private static final double ACCELERATION_PER_TICK = 0.009;
    /**
     * 失去持续奔跑条件后每 tick 移除的速度加成。
     */
    private static final double DECELERATION_PER_TICK = 0.03;
    /**
     * 在基础移动速度之上允许叠加的最高冲锋速度。
     */
    private static final double MAX_SPEED_BONUS = 0.36;
    private double speedBonus;

    public Unicorn(EntityType<? extends Unicorn> type, Level level) {
        super(type, level, 0.0, LandAnimationProfile.NONE, LandSoundProfile.ROUTINE, 1.2, true);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;
        boolean accelerating = getTarget() != null && getTarget().isAlive() && getDeltaMovement().horizontalDistanceSqr() > 0.0025;
        double nextBonus = accelerating ? Math.min(MAX_SPEED_BONUS, speedBonus + ACCELERATION_PER_TICK)
                : Math.max(0.0, speedBonus - DECELERATION_PER_TICK);
        if (Math.abs(nextBonus - speedBonus) < 1.0E-6) return;
        speedBonus = nextBonus;
        var movementSpeed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) return;
        AttributeModifier acceleration = new PortAttributeModifier(Confluence.asResource("unicorn_pursuit_acceleration"), speedBonus, PortAttributeModifier.Operation.ADD_VALUE).unwrap();
        movementSpeed.removeModifier(acceleration.getId());
        if (speedBonus > 0.0) {
            movementSpeed.addTransientModifier(acceleration);
        }
        setSprinting(accelerating);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.UNICORN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.UNICORN_DEATH.get();
    }
}
