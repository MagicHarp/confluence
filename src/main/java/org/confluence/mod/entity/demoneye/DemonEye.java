package org.confluence.mod.entity.demoneye;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.mixin.accessor.EntityAccessor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DemonEye extends Monster implements Enemy, VariantHolder<DemonEyeVariant>, GeoEntity {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(DemonEye.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    public Vec3 moveTargetPoint;
    public AttackPhase attackPhase;
    public BlockPos anchorPoint;
    public SurroundTargetGoal surroundTargetGoal;

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH)
            .add(Attributes.ATTACK_DAMAGE)
            .add(Attributes.ARMOR)
            .add(Attributes.MOVEMENT_SPEED);
    }

    public DemonEye(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveTargetPoint = Vec3.ZERO;
        this.xpReward = 5;
    }

    public static boolean checkDemonEyeSpawn(EntityType<? extends Mob> type, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (!(pLevel instanceof Level level)) {
            return false;
        }
        if (!checkMobSpawnRules(type, pLevel, pSpawnType, pPos, pRandom)) {
            return false;
        } else if (type == ModEntities.DEMON_EYE.get()) {
            int y = pPos.getY();
            boolean levelCon = y > 40 && y < 260 && level.isNight() && pLevel.canSeeSky(pPos);
            // 新月100%，其他80%
            return level.getMoonPhase() == 4 ? levelCon : level.random.nextInt(99) < 80;  // 从六分仪的翻译看的
        }
        return false;
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
        surroundTargetGoal = new SurroundTargetGoal(this);
        goalSelector.addGoal(0, surroundTargetGoal);
        goalSelector.addGoal(1, new WanderGoal(this));
        goalSelector.addGoal(2, new LeaveGoal(this));
//        goalSelector.addGoal(0, new DemonEyeParabolicMovementGoal(this));
//        goalSelector.addGoal(1, new DemonEyeCircleAroundAnchorGoal(this));
//        goalSelector.addGoal(2, new DemonEyeAttackGoal(this));
//        goalSelector.addGoal(3,new RandomStrollGoal(this,2,10));
//        goalSelector.addGoal(3, new DemonEyeSweepAttackGoal(this));
//        targetSelector.addGoal(1, new DemonEyeAttackPlayerTargetGoal(this));
//        goalSelector.addGoal(0,new RandomLookAroundGoal(this));
//        goalSelector.addGoal(1,new LookAtPlayerGoal(this, Player.class,6f,1));
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, @NotNull BlockState pState, @NotNull BlockPos pPos){
    }

    @Override
    public boolean isPushable(){
        return false;
    }

    @Override
    public void push(@NotNull Entity pEntity){
    }

    @Override
    protected void pushEntities(){
    }

    /** @author voila */
    public void move(@NotNull MoverType pType, @NotNull Vec3 motion){
        Vec3 collide = ((EntityAccessor) this).callCollide(motion);
        if(collide.x !=motion.x){
            motion = new Vec3(motion.x < 0 ? 0.22 : -0.22, motion.y, motion.z);
        }
        if(collide.y !=motion.y){
            boolean downward = motion.y < 0;
            motion=new Vec3(motion.x,downward? Mth.clamp(-motion.y,0.1,0.22):Mth.clamp(-motion.y,-0.22,-0.1), motion.z);
            if(surroundTargetGoal.targetPos != null && getTarget() != null){
                surroundTargetGoal.targetPos = surroundTargetGoal.targetPos.with(Direction.Axis.Y, getTarget().position().y + (downward ? 2 : -1));
            }
        }
        if(collide.z !=motion.z){
            motion=new Vec3(motion.x, motion.y, motion.z < 0 ? 0.3 : -0.3);
        }
        setDeltaMovement(motion);
        super.move(pType, motion);
    }

    @Override
    public void tick(){
        // TODO: 仇恨值
        Vec3 pos = position();
        setTarget(level().getNearestPlayer(pos.x, pos.y, pos.z, 40, true));
        super.tick();
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ){
//        Confluence.LOGGER.info("{}",pStrength);
        super.knockback(pStrength, pX, pZ);
    }

    @Override
    public void onAddedToWorld(){
        super.onAddedToWorld();
        setNoGravity(true);
    }

    @Override
    public void setXRot(float pXRot){
        if(pXRot == 0 || pXRot == getXRot()){
            return;
        }
        xRotO = getXRot();
        super.setXRot(pXRot);
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

