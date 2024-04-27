package org.confluence.mod.item.loot;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.NotNull;

public class ClamItem extends Item {
    public ClamItem() {
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
                .withParameter(LootContextParams.TOOL, itemStack)
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withLuck(player.getLuck() + fishingPower.floatValue())
                .create(LootContextParamSets.FISHING);
            LootTable loottable = serverLevel.getServer().getLootData().getLootTable(ModLootTables.FISH_CLAM);
            for (ItemStack loot : loottable.getRandomItems(lootparams)) {
                if (!player.addItem(loot)) {
                    player.drop(loot, false, false);
                }
            }
            itemStack.shrink(1);
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
