package org.confluence.mod.item.magic;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.BulletEntity;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;

public class StaffItem extends Item implements IMagicAttack {
    private final BulletEntity.Type type;

    public StaffItem(BulletEntity.Type type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public StaffItem(BulletEntity.Type type) {
        this(type, new Properties());
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 20;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, Level level, @NotNull LivingEntity living) {
        if (!level.isClientSide && living instanceof ServerPlayer serverPlayer) {
            if (PlayerUtils.extractMana(serverPlayer, () -> 20)) {
                serverPlayer.awardStat(Stats.ITEM_USED.get(this));
                BulletEntity bulletEntity = new BulletEntity(serverPlayer, level);
                bulletEntity.setType(type);
                bulletEntity.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(bulletEntity);
                level.playSound(serverPlayer, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return itemStack;
    }
}
