package org.confluence.mod.item.loot;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
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
import org.confluence.mod.misc.ModSoundEvents;
import org.jetbrains.annotations.NotNull;

public class CthulhuEyeTreasureBagItem extends Item {
    public CthulhuEyeTreasureBagItem() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel && hand == InteractionHand.MAIN_HAND) {
            AtomicDouble fishingPower = new AtomicDouble();
            player.getCapability(AbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> fishingPower.set(playerAbility.getFishingPower(player)));
            LootParams lootparams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withLuck(fishingPower.floatValue())
                .create(LootContextParamSets.GIFT);
            LootTable loottable = serverLevel.getServer().getLootData().getLootTable(ModLootTables.CLAM);
            for (ItemStack loot : loottable.getRandomItems(lootparams)) {
                if (!player.addItem(loot)) {
                    player.drop(loot, false, false);
                }
            }
            itemStack.shrink(1);
            serverLevel.playSound(null, player.blockPosition(), ModSoundEvents.TERRA_OPERATION.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResultHolder.success(itemStack);
    }
}
