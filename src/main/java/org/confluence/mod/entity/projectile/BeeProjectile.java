package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;

public class BeeProjectile extends AbstractHurtingProjectile {
    private int blockHitTime = 0;

    public BeeProjectile(EntityType<BeeProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public BeeProjectile(Level level, Player player) {
        this(ModEntities.BEE_PROJECTILE.get(), level);
        setOwner(player);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        if (blockHitTime >= 3) {
            discard();
            return;
        }
        Vec3 motion = getDeltaMovement();
        double x = motion.x;
        double y = motion.y;
        double z = motion.z;
        switch (blockHitResult.getDirection().getAxis()) {
            case X -> x = -x;
            case Y -> y = -y;
            case Z -> z = -z;
        }
        setDeltaMovement(x, y, z);
        blockHitTime++;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof Enemy) {
            entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), 5.0F);
            discard();
        }
    }
}
