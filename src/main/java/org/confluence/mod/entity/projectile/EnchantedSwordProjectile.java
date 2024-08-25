package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.confluence.mod.entity.ModEntities;

public class EnchantedSwordProjectile extends SwordProjectile {
    public EnchantedSwordProjectile(EntityType<EnchantedSwordProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public EnchantedSwordProjectile(Player player) {
        this(ModEntities.ENCHANTED_SWORD_PROJECTILE.get(), player.level());
        setOwner(player);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
    }

    @Override
    protected int getDamage() {
        return 9;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        discard();
    }
}
