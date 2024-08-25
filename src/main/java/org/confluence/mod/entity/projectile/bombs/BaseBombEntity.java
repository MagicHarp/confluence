package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

// TODO: 客户端显示问题；修复后酌情移除this.hasImpulse = true
public class BaseBombEntity extends ThrowableItemProjectile {
    protected int delay = 60;
    // 取消原版重力有助于减轻客户端显示错误
    protected float gravity = 0.05f;
    protected boolean gravityOn = true;
    protected float blastPower = 3f;
    protected float bounceFactor = 0.2f;
    protected float frictionFactor = 0.9f;

    public BaseBombEntity(EntityType<? extends BaseBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setNoGravity(true);
    }

    public BaseBombEntity(Level pLevel, LivingEntity pShooter) {
        super(EntityType.SNOWBALL, pShooter, pLevel);
        setNoGravity(true);
    }

    public BaseBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityType.SNOWBALL, pX, pY, pZ, pLevel);
        setNoGravity(true);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particle = this.getParticle();

            for (int num = 0; num < 8; ++num) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }


    /** 子类可以覆盖的方法，在弹跳前触发 */
    protected void blockHitCallBack(BlockHitResult hitBlock) {
    }
    /** 子类可以覆盖的方法，定义炸弹如何爆炸 */
    protected void explodeFunction() {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), blastPower, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (pResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) pResult;

            blockHitCallBack(blockHitResult);
            Vec3 velocity = this.getDeltaMovement().scale(frictionFactor);
            // 弹幕弹跳/摩擦
            Direction collDir = blockHitResult.getDirection();
            velocity = switch (collDir) {
                case EAST, WEST -> velocity.with(Direction.Axis.X, -bounceFactor * velocity.x());
                // 0.05 防止客户端认为弹跳类炸弹陷到方块里（？？？）
                case UP -> bounceFactor == 0f ?
                        velocity.with(Direction.Axis.Y, -bounceFactor * velocity.y()) :
                        velocity.with(Direction.Axis.Y, Math.max( -bounceFactor * velocity.y(), 0.05 ) );
                case DOWN -> velocity.with(Direction.Axis.Y, -bounceFactor * velocity.y());
                case NORTH, SOUTH -> velocity.with(Direction.Axis.Z, -bounceFactor * velocity.z());
            };
            this.setDeltaMovement(velocity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        // 爆炸
        if (!this.level().isClientSide) {
            if (--this.delay < 0) {
                explodeFunction();
                this.remove(RemovalReason.DISCARDED);
            }
        }
        // 重力
        if (this.gravityOn) {
            setDeltaMovement( getDeltaMovement().subtract(0, gravity, 0) );
        }
        this.hasImpulse = true;

        // ?李哉赣神魔
//        if (this.level().getBlockState(this.blockPosition()).getBlock() == Blocks.BEDROCK) {
//            this.setDeltaMovement(Vec3.ZERO);
//        }
    }
}
