package org.confluence.mod.entity.bullet;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;

public class AmethystBulletEntity extends BaseBulletEntity {
    public AmethystBulletEntity(EntityType<BaseBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public AmethystBulletEntity(Player player, Level level) {
        this(ModEntities.AMETHYST_BULLET.get(), level);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        setOwner(player);
        setNoGravity(true);
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.RUBY_BULLET.get();
    }
}
