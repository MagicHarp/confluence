package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModSoundsEvent;

public class WhoopieCushionItem extends Item {
    public WhoopieCushionItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // 播放声音
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundsEvent.FART_SOUND.get(), player.getSoundSource(), 1.0f, 1.0f);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

}
