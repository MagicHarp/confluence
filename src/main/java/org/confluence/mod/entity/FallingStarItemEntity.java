package org.confluence.mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
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
        setNeverPickUp();
    }

    @Override
    public void tick() {
        if (level().getDayTime() % 24000 < 12000) {
            discard();
        } else {
            super.tick();
            if (onGround()) {
                if (hasPickUpDelay()) setNoPickUpDelay();
            } else if (ProjectileUtil.getHitResultOnMoveVector(this, entity -> true) instanceof EntityHitResult entityHitResult) {
                entityHitResult.getEntity().hurt(ConfluenceDamageTypes.of(level(), ConfluenceDamageTypes.FALLING_STAR), 100);
                discard();
            }
        }
    }

    public static void summon(ServerLevel serverLevel) {
        if (serverLevel.dimension().equals(Level.OVERWORLD) && serverLevel.getDayTime() % 24000 > 12000 && serverLevel.getGameTime() % 600 == 0) {
            RandomSource random = serverLevel.random;
            for (ServerPlayer serverPlayer : serverLevel.players()) {
                int distance = serverLevel.getServer().getScaledTrackingDistance(1);
                int offsetX = (random.nextFloat() < 0.5F ? 1 : -1) * random.nextInt(distance);
                int offsetZ = (random.nextFloat() < 0.5F ? 1 : -1) * random.nextInt(distance);
                BlockPos pos = serverPlayer.getOnPos().offset(offsetX, 0, offsetZ).atY(256);
                if (serverLevel.isLoaded(pos)) {
                    serverLevel.addFreshEntity(new FallingStarItemEntity(serverLevel, pos.getCenter()));
                }
            }
        }
    }
}
