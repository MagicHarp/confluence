package org.confluence.mod.entity;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.jetbrains.annotations.NotNull;

public class BulletEntity extends Projectile {
    private Type type;

    public BulletEntity(EntityType<BulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BulletEntity(Player player, Level level) {
        this(ConfluenceEntities.BULLET.get(), level);
        this.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        this.setOwner(player);
        this.setNoGravity(true);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getBulletName() {
        return type.name();
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
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
            setDeltaMovement(vec3.scale(0.9));
            setPos(offX, offY, offZ);
            if (type != null) {
                level().addParticle(type.particle.get(), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().hurt(damageSources().indirectMagic(this, this.getOwner()), 5);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
    }

    public enum Type {
        ruby(ConfluenceParticles.RUBY_BULLET);

        private final RegistryObject<SimpleParticleType> particle;

        Type(RegistryObject<SimpleParticleType> particle) {
            this.particle = particle;
        }
    }
}
