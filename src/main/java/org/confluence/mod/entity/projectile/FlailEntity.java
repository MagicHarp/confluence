package org.confluence.mod.entity.projectile;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.util.IOriented;
import org.confluence.mod.util.OBB;

public class FlailEntity extends ChainingEntity implements IOriented {
    public long frameCount = 0;

    public FlailEntity(EntityType<FlailEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        setNoGravity(true);
        setXRot(-90);
    }

    public FlailEntity(EntityType<FlailEntity> pEntityType, Level pLevel, Entity owner){
        this(pEntityType, pLevel);
        setOwner(owner);
    }

    @Override
    protected void defineSynchedData(){
    }

    @Override
    public void tick(){
        Entity owner = getOwner();
        if(owner == null || owner.isRemoved()){
            discard();
            return;
        }
        super.tick();
        float radians = (float) Math.toRadians(owner.getYRot());
        setPos(owner.position().add(-0.6 * Mth.cos(radians), 0, -0.6 * Mth.sin(radians)));
        refreshDimensions();
        if(!level().isClientSide()){
            OBB obb = getOrientedBoundingBox();
            AABB border = obb.getBorder();
            for(LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, border, EntitySelector.NO_SPECTATORS.and(e -> obb.collide(e.getBoundingBox())))){
                living.hurt(damageSources().mobAttack(owner instanceof LivingEntity lo ? lo : null), 1); // TODO: 伤害数值
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
        float radians = (float) Math.toRadians(owner.getYRot());
        return new OBB(pos.add(0.3 * Mth.cos(radians), 1, 0.3 * Mth.sin(radians)), 0.7, 2, 3.6, 0, owner.getYRot()).updateVertex();
    }
}
