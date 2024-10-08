package org.confluence.mod.entity.projectile;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class BaseAmmoEntity extends Projectile implements VariantHolder<BaseAmmoEntity.Variant> {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(BaseAmmoEntity.class, EntityDataSerializers.INT);
    protected float attackDamage = 0.0F;
    protected float criticalChance = 0.0F;
    protected float knockBack = 0.0F;

    public BaseAmmoEntity(EntityType<BaseAmmoEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BaseAmmoEntity(LivingEntity living, Level level, Variant variant) {
        super(ModEntities.BASE_AMMO.get(), level);
        setOwner(living);
        setNoGravity(true);
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
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        checkInsideBlocks();
        Vec3 vec3 = getDeltaMovement();
        HitResult.Type hitresult$type = hitresult.getType();
        if (hitresult$type == HitResult.Type.BLOCK) {
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
        setPos(offX, offY, offZ);
        if (tickCount > 200) discard();
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

    public float getBaseDamage() {
        return getVariant().damage;
    }

    public float getBaseKnockBack() {
        return getVariant().knockBack;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_VARIANT_ID, 0);
    }

    @Override
    public void setVariant(Variant pVariant) {
        entityData.set(DATA_VARIANT_ID, pVariant.id);
    }

    @Override
    public @NotNull Variant getVariant() {
        return Variant.byId(entityData.get(DATA_VARIANT_ID));
    }

    public enum Variant implements StringRepresentable {
        MUSKET(0, "musket", 7.0F, 2.0F, 2, 2.0F),
        METEOR(1, "meteor", 8.0F, 1.5F, 2, 1.0F),
        SILVER(2, "silver", 9.0F, 2.25F, 2, 3.0F),
        CRYSTAL(3, "crystal", 9.0F, 2.25F, 2, 3.0F),
        CURSED(4, "cursed", 12.0F, 1.6667F, 3, 4.5F),
        CHLOROPHYTE(5, "chlorophyte", 9.0F, 1.6667F, 3, 4.5F),
        HIGH_VELOCITY(6, "high_velocity", 11.0F, 0.5F, 8, 4.0F),
        ICHOR(7, "ichor", 13.0F, 1.75F, 3, 4.0F),
        VENOM(8, "venom", 15.0F, 1.7667F, 3, 4.1F),
        PARTY(9, "party", 10.0F, 1.7F, 3, 5.0F),
        NANO(10, "nano", 15.0F, 1.5333F, 3, 3.6F),
        EXPLODING(11, "exploding", 10.0F, .5667F, 3, 6.6F),
        GOLDEN(12, "golden", 10.0F, 1.5333F, 3, 3.6F),
        LUMINITE(13, "luminite", 20.0F, 0.3333F, 6, 3.0F),
        TUNGSTEN(14, "tungsten", 9.0F, 2.25F, 2, 4.0F);

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;
        public final float damage;
        public final float velocity;
        public final int multiplier;
        public final float knockBack;

        Variant(int id, String name, float damage, float velocity, int multiplier, float knockBack) {
            this.id = id;
            this.name = name;
            this.damage = damage;
            this.velocity = velocity;
            this.multiplier = multiplier;
            this.knockBack = knockBack;
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
