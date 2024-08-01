package org.confluence.mod.item.mana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.misc.ModSoundsEvent;
import org.jetbrains.annotations.NotNull;

public class ManaStar extends Item {
    public ManaStar() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(16));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        level.playSound(player, player.getOnPos().above(), ModSoundsEvent.MANA_STAR_USE.get(), SoundSource.PLAYERS, 1, 1);

        ItemStack itemStack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
                if (manaStorage.addStar()) {
                    itemStack.shrink(1);
                }
            });
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
