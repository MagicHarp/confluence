package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DemonEye extends FlyingMob implements Enemy, VariantHolder<DemonEyeVariant> {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(DemonEye.class, EntityDataSerializers.INT);
    public Vec3 moveTargetPoint;
    public AttackPhase attackPhase;
    public BlockPos anchorPoint;

    public static AttributeSupplier.Builder createAttributes() {
        return FlyingMob.createLivingAttributes()
            .add(Attributes.MAX_HEALTH)
            .add(Attributes.ATTACK_DAMAGE)
            .add(Attributes.ARMOR)
            .add(Attributes.MOVEMENT_SPEED, 0.25);
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

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        setVariant(DemonEyeVariant.random(pLevel.getRandom()));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public enum AttackPhase {
        CIRCLE,
        SWOOP
    }
}

