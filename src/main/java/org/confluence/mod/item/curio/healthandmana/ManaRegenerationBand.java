package org.confluence.mod.item.curio.healthandmana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.PlayerUtils;
import top.theillusivec4.curios.api.SlotContext;

public class ManaRegenerationBand extends BaseCurioItem {
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
                manaStorage.setManaRegenerationBand(true);
                manaStorage.setAdditionalMana(manaStorage.getAdditionalMana() + 20);
                PlayerUtils.syncMana2Client(serverPlayer, manaStorage);
            });
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
                manaStorage.setManaRegenerationBand(false);
                manaStorage.setAdditionalMana(manaStorage.getAdditionalMana() - 20);
                PlayerUtils.syncMana2Client(serverPlayer, manaStorage);
            });
        }
    }
}
