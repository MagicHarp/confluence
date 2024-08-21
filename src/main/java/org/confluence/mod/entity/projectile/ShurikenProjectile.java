package org.confluence.mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ShurikenProjectile extends ThrowableProjectile {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(ShurikenProjectile.class, EntityDataSerializers.INT);
    private static int entityHurt;
    private static final float damage = 5.2F;
    private static final int timeExistence = 60;

    public ShurikenProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        entityHurt = 0;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_VARIANT_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= timeExistence)
            this.onHitBlock(new BlockHitResult(this.position(), Direction.UP, BlockPos.containing(this.position()), false));
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        entityHurt++;
        if (entityHurt >= 5) {
            if (this.level().random.nextFloat() <= 0.5F) {
                this.remove(RemovalReason.KILLED);
            } else {
                SimpleContainer inventory = new SimpleContainer(1);
                inventory.addItem(new ItemStack(ModItems.SHURIKEN.get(), 1));
                Containers.dropContents(this.level(), BlockPos.containing(this.position()), inventory);
                this.remove(RemovalReason.KILLED);
            }
        }
        Entity entity = pResult.getEntity();
        if (entity.hurt(damageSources().thrown(this, this.getOwner()), damage)) {
            LivingEntity living = (LivingEntity) entity;
            living.knockback(0.5F, Mth.sin(getYRot() * Mth.DEG_TO_RAD), -Mth.cos(getYRot() * Mth.DEG_TO_RAD));
            /*this.remove(RemovalReason.KILLED);*/
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (this.level().random.nextFloat() <= 0.5F) {
            this.remove(RemovalReason.KILLED);
        } else {
            SimpleContainer inventory = new SimpleContainer(1);
            inventory.addItem(new ItemStack(ModItems.SHURIKEN.get(), 1));
            Containers.dropContents(this.level(), BlockPos.containing(this.position()), inventory);
            this.remove(RemovalReason.KILLED);
        }
    }
}
