package org.confluence.mod.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class ShurikenProjectile extends ThrowableProjectile {
    public ShurikenProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ShurikenProjectile(Player player) {
        this(ModEntities.SHURIKEN_PROJECTILE.get(), player.level());
        setOwner(player);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), 5.2F)) {
            ModUtils.knockBackA2B(this, entity, 0.5, 0.2);
            if (random.nextBoolean()) {
                ModUtils.createItemEntity(new ItemStack(ModItems.SHURIKEN.get()), getX(), getY(), getZ(), level(), 0);
            }
            discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (random.nextBoolean()) {
            ModUtils.createItemEntity(new ItemStack(ModItems.SHURIKEN.get()), getX(), getY(), getZ(), level(), 0);
        }
        discard();
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return pTarget != getOwner();
    }
}
