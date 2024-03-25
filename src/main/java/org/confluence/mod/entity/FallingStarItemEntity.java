package org.confluence.mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.common.Materials;

public class FallingStarItemEntity extends ItemEntity {
    public FallingStarItemEntity(EntityType<FallingStarItemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FallingStarItemEntity(Level level, Vec3 pos) {
        super(ModEntities.FALLING_STAR_ITEM_ENTITY.get(), level);
        setPos(pos);
        setDeltaMovement(level.random.nextDouble(), -8, level.random.nextDouble());
        setItem(Materials.FALLING_STAR.get().getDefaultInstance());
        this.lifespan = 12000;
        setNoPickUpDelay();
    }

    @Override
    public void tick() {
        if (level().getDayTime() < 12000L) {
            discard();
        } else {
            super.tick();
            if (!onGround() && ProjectileUtil.getHitResultOnMoveVector(this, entity -> true) instanceof EntityHitResult entityHitResult) {
                entityHitResult.getEntity().hurt(ConfluenceDamageTypes.of(level(), ConfluenceDamageTypes.FALLING_STAR), 100);
                discard();
            }
        }
    }
}
