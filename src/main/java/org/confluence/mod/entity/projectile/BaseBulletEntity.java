package org.confluence.mod.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBulletEntity extends Projectile {
    protected float attackDamage = 0.0F;
    protected float criticalChance = 0.0F;
    protected float knockBack = 0.0F;

    public BaseBulletEntity(EntityType<BaseBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    protected BaseBulletEntity(EntityType<BaseBulletEntity> entityType, Player player, Level level, @Nullable ItemPrefix itemPrefix) {
        this(entityType, level);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        setOwner(player);
        setNoGravity(true);
        if (itemPrefix != null) {
            this.attackDamage = (float) itemPrefix.attackDamage;
            this.criticalChance = (float) itemPrefix.criticalChance;
            this.knockBack = (float) itemPrefix.knockBack;
        }
    }

    public abstract SimpleParticleType getParticle();

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
            this.setDeltaMovement(vec31.x, vec31.y - getGravity(), vec31.z);
        }
        setPos(offX, offY, offZ);
        level().addParticle(getParticle(), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        float damage = getDamage() * (1 + attackDamage);
        if (random.nextFloat() < criticalChance) damage *= 1.5F;
        Entity entity = entityHitResult.getEntity();
        if (entity.hurt(damageSources().indirectMagic(this, getOwner()), damage)) {
            float attackKnockBack = getKnockBack() + knockBack;
            if (attackKnockBack > 0.0F && entity instanceof LivingEntity living) {
                living.knockback(attackKnockBack * 0.5F, Mth.sin(this.getYRot() * (Mth.PI / 180F)), -Mth.cos(this.getYRot() * (Mth.PI / 180F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
    }

    protected float getDamage() {
        return 5;
    }

    protected double getGravity() {
        return 0.03;
    }

    protected float getKnockBack() {
        return 1.0F;
    }

    public static class Amber extends BaseBulletEntity {
        public Amber(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Amber(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.AMBER_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.AMBER_BULLET.get();
        }
    }

    public static class Amethyst extends BaseBulletEntity {
        public Amethyst(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Amethyst(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.AMETHYST_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.AMETHYST_BULLET.get();
        }
    }

    public static class Diamond extends BaseBulletEntity {
        public Diamond(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Diamond(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.DIAMOND_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.DIAMOND_BULLET.get();
        }
    }

    public static class Emerald extends BaseBulletEntity {
        public Emerald(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Emerald(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.EMERALD_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.EMERALD_BULLET.get();
        }
    }

    public static class Frost extends BaseBulletEntity {
        public Frost(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Frost(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.FROST_BULLET.get(), player, level, itemPrefix);
            setNoGravity(false);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.RUBY_BULLET.get();
        }

        @Override
        protected double getGravity() {
            return 0.5;
        }
    }

    public static class Ruby extends BaseBulletEntity {
        public Ruby(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Ruby(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.RUBY_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.RUBY_BULLET.get();
        }
    }

    public static class Sapphire extends BaseBulletEntity {
        public Sapphire(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Sapphire(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.SAPPHIRE_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.SAPPHIRE_BULLET.get();
        }
    }

    public static class Spark extends BaseBulletEntity {
        public Spark(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Spark(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.SPARK_BULLET.get(), player, level, itemPrefix);
            setNoGravity(false);
        }

        @Override
        protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
            super.onHitEntity(entityHitResult);
            entityHitResult.getEntity().setSecondsOnFire(5);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ParticleTypes.LAVA.getType();
        }

        protected float getDamage() {
            return 1.3F;
        }

        @Override
        protected double getGravity() {
            return 0.2;
        }
    }

    public static class Topaz extends BaseBulletEntity {
        public Topaz(EntityType<BaseBulletEntity> entityType, Level level) {
            super(entityType, level);
        }

        public Topaz(Player player, Level level, @Nullable ItemPrefix itemPrefix) {
            super(ModEntities.TOPAZ_BULLET.get(), player, level, itemPrefix);
        }

        @Override
        public SimpleParticleType getParticle() {
            return ModParticles.TOPAZ_BULLET.get();
        }
    }
}
