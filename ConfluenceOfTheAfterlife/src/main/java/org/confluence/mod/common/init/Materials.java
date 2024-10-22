package org.confluence.mod.common.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.component.ModRarity;
import org.confluence.mod.common.item.CustomRarityItem;

public class Materials{
    public static final DeferredRegister.Items MATERIALS = DeferredRegister.createItems(Confluence.MODID);


    // todo ai自动生成的，没有检查
    public static DeferredItem<Item> GEL = register("material/gel", ModRarity.WHITE);
    public static DeferredItem<Item> PINK_GEL = register("material/pink_gel", ModRarity.PINK);

    public static DeferredItem<Item> RAW_TIN = register("material/raw_tin");
    public static DeferredItem<Item> TIN_INGOT = register("material/tin_ingot");
    public static DeferredItem<Item> RAW_LEAD = register("material/raw_lead");
    public static DeferredItem<Item> LEAD_INGOT = register("material/lead_ingot");
    public static DeferredItem<Item> RAW_SILVER = register("material/raw_silver");
    public static DeferredItem<Item> SILVER_INGOT = register("material/silver_ingot");
    public static DeferredItem<Item> RAW_TUNGSTEN = register("material/raw_tungsten");
    public static DeferredItem<Item> TUNGSTEN_INGOT = register("material/tungsten_ingot");
    public static DeferredItem<Item> RAW_PLATINUM = register("material/raw_platinum", ModRarity.BLUE);
    public static DeferredItem<Item> PLATINUM_INGOT = register("material/platinum_ingot", ModRarity.BLUE);
    public static DeferredItem<Item> RAW_METEORITE = register("material/raw_meteorite", ModRarity.BLUE);
    public static DeferredItem<Item> METEORITE_INGOT = register("material/meteorite_ingot", ModRarity.BLUE);
    public static DeferredItem<Item> RAW_EBONY = register("material/raw_ebony", ModRarity.BLUE);
    public static DeferredItem<Item> EBONY_INGOT = register("material/ebony_ingot", ModRarity.BLUE);
    public static DeferredItem<Item> RAW_TR_CRIMSON = register("material/raw_tr_crimson", ModRarity.BLUE);
    public static DeferredItem<Item> TR_CRIMSON_INGOT = register("material/tr_crimson_ingot", ModRarity.BLUE);
    public static DeferredItem<Item> HELLSTONE_INGOT = register("material/hellstone_ingot", ModRarity.ORANGE);
    public static DeferredItem<Item> RAW_HELLSTONE = register("material/raw_hellstone", ModRarity.ORANGE);
    public static DeferredItem<Item> PRIMORDIAL_HELLSTONE_INGOT = register("material/primordial_hellstone_ingot", ModRarity.ORANGE);

    public static DeferredItem<Item> RAW_COBALT = register("material/raw_cobalt", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> COBALT_INGOT = register("material/cobalt_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> RAW_PALLADIUM = register("material/raw_palladium", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> PALLADIUM_INGOT = register("material/palladium_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> RAW_MITHRIL = register("material/raw_mithril", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> MITHRIL_INGOT = register("material/mithril_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> RAW_ORICHALCUM = register("material/raw_orichalcum", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> ORICHALCUM_INGOT = register("material/orichalcum_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> RAW_ADAMANTITE = register("material/raw_adamantite", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> ADAMANTITE_INGOT = register("material/adamantite_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> RAW_TITANIUM = register("material/raw_titanium", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> TITANIUM_INGOT = register("material/titanium_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> HALLOWED_INGOT = register("material/hallowed_ingot", ModRarity.LIGHT_RED);
    public static DeferredItem<Item> CHLOROPHYTE_INGOT = register("material/chlorophyte_ingot", ModRarity.ORANGE);
    public static DeferredItem<Item> RAW_CHLOROPHYTE = register("material/raw_chlorophyte", ModRarity.ORANGE);
    public static DeferredItem<Item> SHROOMITE_INGOT = register("material/shroomite_ingot", ModRarity.ORANGE);
    public static DeferredItem<Item> SPECTRE_INGOT = register("material/spectre_ingot", ModRarity.ORANGE);
    public static DeferredItem<Item> RAW_LUMINITE = register("material/raw_luminite", ModRarity.ORANGE);
    public static DeferredItem<Item> LUMINITE_INGOT = register("material/luminite_ingot", ModRarity.ORANGE);

    public static DeferredItem<Item> AMBER = register("material/amber");
    public static DeferredItem<Item> TR_AMETHYST = register("material/tr_amethyst");
    public static DeferredItem<Item> TR_EMERALD = register("material/tr_emerald");
    public static DeferredItem<Item> RUBY = register("material/ruby");
    public static DeferredItem<Item> SAPPHIRE = register("material/sapphire");
    public static DeferredItem<Item> TOPAZ = register("material/topaz");

    public static DeferredItem<Item> EMERALD_COIN = register("material/emerald_coin");

    public static DeferredItem<Item> STAR_PETALS = register("material/star_petals");
    public static DeferredItem<Item> FALLING_STAR = register("material/falling_star");
    public static DeferredItem<Item> WEAVING_CLOUD_COTTON = register("material/weaving_cloud_cotton");
    public static DeferredItem<Item> CARRION = register("material/carrion");
    public static DeferredItem<Item> VERTEBRA = register("material/vertebra");
    public static DeferredItem<Item> BLOOD_CLOT_POWDER = register("material/blood_clot_powder");
    public static DeferredItem<Item> BLACK_INK = register("material/black_ink");
    public static DeferredItem<Item> PURPLE_MUCUS = register("material/purple_mucus");
    public static DeferredItem<Item> SHARK_FIN = register("material/shark_fin");
    public static DeferredItem<Item> ANTLION_MANDIBLE = register("material/antlion_mandible");
    public static DeferredItem<Item> HOOK = register("material/hook");
    public static DeferredItem<Item> LENS = register("material/lens");
    public static DeferredItem<Item> BLACK_LENS = register("material/black_lens");
    public static DeferredItem<Item> LIFE_CRYSTAL = register("material/life_crystal", ModRarity.WHITE);
    public static DeferredItem<Item> LIFE_FRUIT = register("material/life_fruit");
    public static DeferredItem<Item> MANA_STAR = register("material/mana_star");
    public static DeferredItem<Item> STURDY_FOSSIL = register("material/sturdy_fossil");
    public static DeferredItem<Item> SHADOW_SCALE = register("material/shadow_scale");
    public static DeferredItem<Item> TISSUE_SAMPLE = register("material/tissue_sample");

    public static DeferredItem<Item> CRYSTAL_SHARDS_ITEM = register("material/crystal_shards_item");
    public static DeferredItem<Item> CURSED_FLAME = register("material/cursed_flame");
    public static DeferredItem<Item> ICHOR = register("material/ichor");
    public static DeferredItem<Item> PIXIE_DUST = register("material/pixie_dust");

    public static DeferredItem<Item> PEARL = register("material/pearl");
    public static DeferredItem<Item> BLACK_PEARL = register("material/black_pearl");
    public static DeferredItem<Item> PINK_PEARL = register("material/pink_pearl");

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
        return MATERIALS.register(id, ()->new Item(new Item.Properties()));
    }
    public static DeferredItem<Item> register(String id,ModRarity rarity) {
        return MATERIALS.register(id, ()->new CustomRarityItem(new Item.Properties(), rarity));
    }

}
