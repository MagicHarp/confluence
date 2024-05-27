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
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.entity.projectile.BaseBulletEntity;
import org.confluence.mod.misc.ModSounds;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StaffItem extends Item implements IManaWeapon {
    private final BulletSupplier bulletSupplier;


    public StaffItem(BulletSupplier bulletSupplier, Properties properties) {
        super(properties.stacksTo(1));
        this.bulletSupplier = bulletSupplier;
    }

    public StaffItem(BulletSupplier bulletSupplier) {
        this(bulletSupplier, new Properties());
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer && PlayerUtils.extractMana(serverPlayer, () -> getManaCost(itemStack, 20))) {
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            Optional<ItemPrefix> optional = PrefixProvider.getPrefix(itemStack);
            BaseBulletEntity baseBulletEntity = bulletSupplier.create(serverPlayer, level, optional.orElse(null));
            baseBulletEntity.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0F, getVelocity(itemStack, 1.5F), 1.0F);
            level.addFreshEntity(baseBulletEntity);
            player.getCooldowns().addCooldown(this, getAttackSpeed(itemStack, 20));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.REGULAR_STAFF_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }

    @FunctionalInterface
    public interface BulletSupplier {
        BaseBulletEntity create(ServerPlayer serverPlayer, Level level, @Nullable ItemPrefix itemPrefix);
    }
}
