package org.confluence.mod.common.item.mana;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.init.ModAttachments;
import org.confluence.mod.common.init.ModRarities;
import org.confluence.mod.common.init.ModSoundEvents;
import org.confluence.mod.common.item.CustomRarityItem;
import org.jetbrains.annotations.NotNull;

public class ManaStar extends CustomRarityItem {
    public ManaStar() {
        super(new Properties().stacksTo(16), ModRarities.YELLOW);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        level.playSound(player, player.getOnPos().above(), ModSoundEvents.MANA_STAR_USE.get(), SoundSource.PLAYERS, 1, 1);

        ItemStack itemStack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getData(ModAttachments.MANA_STORAGE.get()).addStar()) {
                itemStack.shrink(1);
            }
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
