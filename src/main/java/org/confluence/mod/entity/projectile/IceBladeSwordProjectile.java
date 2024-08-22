package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;

public class IceBladeSwordProjectile extends SwordProjectile {
    public IceBladeSwordProjectile(EntityType<IceBladeSwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public IceBladeSwordProjectile(Player player) {
        this(ModEntities.ICE_BLADE_SWORD_PROJECTILE.get(), player.level());
        setOwner(player);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
    }

    @Override
    protected int getDamage() {
        return 7;
    }
}
