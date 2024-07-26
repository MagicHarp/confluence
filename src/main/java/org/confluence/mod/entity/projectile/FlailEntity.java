package org.confluence.mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.flail.AbstractFlailItem;
import org.confluence.mod.util.IOriented;
import org.confluence.mod.util.OBB;

public class FlailEntity extends ChainingEntity implements IOriented {
    public long frameCount = 0;
    private AbstractFlailItem item;
    public static final EntityDataAccessor<Float> DATA_OFFSET = SynchedEntityData.defineId(FlailEntity.class, EntityDataSerializers.FLOAT);


    public FlailEntity(EntityType<FlailEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        setNoGravity(true);
        setXRot(-90);
    }

    public FlailEntity(EntityType<FlailEntity> pEntityType, Level pLevel, Entity owner, InteractionHand hand, AbstractFlailItem flailItem){
        this(pEntityType, pLevel);
        setOwner(owner);
        this.item = flailItem;
        float radians = (float) Math.toRadians(owner.getYRot());
        float offset = hand == InteractionHand.MAIN_HAND ? -0.6f : 0.6f;
        getEntityData().set(DATA_OFFSET,offset);
        setPos(owner.position().add(offset * Mth.cos(radians), 0, offset * Mth.sin(radians)));
    }

    @Override
    protected void defineSynchedData(){
        entityData.define(DATA_OFFSET, -0.6f);
    }

    @Override
    public void tick(){
        Entity owner = getOwner();
        if(owner == null || owner.isRemoved()){
            discard();
            return;
        }
        super.tick();
        float offset = getEntityData().get(DATA_OFFSET);
        float radians = (float) Math.toRadians(owner.getYRot());
        setPos(owner.position().add(offset * Mth.cos(radians), 0, offset * Mth.sin(radians)));
        refreshDimensions();
        if(!level().isClientSide()){
            OBB obb = getOrientedBoundingBox();
            AABB border = obb.getBorder();
            for(LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, border, EntitySelector.NO_SPECTATORS.and(e -> obb.collide(e.getBoundingBox())))){
                living.hurt(damageSources().mobAttack(owner instanceof LivingEntity lo ? lo : null), item.damage);
//                living.knockback();
                // TODO: 击退
            }
        }
    }

    // TODO: phase
    @Override
    public OBB getOrientedBoundingBox(){
        Vec3 pos = position();
        Entity owner = getOwner();
        if(owner == null){
            return new OBB(getBoundingBox());
        }
        float offset = getEntityData().get(DATA_OFFSET) / -2;
        float radians = (float) Math.toRadians(owner.getYRot());
        return new OBB(pos.add(offset * Mth.cos(radians), 1, offset * Mth.sin(radians)), 0.7, 2, 3.6, 0, owner.getYRot()).updateVertex();
    }

}
