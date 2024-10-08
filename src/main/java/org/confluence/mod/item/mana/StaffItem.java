package org.confluence.mod.item.mana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.BaseBulletEntity;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;

public class StaffItem extends Item implements IManaWeapon {
    private final BulletSupplier bulletSupplier;
    private final int manaCost;


    public StaffItem(int manaCost, BulletSupplier bulletSupplier, Properties properties) {
        super(properties.stacksTo(1));
        this.manaCost = manaCost;
        this.bulletSupplier = bulletSupplier;
    }

    public StaffItem(int manaCost, BulletSupplier bulletSupplier) {
        this(manaCost, bulletSupplier, new Properties());
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer && PlayerUtils.extractMana(serverPlayer, () -> calculateManaCost(itemStack, manaCost))) {
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            BaseBulletEntity baseBulletEntity = bulletSupplier.create(serverPlayer, level);
            baseBulletEntity.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0F, getVelocity(player, 1.5F), 1.0F);
            level.addFreshEntity(baseBulletEntity);
            player.getCooldowns().addCooldown(this, getAttackSpeed(player, 20));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.REGULAR_STAFF_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }

    @FunctionalInterface
    public interface BulletSupplier {
        BaseBulletEntity create(ServerPlayer serverPlayer, Level level);
    }
}
