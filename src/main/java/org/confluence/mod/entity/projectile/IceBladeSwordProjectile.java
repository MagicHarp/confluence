package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;

public class IceBladeSwordProjectile extends SwordProjectile {
    public IceBladeSwordProjectile(EntityType<IceBladeSwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public IceBladeSwordProjectile(Level level) {
        this(ModEntities.ICE_BLADE_SWORD_PROJECTILE.get(), level);
    }

    @Override
    protected int getDamage() {
        return 7;
    }
}
