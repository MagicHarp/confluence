package org.confluence.mod.common.init.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.item.CustomRarityItem;

public class Materials {
    public static final DeferredRegister.Items MATERIALS = DeferredRegister.createItems(Confluence.MODID);

    // todo ai自动生成的，没有检查（逆天）
    public static final DeferredItem<Item> GEL = register("gel", ModRarity.WHITE);
    public static final DeferredItem<Item> PINK_GEL = register("pink_gel", ModRarity.PINK);

    public static final DeferredItem<Item> RAW_TIN = register("raw_tin");
    public static final DeferredItem<Item> TIN_INGOT = register("tin_ingot");
    public static final DeferredItem<Item> RAW_LEAD = register("raw_lead");
    public static final DeferredItem<Item> LEAD_INGOT = register("lead_ingot");
    public static final DeferredItem<Item> RAW_SILVER = register("raw_silver");
    public static final DeferredItem<Item> SILVER_INGOT = register("silver_ingot");
    public static final DeferredItem<Item> RAW_TUNGSTEN = register("raw_tungsten");
    public static final DeferredItem<Item> TUNGSTEN_INGOT = register("tungsten_ingot");
    public static final DeferredItem<Item> RAW_PLATINUM = register("raw_platinum", ModRarity.BLUE);
    public static final DeferredItem<Item> PLATINUM_INGOT = register("platinum_ingot", ModRarity.BLUE);
    public static final DeferredItem<Item> RAW_METEORITE = register("raw_meteorite", ModRarity.BLUE);
    public static final DeferredItem<Item> METEORITE_INGOT = register("meteorite_ingot", ModRarity.BLUE);
    public static final DeferredItem<Item> RAW_EBONY = register("raw_ebony", ModRarity.BLUE);
    public static final DeferredItem<Item> EBONY_INGOT = register("ebony_ingot", ModRarity.BLUE);
    public static final DeferredItem<Item> RAW_TR_CRIMSON = register("raw_tr_crimson", ModRarity.BLUE);
    public static final DeferredItem<Item> TR_CRIMSON_INGOT = register("tr_crimson_ingot", ModRarity.BLUE);
    public static final DeferredItem<Item> HELLSTONE_INGOT = register("hellstone_ingot", ModRarity.ORANGE);
    public static final DeferredItem<Item> RAW_HELLSTONE = register("raw_hellstone", ModRarity.ORANGE);
    public static final DeferredItem<Item> PRIMORDIAL_HELLSTONE_INGOT = register("primordial_hellstone_ingot", ModRarity.ORANGE);

    public static final DeferredItem<Item> RAW_COBALT = register("raw_cobalt", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> COBALT_INGOT = register("cobalt_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> RAW_PALLADIUM = register("raw_palladium", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> PALLADIUM_INGOT = register("palladium_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> RAW_MITHRIL = register("raw_mithril", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> MITHRIL_INGOT = register("mithril_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> RAW_ORICHALCUM = register("raw_orichalcum", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> ORICHALCUM_INGOT = register("orichalcum_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> RAW_ADAMANTITE = register("raw_adamantite", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> ADAMANTITE_INGOT = register("adamantite_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> RAW_TITANIUM = register("raw_titanium", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> TITANIUM_INGOT = register("titanium_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> HALLOWED_INGOT = register("hallowed_ingot", ModRarity.LIGHT_RED);
    public static final DeferredItem<Item> CHLOROPHYTE_INGOT = register("chlorophyte_ingot", ModRarity.ORANGE);
    public static final DeferredItem<Item> RAW_CHLOROPHYTE = register("raw_chlorophyte", ModRarity.ORANGE);
    public static final DeferredItem<Item> SHROOMITE_INGOT = register("shroomite_ingot", ModRarity.ORANGE);
    public static final DeferredItem<Item> SPECTRE_INGOT = register("spectre_ingot", ModRarity.ORANGE);
    public static final DeferredItem<Item> RAW_LUMINITE = register("raw_luminite", ModRarity.ORANGE);
    public static final DeferredItem<Item> LUMINITE_INGOT = register("luminite_ingot", ModRarity.ORANGE);

    public static final DeferredItem<Item> AMBER = register("amber");
    public static final DeferredItem<Item> TR_AMETHYST = register("tr_amethyst");
    public static final DeferredItem<Item> TR_EMERALD = register("tr_emerald");
    public static final DeferredItem<Item> RUBY = register("ruby");
    public static final DeferredItem<Item> SAPPHIRE = register("sapphire");
    public static final DeferredItem<Item> TOPAZ = register("topaz");

    public static final DeferredItem<Item> EMERALD_COIN = register("emerald_coin");

    public static final DeferredItem<Item> STAR_PETALS = register("star_petals");
    public static final DeferredItem<Item> FALLING_STAR = register("falling_star");
    public static final DeferredItem<Item> WEAVING_CLOUD_COTTON = register("weaving_cloud_cotton");
    public static final DeferredItem<Item> CARRION = register("carrion");
    public static final DeferredItem<Item> VERTEBRA = register("vertebra");
    public static final DeferredItem<Item> BLOOD_CLOT_POWDER = register("blood_clot_powder");
    public static final DeferredItem<Item> BLACK_INK = register("black_ink");
    public static final DeferredItem<Item> PURPLE_MUCUS = register("purple_mucus");
    public static final DeferredItem<Item> SHARK_FIN = register("shark_fin");
    public static final DeferredItem<Item> ANTLION_MANDIBLE = register("antlion_mandible");
    public static final DeferredItem<Item> HOOK = register("hook");
    public static final DeferredItem<Item> LENS = register("lens");
    public static final DeferredItem<Item> BLACK_LENS = register("black_lens");
    public static final DeferredItem<Item> LIFE_CRYSTAL = register("life_crystal", ModRarity.WHITE);
    public static final DeferredItem<Item> LIFE_FRUIT = register("life_fruit");
    public static final DeferredItem<Item> MANA_STAR = register("mana_star");
    public static final DeferredItem<Item> STURDY_FOSSIL = register("sturdy_fossil");
    public static final DeferredItem<Item> SHADOW_SCALE = register("shadow_scale");
    public static final DeferredItem<Item> TISSUE_SAMPLE = register("tissue_sample");

    public static final DeferredItem<Item> CRYSTAL_SHARDS_ITEM = register("crystal_shards_item");
    public static final DeferredItem<Item> CURSED_FLAME = register("cursed_flame");
    public static final DeferredItem<Item> ICHOR = register("ichor");
    public static final DeferredItem<Item> PIXIE_DUST = register("pixie_dust");

    public static final DeferredItem<Item> PEARL = register("pearl");
    public static final DeferredItem<Item> BLACK_PEARL = register("black_pearl");
    public static final DeferredItem<Item> PINK_PEARL = register("pink_pearl");

    /*
    GEL("gel", () -> new GelItem(WHITE)),
    PINK_GEL("pink_gel", () -> new BaseItem(PINK)),

    RAW_TIN("raw_tin", () -> new BaseItem(WHITE)),
    TIN_INGOT("tin_ingot", () -> new BaseItem(WHITE)),
    RAW_LEAD("raw_lead", () -> new BaseItem(WHITE)),
    LEAD_INGOT("lead_ingot", () -> new BaseItem(WHITE)),
    RAW_SILVER("raw_silver", () -> new BaseItem(WHITE)),
    SILVER_INGOT("silver_ingot", () -> new BaseItem(WHITE)),
    RAW_TUNGSTEN("raw_tungsten", () -> new BaseItem(WHITE)),
    TUNGSTEN_INGOT("tungsten_ingot", () -> new BaseItem(WHITE)),
    RAW_PLATINUM("raw_platinum", () -> new BaseItem(BLUE)),
    PLATINUM_INGOT("platinum_ingot", () -> new BaseItem(BLUE)),
    RAW_METEORITE("raw_meteorite", () -> new BaseItem(BLUE)),
    METEORITE_INGOT("meteorite_ingot", () -> new TooltipItem(Component.translatable("item.confluence.meteorite_ingot.tooltip"))),
    RAW_EBONY("raw_ebony", () -> new BaseItem(BLUE)),
    EBONY_INGOT("ebony_ingot", () -> new BaseItem(BLUE)),
    RAW_TR_CRIMSON("raw_tr_crimson", () -> new BaseItem(BLUE)),
    TR_CRIMSON_INGOT("tr_crimson_ingot", () -> new BaseItem(BLUE)),
    HELLSTONE_INGOT("hellstone_ingot", () -> new BaseItem(ORANGE,true)),
    RAW_HELLSTONE("raw_hellstone", () -> new BaseItem(ORANGE,true)),
    PRIMORDIAL_HELLSTONE_INGOT("primordial_hellstone_ingot", () -> new BaseItem(ORANGE,true)),

    RAW_COBALT("raw_cobalt", () -> new BaseItem(LIGHT_RED,true)),
    COBALT_INGOT("cobalt_ingot", () -> new BaseItem(LIGHT_RED,true)),
    RAW_PALLADIUM("raw_palladium", () -> new BaseItem(LIGHT_RED,true)),
    PALLADIUM_INGOT("palladium_ingot", () -> new BaseItem(LIGHT_RED,true)),
    RAW_MITHRIL("raw_mithril", () -> new BaseItem(LIGHT_RED,true)),
    MITHRIL_INGOT("mithril_ingot", () -> new BaseItem(LIGHT_RED,true)),
    RAW_ORICHALCUM("raw_orichalcum", () -> new BaseItem(LIGHT_RED,true)),
    ORICHALCUM_INGOT("orichalcum_ingot", () -> new BaseItem(LIGHT_RED,true)),
    RAW_ADAMANTITE("raw_adamantite", () -> new BaseItem(LIGHT_RED,true)),
    ADAMANTITE_INGOT("adamantite_ingot", () -> new BaseItem(LIGHT_RED,true)),
    RAW_TITANIUM("raw_titanium", () -> new BaseItem(LIGHT_RED,true)),
    TITANIUM_INGOT("titanium_ingot", () -> new BaseItem(LIGHT_RED,true)),
    HALLOWED_INGOT("hallowed_ingot", () -> new BaseItem(LIGHT_RED,true)),
    CHLOROPHYTE_INGOT("chlorophyte_ingot", () -> new BaseItem(ORANGE,true)),
    RAW_CHLOROPHYTE("raw_chlorophyte", () -> new BaseItem(ORANGE,true)),
    SHROOMITE_INGOT("shroomite_ingot", () -> new BaseItem(ORANGE,true)),
    SPECTRE_INGOT("spectre_ingot", () -> new BaseItem(ORANGE,true)),
    RAW_LUMINITE("raw_luminite", () -> new BaseItem(ORANGE,true)),
    LUMINITE_INGOT("luminite_ingot", () -> new BaseItem(ORANGE,true)),

    AMBER("amber", () -> new BaseItem(WHITE)),
    TR_AMETHYST("tr_amethyst", () -> new BaseItem(WHITE)),
    TR_EMERALD("tr_emerald",() -> new BaseItem(WHITE)),
    RUBY("ruby", () -> new BaseItem(WHITE)),
    SAPPHIRE("sapphire", () -> new BaseItem(WHITE)),
    TOPAZ("topaz", () -> new BaseItem(WHITE)),

    EMERALD_COIN("emerald_coin", () -> new BaseItem(YELLOW)),

    STAR_PETALS("star_petals", () -> new FallingStarItem(WHITE)),
    FALLING_STAR("falling_star", () -> new FallingStarItem(WHITE)),
    WEAVING_CLOUD_COTTON("weaving_cloud_cotton", () -> new BaseItem(WHITE)),
    CARRION("carrion", () -> new BaseItem(WHITE)),
    VERTEBRA("vertebra", () -> new BaseItem(WHITE)),
    BLOOD_CLOT_POWDER("blood_clot_powder", () -> new BaseItem(WHITE)),
    BLACK_INK("black_ink", () -> new BaseItem(WHITE)),
    PURPLE_MUCUS("purple_mucus", () -> new BaseItem(WHITE)),
    SHARK_FIN("shark_fin", () -> new BaseItem(WHITE)),
    ANTLION_MANDIBLE("antlion_mandible", () -> new BaseItem(WHITE)),
    HOOK("hook", () -> new BaseItem(WHITE)),
    LENS("lens", () -> new BaseItem(WHITE)),
    BLACK_LENS("black_lens", () -> new BaseItem(WHITE)),
    LIFE_CRYSTAL("life_crystal", LifeCrystal::new),
    LIFE_FRUIT("life_fruit", LifeFruit::new),
    MANA_STAR("mana_star",ManaStar::new),
    STURDY_FOSSIL("sturdy_fossil", () -> new BaseItem(BLUE)),
    SHADOW_SCALE("shadow_scale", () -> new BaseItem(BLUE)),
    TISSUE_SAMPLE("tissue_sample", () -> new BaseItem(BLUE)),

    CRYSTAL_SHARDS_ITEM("crystal_shards_item", () -> new BaseItem(BLUE)),
    CURSED_FLAME("cursed_flame", () -> new BaseItem(ORANGE)),
    ICHOR("ichor", () -> new BaseItem(ORANGE)),
    PIXIE_DUST("pixie_dust", () -> new BaseItem(BLUE)),

    PEARL("pearl", () -> new BaseItem(BLUE)),
    BLACK_PEARL("black_pearl", () -> new BaseItem(BLUE)),
    PINK_PEARL("pink_pearl", () -> new BaseItem(BLUE));
*/

    public static DeferredItem<Item> register(String id) {
        return MATERIALS.register(id, () -> new Item(new Item.Properties()));
    }

    public static DeferredItem<Item> register(String id, ModRarity rarity) {
        return MATERIALS.register(id, () -> new CustomRarityItem(rarity));
    }
}
