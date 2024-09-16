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

public class ThrowingKnivesProjectile extends ThrowableProjectile {
    int penetrate = 0;
    final int maxPenetrate = 2;

    public ThrowingKnivesProjectile(EntityType<ThrowingKnivesProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrowingKnivesProjectile(Player player) {
        this(ModEntities.THROW_KNIVES_PROJECTILE.get(), player.level());
        setOwner(player);
        setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (entity.hurt(damageSources().mobProjectile(this, (LivingEntity) getOwner()), 6.0F)) {
            ModUtils.knockBackA2B(this, entity, 0.5, 0.2);
            if (penetrate == maxPenetrate){
                if (random.nextBoolean()) {
                    ModUtils.createItemEntity(new ItemStack(ModItems.THROWING_KNIVES.get()), getX(), getY(), getZ(), level(), 0);
                }
                discard();
            } else {
                penetrate++;
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (random.nextBoolean()) {
            ModUtils.createItemEntity(new ItemStack(ModItems.THROWING_KNIVES.get()), getX(), getY(), getZ(), level(), 0);
        }
        discard();
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return pTarget != getOwner();
    }
}
