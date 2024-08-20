package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class BouncyBombEntity extends BaseBombEntity {
    float bounceFactorNew = 0.8f;

    public BouncyBombEntity(EntityType<? extends BouncyBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        super.bounceFactor = bounceFactorNew;
    }

    public BouncyBombEntity(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
        super.bounceFactor = bounceFactorNew;
    }

    public BouncyBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        super.bounceFactor = bounceFactorNew;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BOUNCY_BOMB.get();
    }
}
