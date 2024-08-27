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
    public static final RegistryObject<ShortBowItem> WOODEN_SHORT_BOW = ITEMS.register("wooden_short_bow", () -> new ShortBowItem(4.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(384)));
    public static final RegistryObject<ShortBowItem> COPPER_SHORT_BOW = ITEMS.register("copper_short_bow", () -> new ShortBowItem(4.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(640)));
    public static final RegistryObject<ShortBowItem> TIN_SHORT_BOW = ITEMS.register("tin_short_bow", () -> new ShortBowItem(4.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(768)));
    public static final RegistryObject<ShortBowItem> IRON_SHORT_BOW = ITEMS.register("iron_short_bow", () -> new ShortBowItem(5.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(896)));
    public static final RegistryObject<ShortBowItem> LEAD_SHORT_BOW = ITEMS.register("lead_short_bow", () -> new ShortBowItem(5.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(1024)));
    public static final RegistryObject<ShortBowItem> SILVER_SHORT_BOW = ITEMS.register("silver_short_bow", () -> new ShortBowItem(5.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(1152)));
    public static final RegistryObject<ShortBowItem> TUNGSTEN_SHORT_BOW = ITEMS.register("tungsten_short_bow", () -> new ShortBowItem(5.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(1280)));
    public static final RegistryObject<ShortBowItem> GOLDEN_SHORT_BOW = ITEMS.register("golden_short_bow", () -> new ShortBowItem(6.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(1408)));
    public static final RegistryObject<ShortBowItem> PLATINUM_SHORT_BOW = ITEMS.register("platinum_short_bow", () -> new ShortBowItem(6.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(1536)));

    public static final RegistryObject<ShortBowItem> COPPER_BOW = ITEMS.register("copper_bow", () -> new ShortBowItem(4.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(640)));
    public static final RegistryObject<ShortBowItem> TIN_BOW = ITEMS.register("tin_bow", () -> new ShortBowItem(4.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(768)));
    public static final RegistryObject<ShortBowItem> IRON_BOW = ITEMS.register("iron_bow", () -> new ShortBowItem(5.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(896)));
    public static final RegistryObject<ShortBowItem> LEAD_BOW = ITEMS.register("lead_bow", () -> new ShortBowItem(5.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(1024)));
    public static final RegistryObject<ShortBowItem> SILVER_BOW = ITEMS.register("silver_bow", () -> new ShortBowItem(5.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(1152)));
    public static final RegistryObject<ShortBowItem> TUNGSTEN_BOW = ITEMS.register("tungsten_bow", () -> new ShortBowItem(5.5F, new Item.Properties().rarity(ModRarity.WHITE).durability(1280)));
    public static final RegistryObject<ShortBowItem> GOLDEN_BOW = ITEMS.register("golden_bow", () -> new ShortBowItem(6.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(1408)));
    public static final RegistryObject<ShortBowItem> PLATINUM_BOW = ITEMS.register("platinum_bow", () -> new ShortBowItem(6.0F, new Item.Properties().rarity(ModRarity.WHITE).durability(1536)));

    @OnlyIn(Dist.CLIENT)
    public static void registerProperties() {
        ResourceLocation pull = new ResourceLocation("pull");
        ClampedItemPropertyFunction shortBowPull = (itemStack, clientLevel, living, speed) -> living != null && living.getUseItem() == itemStack ? (float) (itemStack.getUseDuration() - living.getUseItemRemainingTicks()) / ShortBowItem.FULL_POWER_TICKS : 0.0F;
        ResourceLocation pulling = new ResourceLocation("pulling");
        ClampedItemPropertyFunction bowPulling = (itemStack, clientLevel, living, speed) -> living != null && living.isUsingItem() && living.getUseItem() == itemStack ? 1.0F : 0.0F;

        ItemProperties.register(WOODEN_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(WOODEN_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(COPPER_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(COPPER_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(TIN_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(TIN_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(IRON_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(IRON_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(LEAD_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(LEAD_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(SILVER_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(SILVER_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(TUNGSTEN_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(TUNGSTEN_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(GOLDEN_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(GOLDEN_SHORT_BOW.get(), pulling, bowPulling);
        ItemProperties.register(PLATINUM_SHORT_BOW.get(), pull, shortBowPull);
        ItemProperties.register(PLATINUM_SHORT_BOW.get(), pulling, bowPulling);
    }

    public static void init() {}
}
