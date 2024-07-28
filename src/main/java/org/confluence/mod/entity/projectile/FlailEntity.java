package org.confluence.mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.flail.AbstractFlailItem;
import org.confluence.mod.mixinauxiliary.IPlayer;
import org.confluence.mod.util.IOriented;
import org.confluence.mod.util.OBB;
import org.jetbrains.annotations.NotNull;

public class FlailEntity extends ChainingEntity implements IOriented {
    public long frameCount = 0;
    private final AbstractFlailItem item;
    private InteractionHand hand;
    private Vec3 direction;

    public static final EntityDataAccessor<Float> DATA_OFFSET = SynchedEntityData.defineId(FlailEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(FlailEntity.class, EntityDataSerializers.INT);

    public static final int PHASE_SPIN = 0;
    public static final int PHASE_THROWN = 1;
    public static final int PHASE_RETRACT = 2;
    public static final int PHASE_FORCE_RETRACT = 3;
    public static final int PHASE_STAY = 4;

    public FlailEntity(Level pLevel, AbstractFlailItem flailItem){
        super(ModEntities.FLAIL.get(), pLevel);
        setNoGravity(true);
        noPhysics = true;
        setXRot(-90);
        this.item = flailItem;
    }

    public FlailEntity(Level pLevel, Entity owner, InteractionHand hand, AbstractFlailItem flailItem){
        this(pLevel, flailItem);
        setOwner(owner);
        float radians = (float) Math.toRadians(owner.getYRot());
        this.hand = hand;
        float offset = hand == InteractionHand.MAIN_HAND ? -0.6f : 0.6f;
        getEntityData().set(DATA_OFFSET, offset);
        setPos(owner.position().add(offset * Mth.cos(radians), 0, offset * Mth.sin(radians)));
    }

    @Override
    protected void defineSynchedData(){
        entityData.define(DATA_OFFSET, -0.6f);
        entityData.define(DATA_PHASE, PHASE_SPIN);
    }

    @Override
    public void tick(){
        Entity owner = getOwner();
        int phase = getPhase();
        if(owner == null || owner.isRemoved()  // 没有owner
            || (!level().isClientSide() && item == null)  // 没有物品
            || !level().isClientSide() && owner instanceof LivingEntity living && living.getItemInHand(hand).getItem() != item   // 切换物品
            || position().distanceTo(owner.position()) >= 30){  //距离太远，自动消失的距离是固定的 TODO: 调整数值
            discard();
            if(owner instanceof IPlayer fp){
                fp.confluence$flailEntity((FlailEntity) null);
            }
            return;
        }
        super.tick();
        move(MoverType.SELF,getDeltaMovement());
        switch(phase){
            case PHASE_SPIN -> {
                float offset = getEntityData().get(DATA_OFFSET);
                float radians = (float) Math.toRadians(owner.getYRot());
                setPos(owner.position().add(offset * Mth.cos(radians), 0, offset * Mth.sin(radians)));
            }
            case PHASE_THROWN -> {
                // TODO: 反弹
                if(direction == null){
                    direction = Vec3.directionFromRotation(owner.getXRot() - 3, owner.getYRot() - 3).scale(3); // TODO: 发射速度，近战速度加成
                }
                setDeltaMovement(direction.add(owner.getDeltaMovement()));
                noPhysics = false;
                if(position().distanceTo(owner.position()) > 16){  // TODO: 抛出时间固定，时间决定速度和距离
                    setPhase(PHASE_RETRACT);
                }
            }
            case PHASE_STAY -> {
                noPhysics = false;
                setNoGravity(false);
                setDeltaMovement(0, -0.2, 0);
            }
            case PHASE_RETRACT, PHASE_FORCE_RETRACT -> {
                noPhysics = true;
                setNoGravity(true);
                setDeltaMovement(owner.position().add(0,1,0).subtract(position()).normalize().scale(3)); // TODO: 时间决定速度
            }
        }
        refreshDimensions();
        if(!level().isClientSide()){
            OBB obb = getOrientedBoundingBox();
            AABB border = obb.getBorder();
            for(LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, border, EntitySelector.NO_SPECTATORS.and(e -> obb.inflate(0.1).collide(e.getBoundingBox(),getDeltaMovement(),e.getDeltaMovement())))){
                if(living == owner){
                    if(phase == PHASE_RETRACT || phase == PHASE_FORCE_RETRACT){
                        if(owner instanceof IPlayer fp){
                            fp.confluence$flailEntity((FlailEntity) null);
                        }
                        discard();
                    }
                    return;
                }
                living.hurt(damageSources().mobAttack(owner instanceof LivingEntity lo ? lo : null), item.damage);
//                living.knockback();
                // TODO: 击退
            }
        }
    }

    @Override
    public void move(MoverType pType, Vec3 pPos){
        super.move(pType, pPos);
    }

    public int getPhase(){
        return getEntityData().get(DATA_PHASE);
    }

    public void setPhase(int phase){
        if(phase < PHASE_SPIN || phase > PHASE_STAY){
            throw new IllegalArgumentException("Invalid phase");
        }
        if(phase == PHASE_FORCE_RETRACT){
            Confluence.debugMethod();
        }
        getEntityData().set(DATA_PHASE, phase);
    }

    @Override
    @NotNull
    public EntityDimensions getDimensions(@NotNull Pose pPose){
        if(getPhase() == PHASE_SPIN){
            return super.getDimensions(pPose);
        }else{
            return EntityDimensions.fixed(0.5f, 0.5f);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult){
        Confluence.LOGGER.info("{}", pResult);
        super.onHitBlock(pResult);
    }

    @Override
    public OBB getOrientedBoundingBox(){
        Vec3 pos = position();
        Entity owner = getOwner();
        if(owner == null || getEntityData().get(DATA_PHASE) != PHASE_SPIN){
            return new OBB(getBoundingBox());
        }
        float offset = getEntityData().get(DATA_OFFSET) / -2;
        float radians = (float) Math.toRadians(owner.getYRot());
        return new OBB(pos.add(offset * Mth.cos(radians), 1, offset * Mth.sin(radians)), 0.7, 2, 3.6, 0, owner.getYRot()).updateVertex();
    }

}
