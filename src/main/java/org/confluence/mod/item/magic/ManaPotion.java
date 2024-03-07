package org.confluence.mod.item.magic;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.event.PlayerEvents;
import org.jetbrains.annotations.NotNull;

public class ManaPotion extends Item {
    private final int amount;

    public ManaPotion(int amount, Properties properties) {
        super(properties);
        this.amount = amount;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        level.playSound(player, player.getOnPos(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1, 1);

        ItemStack itemStack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer && PlayerEvents.receiveMana(serverPlayer, () -> amount)) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
