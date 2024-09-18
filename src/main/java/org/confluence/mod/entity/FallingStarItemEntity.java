package org.confluence.mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.misc.ModDamageTypes;
import org.confluence.mod.misc.ModSoundEvents;

public class FallingStarItemEntity extends ItemEntity {
    private boolean wasOnGround;

    public FallingStarItemEntity(EntityType<FallingStarItemEntity> entityType, Level level) {
        super(entityType, level);
        this.wasOnGround = false;
    }

    public FallingStarItemEntity(Level level, Vec3 pos) {
        this(ModEntities.FALLING_STAR_ITEM_ENTITY.get(), level);
        setPos(pos);
        setDeltaMovement(level.random.nextDouble(), -8.0, level.random.nextDouble());
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
                if (!wasOnGround) {
                    this.wasOnGround = true;
                    level().playSound(null, getX(), getY(), getZ(), ModSoundEvents.STAR_LANDS.get(), SoundSource.AMBIENT, 2.0F, 1.0F);
                }
            } else if (!wasOnGround && !level().getBlockState(getOnPos().below(6)).isAir()) {
                level().playSound(null, getX(), getY(), getZ(), ModSoundEvents.STAR.get(), SoundSource.AMBIENT, 2.0F, 1.0F);
            } else if (ProjectileUtil.getHitResultOnMoveVector(this, entity -> true) instanceof EntityHitResult entityHitResult) {
                entityHitResult.getEntity().hurt(ModDamageTypes.of(level(), ModDamageTypes.FALLING_STAR), 100);
                discard();
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("wasOnGround", wasOnGround);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.wasOnGround = pCompound.getBoolean("wasOnGround");
    }

    public static void summon(ServerLevel serverLevel) {
        if (serverLevel.dimension().equals(Level.OVERWORLD) && serverLevel.getDayTime() % 24000 > 12000 && serverLevel.getGameTime() % 600 == 0) {
            RandomSource random = serverLevel.random;
            for (ServerPlayer serverPlayer : serverLevel.players()) {
                int offsetX = (random.nextBoolean() ? 1 : -1) * random.nextInt(2);
                int offsetZ = (random.nextBoolean() ? 1 : -1) * random.nextInt(2);
                BlockPos pos = serverPlayer.getOnPos().offset(offsetX, 0, offsetZ).atY(256);
                if (serverLevel.isLoaded(pos)) {
                    serverLevel.addFreshEntity(new FallingStarItemEntity(serverLevel, pos.getCenter()));
                }
            }
        }
    }
}
