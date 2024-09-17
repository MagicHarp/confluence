package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class BaseBombEntity extends ThrowableItemProjectile {
    public static final float DIAMETER = 0.375F;
    public float rotateO = 0.0F;
    public float rotate = 0.0F;

    protected int delay = 60;
    protected float blastPower = 3f;
    protected float bounceFactor = 0.2f;
    protected float frictionFactor = 0.9f;

    public BaseBombEntity(EntityType<? extends BaseBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BaseBombEntity(EntityType<? extends BaseBombEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public BaseBombEntity(EntityType<? extends BaseBombEntity> pEntityType, LivingEntity pShooter) {
        super(pEntityType, pShooter, pShooter.level());
    }

    public BaseBombEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.BOMB_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public BaseBombEntity(LivingEntity pShooter) {
        this(ModEntities.BOMB_ENTITY.get(), pShooter);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = getItemRaw();
        return itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particle = getParticle();
            for (int num = 0; num < 8; ++num) {
                level().addParticle(particle, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
        }
    }


    /**
     * 子类可以覆盖的方法，在弹跳前触发
     */
    protected void blockHitCallBack(BlockHitResult hitBlock) {}

    /**
     * 子类可以覆盖的方法，定义炸弹如何爆炸
     */
    protected void explodeFunction() {
        level().explode(this, getX(), getY(), getZ(), blastPower, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
        blockHitCallBack(pResult);
        Vec3 motion = ModUtils.relativeScale(getDeltaMovement(), pResult.getDirection().getAxis(), -bounceFactor);
        if (Math.abs(motion.y) < 0.03) motion = new Vec3(motion.x, 0.0, motion.z);
        setDeltaMovement(motion.scale(frictionFactor));
    }

    @Override
    public void tick() {
        super.tick();

        float s = (float) getDeltaMovement().length();
        float r = 2.0F * s / DIAMETER;
        if (rotate > Mth.TWO_PI) this.rotate -= Mth.TWO_PI;
        this.rotateO = rotate;
        this.rotate += r / Mth.PI;
        Confluence.LOGGER.info("{}, {}, {}", s, r, rotate);

        if (!level().isClientSide) {
            if (this.delay-- < 0) {
                explodeFunction();
                discard();
            }
        }
    }

    @Override
    public float getGravity() {
        return 0.05F;
    }
}
