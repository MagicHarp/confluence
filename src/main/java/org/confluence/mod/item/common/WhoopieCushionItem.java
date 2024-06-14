package org.confluence.mod.item.common;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.misc.ModLootTables;
import org.confluence.mod.misc.ModSounds;
import org.jetbrains.annotations.NotNull;

public class WhoopieCushionItem extends Item {
    public WhoopieCushionItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // 播放声音
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.FART_SOUND.get(), player.getSoundSource(), 1.0f, 1.0f);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

}
