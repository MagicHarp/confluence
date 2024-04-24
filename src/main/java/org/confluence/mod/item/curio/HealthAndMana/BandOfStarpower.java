package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.PlayerUtils;
import top.theillusivec4.curios.api.SlotContext;

public class BandOfStarpower extends BaseCurioItem {
    public BandOfStarpower() {
        super(ModRarity.BLUE);
    }

    public BandOfStarpower(Rarity rarity) {
        super(rarity);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        updateMana(living, 20);
        PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.applyCurioPrefix(living));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        updateMana(living, -20);
        PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.expireCurioPrefix(living));
    }

    protected static void updateMana(LivingEntity living, int amount) {
        if (living instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
                manaStorage.setAdditionalMana(manaStorage.getAdditionalMana() + amount);
                PlayerUtils.syncMana2Client(serverPlayer, manaStorage);
            });
        }
    }
}
