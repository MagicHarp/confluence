package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DemonEye extends FlyingMob implements Enemy, VariantHolder<DemonEyeVariant>, GeoEntity {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(DemonEye.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    public Vec3 moveTargetPoint;
    public AttackPhase attackPhase;
    public BlockPos anchorPoint;

    public static AttributeSupplier.Builder createAttributes() {
        return AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH)
            .add(Attributes.ATTACK_DAMAGE)
            .add(Attributes.ARMOR)
            .add(Attributes.MOVEMENT_SPEED);
    }

    public DemonEye(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
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
        AttributeMap attributeMap = getAttributes();
        attributeMap.getInstance(Attributes.MAX_HEALTH).setBaseValue(pVariant.health);
        attributeMap.getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(pVariant.damage);
        attributeMap.getInstance(Attributes.ARMOR).setBaseValue(pVariant.armor);
        attributeMap.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(pVariant.big ? 0.1 : 0.2);
        setHealth(getMaxHealth());
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new DemonEyeParabolicMovementGoal(this));
        goalSelector.addGoal(1, new DemonEyeCircleAroundAnchorGoal(this));
        goalSelector.addGoal(2, new DemonEyeAttackGoal(this));
        goalSelector.addGoal(3, new DemonEyeSweepAttackGoal(this));
        targetSelector.addGoal(1, new DemonEyeAttackPlayerTargetGoal(this));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> state.setAndContinue(RawAnimation.begin().thenLoop("fly"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }

    public enum AttackPhase {
        CIRCLE,
        SWOOP
    }
}

