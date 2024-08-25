package org.confluence.mod.item.bow;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.misc.ModRarity;

import static org.confluence.mod.item.ModItems.ITEMS;

public final class Bows {
    public static final RegistryObject<ShortBowItem> WOOD_SHORT_BOW = ITEMS.register("wood_short_bow", () -> new ShortBowItem(new Item.Properties().rarity(ModRarity.WHITE).durability(384)));

    @OnlyIn(Dist.CLIENT)
    public static void registerPull() {
        ResourceLocation pull = new ResourceLocation("pull");
        ClampedItemPropertyFunction shortBowPull = (itemStack, clientLevel, living, speed) -> living != null && living.getUseItem() == itemStack ? (float) (itemStack.getUseDuration() - living.getUseItemRemainingTicks()) / ShortBowItem.FULL_POWER_TICKS : 0.0F;
        ResourceLocation pulling = new ResourceLocation("pulling");
        ClampedItemPropertyFunction shortBowPulling = (itemStack, clientLevel, living, speed) -> living != null && living.isUsingItem() && living.getUseItem() == itemStack ? 1.0F : 0.0F;

        ItemProperties.register(WOOD_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(WOOD_SHORT_BOW.get(), pulling, shortBowPulling);
    }

    public static void init() {}
}
