package org.confluence.mod.item.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.projectile.ThrowingKnivesProjectile;
import org.confluence.mod.misc.ModSoundEvents;
import org.jetbrains.annotations.NotNull;

public class ThrowingKnivesItem extends Item {
    public ThrowingKnivesItem() {
        super(new Properties());
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSoundEvents.WAVING.get(), SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            Vec3 lookDirection = pPlayer.getViewVector(1.0F);
            Vec3 spawnPos = pPlayer.position().add(lookDirection.scale(0.2F));

            ThrowingKnivesProjectile projectile = new ThrowingKnivesProjectile(ModEntities.THROW_KNIVES_PROJECTILE.get(), pLevel);
            projectile.setPos(spawnPos.x, spawnPos.y + 1.0F, spawnPos.z);
            projectile.setDeltaMovement(lookDirection.scale(2.5F));
            projectile.setOwner(pPlayer);

            pLevel.addFreshEntity(projectile);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
