package org.confluence.mod.entity.projectile.bullet;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;

public class SparkBulletEntity extends BaseBulletEntity {
    public SparkBulletEntity(EntityType<BaseBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SparkBulletEntity(Player player, Level level) {
        this(ModEntities.SPARK_BULLET.get(), level);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        setOwner(player);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(damageSources().indirectMagic(this, getOwner()), getDamage());
        entity.setSecondsOnFire(5);
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
