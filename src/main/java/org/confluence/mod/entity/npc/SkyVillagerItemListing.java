package org.confluence.mod.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyVillagerItemListing implements VillagerTrades.ItemListing {
    private final ItemStack cost;
    private final ItemStack payment;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    public SkyVillagerItemListing(ItemStack pCost, ItemStack pPayment, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
        this.cost = pCost;
        this.payment = pPayment;
        this.maxUses = pMaxUses;
        this.villagerXp = pVillagerXp;
        this.priceMultiplier = pPriceMultiplier;
    }

    @Override
    public @Nullable MerchantOffer getOffer(@NotNull Entity pTrader, @NotNull RandomSource random) {
        if (pTrader instanceof VillagerDataHolder holder && holder.getVillagerData().getType() == ModVillagers.SKY_TYPE.get()) {
            return new MerchantOffer(cost.copy(), payment.copy(), maxUses, villagerXp, priceMultiplier);
        } else {
            return null;
        }
    }
}
