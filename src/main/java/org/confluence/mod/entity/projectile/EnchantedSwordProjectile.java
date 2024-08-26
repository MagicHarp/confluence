package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.confluence.mod.entity.ModEntities;

public class EnchantedSwordProjectile extends SwordProjectile {
    public EnchantedSwordProjectile(EntityType<EnchantedSwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public EnchantedSwordProjectile(LivingEntity living) {
        super(ModEntities.ENCHANTED_SWORD_PROJECTILE.get(), living.level(), living);
    }

    @Override
    protected int getBaseDamage() {
        return 9;
    }

    @Override
    protected float getBaseKnockBack() {
        return 1.0F;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        discard();
    }
}
