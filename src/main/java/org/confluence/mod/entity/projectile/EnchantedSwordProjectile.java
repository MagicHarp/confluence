package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;

public class EnchantedSwordProjectile extends SwordProjectile {
    public EnchantedSwordProjectile(EntityType<EnchantedSwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public EnchantedSwordProjectile(Level level) {
        this(ModEntities.ENCHANTED_SWORD_PROJECTILE.get(), level);
    }
}
