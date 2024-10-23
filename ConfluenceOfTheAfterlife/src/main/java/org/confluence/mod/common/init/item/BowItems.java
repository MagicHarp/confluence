package org.confluence.mod.common.init.item;

import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.item.bow.ShortBowItem;

public class BowItems {
    public static final DeferredRegister.Items BOWS = DeferredRegister.createItems(Confluence.MODID);

    public static final DeferredItem<ShortBowItem> WOODEN_SHORT_BOW = BOWS.register("wooden_short_bow", () -> new ShortBowItem(4.0F, 384, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> COPPER_SHORT_BOW = BOWS.register("copper_short_bow", () -> new ShortBowItem(4.5F, 640, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> TIN_SHORT_BOW = BOWS.register("tin_short_bow", () -> new ShortBowItem(4.5F, 768, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> IRON_SHORT_BOW = BOWS.register("iron_short_bow", () -> new ShortBowItem(5.0F, 896, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> LEAD_SHORT_BOW = BOWS.register("lead_short_bow", () -> new ShortBowItem(5.0F, 1024, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> SILVER_SHORT_BOW = BOWS.register("silver_short_bow", () -> new ShortBowItem(5.5F, 1152, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> TUNGSTEN_SHORT_BOW = BOWS.register("tungsten_short_bow", () -> new ShortBowItem(5.5F, 1280, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> GOLDEN_SHORT_BOW = BOWS.register("golden_short_bow", () -> new ShortBowItem(6.0F, 1408, ModRarity.WHITE));
    public static final DeferredItem<ShortBowItem> PLATINUM_SHORT_BOW = BOWS.register("platinum_short_bow", () -> new ShortBowItem(6.0F, 1536, ModRarity.WHITE));
}
