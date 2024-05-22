package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DemonEye extends FlyingMob implements Enemy, VariantHolder<DemonEyeVariant> {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(DemonEye.class, EntityDataSerializers.INT);
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_VARIANT_ID, 0);
    }

    public @NotNull DemonEyeVariant getVariant() {
        return DemonEyeVariant.byId(entityData.get(DATA_VARIANT_ID));
    }

    public void setVariant(DemonEyeVariant pVariant) {
        entityData.set(DATA_VARIANT_ID, pVariant.id);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new DemonEyeParabolicMovementGoal(this));
        goalSelector.addGoal(1, new DemonEyeCircleAroundAnchorGoal(this));
        goalSelector.addGoal(2, new DemonEyeAttackGoal(this));
        goalSelector.addGoal(3, new DemonEyeSweepAttackGoal(this));
        targetSelector.addGoal(1, new DemonEyeAttackPlayerTargetGoal(this));
    }

    public enum AttackPhase {
        CIRCLE,
        SWOOP
    }
}

