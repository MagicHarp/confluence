package org.confluence.mod.common.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import org.confluence.lib.util.LibUtils;
import org.confluence.mod.common.entity.ai.bt.BTNode;
import org.confluence.mod.common.entity.ai.bt.BTRoot;
import org.confluence.mod.common.entity.ai.bt.leaf.DirectFloatingPursuitAction;
import org.confluence.mod.common.init.ModSoundEvents;

/// 会穿过地形持续追逐玩家的幻灵。
public class Wraith extends BaseFlyingMonster {

    public Wraith(EntityType<? extends Wraith> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return BaseFlyingMonster.createFlyingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.ATTACK_DAMAGE, 12.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected boolean mustSeePlayerTarget() {
        /// 幻灵自身可以穿过方块，目标筛选也必须使用相同语义；否则隔着一面墙就会
        /// 丢失玩家并切回游荡，形成“身体能穿墙、仇恨却不能穿墙”的行为断层。
        return false;
    }

    @Override
    protected BTRoot createBT() {
        return new BTRoot() {
            @Override
            protected BTNode createTree() {
                // 漂浮行为始终占用移动通道：无目标时只保留上下漂浮，
                // 有目标时直接追击。这里不能再回落到普通飞行怪的随机游荡。
                return new DirectFloatingPursuitAction(Wraith.this, 2.0);
            }
        };
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ROUTINE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.SOUL_DEATH.get();
    }

    @Override
    protected boolean hasPushableBody() {
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean damaged = super.doHurtTarget(target);
        if (damaged && target instanceof LivingEntity living && random.nextInt(8) == 0) {
            int duration = LibUtils.isMaster(level(), blockPosition()) ? 750 : LibUtils.isAtLeastExpert(level(), blockPosition()) ? 600 : 300;
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration), this);
        }
        return damaged;
    }

    /// 幻灵可以漂浮并穿过门洞。
    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanPassDoors(true);
        navigation.setCanFloat(true);
        return navigation;
    }
}
