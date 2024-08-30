package org.confluence.mod.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public abstract class SwordProjectile extends Projectile {
    private static final int TIME_EXISTENCE = 40;
    protected float attackDamage = 0.0F;
    protected float criticalChance = 0.0F;
    protected float knockBack = 0.0F;

    public SwordProjectile(EntityType<? extends SwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public SwordProjectile(EntityType<? extends SwordProjectile> entityType, Level level, LivingEntity living) {
        this(entityType, level);
        setOwner(living);
        setPos(living.getX(), living.getEyeY() - 0.1, living.getZ());
        AttributeInstance attributeInstance = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (attributeInstance != null) {
            this.knockBack = (float) attributeInstance.getValue();
        }
        attributeInstance = living.getAttribute(ModAttributes.getRangedDamage());
        if (attributeInstance != null) {
            this.attackDamage = (float) attributeInstance.getValue();
        }
        if (ModAttributes.hasCustomAttribute(ModAttributes.CRIT_CHANCE.get())) return;
        attributeInstance = living.getAttribute(ModAttributes.CRIT_CHANCE.get());
        if (attributeInstance != null) {
            this.criticalChance = (float) attributeInstance.getValue();
        }
    }

    @Override
    protected void defineSynchedData() {}

    protected abstract int getBaseDamage();

    protected abstract float getBaseKnockBack();

    @Override
    public void tick() {
        super.tick();
        checkCollision();
        Vec3 vec3 = getDeltaMovement();
        double offX = getX() + vec3.x;
        double offY = getY() + vec3.y;
        double offZ = getZ() + vec3.z;
        setDeltaMovement(vec3.scale(0.93));
        setPos(offX, offY, offZ);
        if (tickCount >= TIME_EXISTENCE) discard();
    }

    private void checkCollision() {
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity != getOwner());
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitResult);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            onHitBlock((BlockHitResult) hitResult);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!level().isClientSide) {
            float damage = getBaseDamage() * (1.0F + attackDamage);
            if (random.nextFloat() < criticalChance) damage *= 1.5F;
            if (entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), damage)) {
                float attackKnockBack = getBaseKnockBack() + knockBack;
                ModUtils.knockBackA2B(this, entity, attackKnockBack * 0.5, 0.2);
            }
        }
        if (entity.isPickable()) discard();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }
}
