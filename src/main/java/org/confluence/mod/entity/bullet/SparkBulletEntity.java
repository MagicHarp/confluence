package org.confluence.mod.entity.bullet;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.confluence.mod.entity.ConfluenceEntities;

public class SparkBulletEntity extends BaseBulletEntity {
    public SparkBulletEntity(EntityType<BaseBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SparkBulletEntity(Player player, Level level) {
        this(ConfluenceEntities.SPARK_BULLET.get(), level);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        setOwner(player);
        setNoGravity(true);
    }

    @Override
    public SimpleParticleType getParticle() {
        return ConfluenceParticles.RUBY_BULLET.get();
    }
}
