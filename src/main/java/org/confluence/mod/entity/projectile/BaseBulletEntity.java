package org.confluence.mod.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class BaseBulletEntity extends Projectile {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(BaseBulletEntity.class, EntityDataSerializers.INT);
    protected float attackDamage = 0.0F;
    protected float criticalChance = 0.0F;
    protected float knockBack = 0.0F;

    public BaseBulletEntity(EntityType<BaseBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BaseBulletEntity(LivingEntity living, Level level, Variant variant) {
        super(ModEntities.BASE_BULLET.get(), level);
        setOwner(living);
        setNoGravity(variant.gravity <= 0.0);
        setVariant(variant);
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
    protected void defineSynchedData() {
        entityData.define(DATA_VARIANT_ID, 0);
    }

    public void setVariant(Variant pVariant) {
        entityData.set(DATA_VARIANT_ID, pVariant.id);
    }

    public @NotNull Variant getVariant() {
        return Variant.byId(entityData.get(DATA_VARIANT_ID));
    }

    public SimpleParticleType getParticle() {
        return getVariant().supplier.get();
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        checkInsideBlocks();
        Vec3 vec3 = getDeltaMovement();
        HitResult.Type hitresult$type = hitresult.getType();
        if (hitresult$type == HitResult.Type.BLOCK || vec3.length() < 0.007) {
            discard();
            return;
        }
        if (hitresult$type == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) hitresult);
        }

        double offX = getX() + vec3.x;
        double offY = getY() + vec3.y;
        double offZ = getZ() + vec3.z;
        setDeltaMovement(vec3.scale(0.93));
        if (!isNoGravity()) {
            Vec3 vec31 = getDeltaMovement();
            setDeltaMovement(vec31.x, vec31.y - getGravity(), vec31.z);
        }
        setPos(offX, offY, offZ);
        level().addParticle(getParticle(), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!level().isClientSide) {
            float damage = getBaseDamage() * (1.0F + attackDamage);
            if (random.nextFloat() < criticalChance) damage *= 1.5F;
            if (entity.hurt(damageSources().indirectMagic(this, getOwner()), damage)) {
                float attackKnockBack = getBaseKnockBack() * (1.0F + knockBack);
                if (attackKnockBack > 0.0F) {
                    ModUtils.knockBackA2B(this, entity, attackKnockBack * 0.5, 0.2);
                }
            }
        }
        if (entity.isPickable()) discard();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    protected float getBaseDamage() {
        return getVariant().damage;
    }

    protected double getGravity() {
        return getVariant().gravity;
    }

    protected float getBaseKnockBack() {
        return getVariant().knockBack;
    }

    public static class Spark extends BaseBulletEntity {
        public Spark(LivingEntity living, Level level) {
            super(living, level, Variant.SPARK);
        }

        @Override
        protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
            super.onHitEntity(entityHitResult);
            entityHitResult.getEntity().setSecondsOnFire(5);
        }
    }

    public enum Variant implements StringRepresentable {
        AMETHYST(0, "amethyst", 4.0F, -1.0, 1.0F, ModParticles.AMBER_BULLET),
        TOPAZ(1, "topaz", 4.0F, -1.0, 1.0F, ModParticles.TOPAZ_BULLET),
        SAPPHIRE(2, "sapphire", 4.5F, -1.0, 1.0F, ModParticles.SAPPHIRE_BULLET),
        EMERALD(3, "emerald", 4.5F, -1.0, 1.0F, ModParticles.EMERALD_BULLET),
        RUBY(4, "ruby", 5.5F, -1.0, 1.0F, ModParticles.RUBY_BULLET),
        AMBER(5, "amber", 5.5F, -1.0, 1.0F, ModParticles.AMBER_BULLET),
        DIAMOND(6, "diamond", 6F, -1.0, 1.0F, ModParticles.DIAMOND_BULLET),
        FROST(7, "frost", 5.0F, 0.5, 1.0F, ParticleTypes.SNOWFLAKE::getType),
        SPARK(8, "spark", 1.3F, 0.2, 1.0F, ParticleTypes.LAVA::getType);

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;
        public final float damage;
        public final double gravity;
        public final float knockBack;
        public final Supplier<SimpleParticleType> supplier;

        Variant(int id, String name, float damage, double gravity, float knockBack, Supplier<SimpleParticleType> supplier) {
            this.id = id;
            this.name = name;
            this.damage = damage;
            this.gravity = gravity;
            this.knockBack = knockBack;
            this.supplier = supplier;
        }

        public int getId() {
            return id;
        }

        public static Variant byId(int pId) {
            return BY_ID.apply(pId);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
