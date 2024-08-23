package org.confluence.mod.item.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModSoundEvents;

public class FallingStarItem extends Item {
    public FallingStarItem() {
        super(new Properties().rarity(ModRarity.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // 播放声音
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.LIFE_CRYSTAL_USE.get(), player.getSoundSource(), 1.0f, 1.0f);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
    public FallingStarItem(Rarity rarity) {
        super(new Properties().rarity(rarity));
    }

    public FallingStarItem(Rarity rarity,Boolean isFireResistant) {
        super(new Properties().rarity(rarity).fireResistant());
    }
}
