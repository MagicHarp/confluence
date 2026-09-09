package org.confluence.mod.common.summon.ground;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.api.summon.SummonTargetCache;
import org.confluence.mod.common.summon.SummonInstance;
import org.confluence.mod.common.summon.SummonPose;
import org.confluence.mod.common.summon.SummonStats;
import org.mesdag.portlib.wrapper.world.entity.ai.attributes.PortAttributeModifier;

import java.util.EnumSet;

/// 使用原版铁傀儡实体驱动移动、寻路、碰撞和攻击动画的召唤物。
public final class IronGolemSummon extends SummonInstance {
    public static final int SLOT_COST = 1;
    public static final float BASE_DAMAGE = 8.0F;
    private static final double SEARCH_RANGE = 32.0;
    private SummonedIronGolem entity;

    public IronGolemSummon(ServerPlayer owner, int slotCost, SummonStats stats, SummonPose initialPose) {
        super(Confluence.asResource("i_32_iron_golem"), owner, slotCost, stats, initialPose);
    }

    @Override
    protected LivingEntity findTarget() {
        Vec3 origin = entity == null ? position() : entity.position();
        return SummonTargetCache.acquire(owner().serverLevel(), owner(), uuid(), origin, SEARCH_RANGE);
    }

    @Override
    protected void beforeGoalTick() {
        if (ensureEntity()) entity.setTarget(target());
    }

    @Override
    protected void afterGoalTick() {
        if (entity != null && !entity.isRemoved()) {
            advanceTo(new SummonPose(entity.position(), entity.getYRot(), entity.getXRot(), 0.0F));
        }
    }

    @Override
    protected boolean usesOwnerRecovery() {
        return false;
    }

    @Override
    protected void onRemoved() {
        super.onRemoved();
        if (entity != null) {
            entity.discard();
            entity = null;
        }
    }

    private boolean ensureEntity() {
        if (entity != null && entity.level() != owner().level()) {
            entity.discard();
            entity = null;
        }
        if (entity != null) {
            if (entity.isAlive() && !entity.isRemoved()) return true;
            remove();
            return false;
        }
        entity = new SummonedIronGolem(owner().serverLevel(), this);
        entity.moveTo(position().x, position().y, position().z, currentPose().yaw(), currentPose().pitch());
        if (!owner().serverLevel().addFreshEntity(entity)) {
            entity = null;
            remove();
            return false;
        }
        return true;
    }

    private static final class SummonedIronGolem extends IronGolem {
        private static final ResourceLocation SPEED_MODIFIER_ID = Confluence.asResource("summon_iron_golem_speed");
        private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(PortAttributeModifier.rl2uuid(SPEED_MODIFIER_ID), SPEED_MODIFIER_ID.getPath(), 0.2, AttributeModifier.Operation.MULTIPLY_BASE);
        private final IronGolemSummon summon;

        private SummonedIronGolem(ServerLevel level, IronGolemSummon summon) {
            super(EntityType.IRON_GOLEM, level);
            this.summon = summon;
            if (!getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER)) {
                getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER);
            }
        }

        @Override
        protected void registerGoals() {
            goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
            goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
            goalSelector.addGoal(6, new FollowSummonOwnerGoal(this));
            goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
            goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        }

        @Override
        public boolean doHurtTarget(Entity target) {
            level().broadcastEntityEvent(this, (byte) 4);
            playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
            if (!(target instanceof LivingEntity living) || !summon.hurtTarget(living, 1.0F))
                return false;
            double resistance = living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            living.setDeltaMovement(living.getDeltaMovement().add(
                    0.0, 0.4 * Math.max(0.0, 1.0 - resistance), 0.0));
            living.hasImpulse = true;
            return true;
        }

        @Override
        public boolean canAttack(LivingEntity target) {
            return target != summon.owner() && super.canAttack(target);
        }

        @Override
        public boolean hurt(DamageSource source, float amount) {
            return source.is(DamageTypes.GENERIC_KILL) && super.hurt(source, amount);
        }

        @Override
        public boolean isPickable() {
            return false;
        }

        @Override
        public boolean shouldBeSaved() {
            return false;
        }
    }

    private static final class FollowSummonOwnerGoal extends Goal {
        private final SummonedIronGolem golem;
        private int repathTicks;

        private FollowSummonOwnerGoal(SummonedIronGolem golem) {
            this.golem = golem;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return golem.getTarget() == null && golem.distanceToSqr(golem.summon.owner()) >= 32.0 * 32.0;
        }

        @Override
        public boolean canContinueToUse() {
            return golem.getTarget() == null && !golem.getNavigation().isDone() && golem.distanceToSqr(golem.summon.owner()) > 2.0 * 2.0;
        }

        @Override
        public void start() {
            repathTicks = 0;
        }

        @Override
        public void stop() {
            golem.getNavigation().stop();
        }

        @Override
        public void tick() {
            ServerPlayer owner = golem.summon.owner();
            golem.getLookControl().setLookAt(owner, 10.0F, golem.getMaxHeadXRot());
            if (--repathTicks > 0) return;
            repathTicks = adjustedTickDelay(10);
            if (golem.distanceToSqr(owner) >= 40.0 * 40.0) teleportNearOwner(owner);
            else golem.getNavigation().moveTo(owner, 1.0);
        }

        private void teleportNearOwner(ServerPlayer owner) {
            BlockPos origin = owner.blockPosition();
            for (int attempt = 0; attempt < 10; attempt++) {
                int x = golem.getRandom().nextIntBetweenInclusive(-3, 3);
                int z = golem.getRandom().nextIntBetweenInclusive(-3, 3);
                if (Math.abs(x) < 2 && Math.abs(z) < 2) continue;
                BlockPos target = origin.offset(x, golem.getRandom().nextIntBetweenInclusive(-1, 1), z);
                if (WalkNodeEvaluator.getBlockPathTypeStatic(golem.level(), target.mutable()) != BlockPathTypes.WALKABLE || golem.level().getBlockState(target.below()).getBlock() instanceof LeavesBlock)
                    continue;
                BlockPos offset = target.subtract(golem.blockPosition());
                if (!golem.level().noCollision(golem, golem.getBoundingBox().move(offset)))
                    continue;
                golem.moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5,
                        golem.getYRot(), golem.getXRot());
                golem.getNavigation().stop();
                return;
            }
        }
    }
}
