package org.confluence.mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.confluence.mod.item.common.Materials;

public class FallingStarItemEntity extends ItemEntity {
    public FallingStarItemEntity(Level level, BlockPos pos) {
        super(level, pos.getX(), 256, pos.getZ(), Materials.FALLING_STAR.get().getDefaultInstance(), level.random.nextDouble(), -8, level.random.nextDouble());
        setUnlimitedLifetime();
        setNoPickUpDelay();
    }

    @Override
    public void tick() {
        if (level().isDay()) {
            discard();
        } else {
            super.tick();
            if (!onGround()) {
                HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> true);
                if (hitresult instanceof EntityHitResult entityHitResult) {
                    entityHitResult.getEntity().hurt(ConfluenceDamageTypes.of(level(), ConfluenceDamageTypes.FALLING_STAR), 100);
                }
            }
        }
    }
}
