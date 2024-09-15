package org.confluence.mod.entity.projectile.bombs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class BouncyBombEntity extends BaseBombEntity {
    float bounceFactorNew = 0.8f;

    public BouncyBombEntity(EntityType<? extends BouncyBombEntity> pEntityType, Player player, Level pLevel) {
        super(pEntityType, player, pLevel);
        super.bounceFactor = bounceFactorNew;
    }

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
