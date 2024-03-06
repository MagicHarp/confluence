package org.confluence.mod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import org.jetbrains.annotations.NotNull;

public class MagicMirror extends Item {
    public MagicMirror() {
        super(new Properties().rarity(Rarity.RARE).fireResistant().stacksTo(1));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        level.playSound(player, player.getOnPos(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1, 1);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 20;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living, int tick) {
        if (tick < 20) return;
        if (level.isClientSide) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ConfluenceItems.MAGIC_MIRROR.get()));
        } else if (living instanceof ServerPlayer serverPlayer) {
            BlockPos pos = serverPlayer.getRespawnPosition();
            if (pos == null) {
                ServerLevel serverLevel = serverPlayer.server.getLevel(Level.OVERWORLD);
                if (serverLevel == null) serverLevel = (ServerLevel) level;
                LevelData data = serverLevel.getLevelData();
                serverPlayer.teleportTo(data.getXSpawn(), data.getYSpawn(), data.getXSpawn());
            } else {
                serverPlayer.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}
