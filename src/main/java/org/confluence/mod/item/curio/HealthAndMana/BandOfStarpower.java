package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
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
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.increaseAdditionalMana(serverPlayer, 20);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.increaseAdditionalMana(serverPlayer, -20);
        }
    }
}
