package org.confluence.mod.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class BaseBombEntity extends ThrowableItemProjectile {
    private boolean isTouchingBlock = false;
    private int delay = 40;

    public BaseBombEntity(EntityType<? extends BaseBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BaseBombEntity(Level pLevel, LivingEntity pShooter) {
        super(EntityType.SNOWBALL, pShooter, pLevel);
    }

    public BaseBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityType.SNOWBALL, pX, pY, pZ, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particle = this.getParticle();

            for (int num = 0; num < 8; ++num) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }


    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide && pResult.getType() == HitResult.Type.BLOCK) {
            this.isTouchingBlock = true;
            this.setDeltaMovement(Vec3.ZERO);
        }
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isTouchingBlock) {
            RemovalReason removalReason = RemovalReason.KILLED;
            if (this.delay <= 0) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.BLOCK);
                this.remove(removalReason);
            } else {
                this.delay--;
            }
        }

        if (this.level().getBlockState(this.blockPosition()).getBlock() == Blocks.BEDROCK) {
            this.setDeltaMovement(Vec3.ZERO);
        }
    }
}
