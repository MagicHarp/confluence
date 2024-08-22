package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.mana.ManaStar;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

import static org.confluence.mod.misc.ModRarity.*;

public enum Materials implements EnumRegister<Item> {
    GEL("gel", () -> new ColoredItem(WHITE)),
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
    //TR_EMERALD("tr_emerald"),
    RUBY("ruby", () -> new BaseItem(WHITE)),
    SAPPHIRE("sapphire", () -> new BaseItem(WHITE)),
    TOPAZ("topaz", () -> new BaseItem(WHITE)),

    FALLING_STAR("falling_star", () -> new FallingStarItem(WHITE)),
    CARRION("carrion", () -> new BaseItem(WHITE)),
    VERTEBRA("vertebra", () -> new BaseItem(WHITE)),
    BLACK_INK("black_ink", () -> new BaseItem(WHITE)),
    PURPLE_MUCUS("purple_mucus", () -> new BaseItem(WHITE)),
    SHARK_FIN("shark_fin", () -> new BaseItem(WHITE)),
    ANTLION_MANDIBLE("antlion_mandible", () -> new BaseItem(WHITE)),
    HOOK("hook", () -> new BaseItem(WHITE)),
    LENS("lens", () -> new BaseItem(WHITE)),
    BLACK_LENS("black_lens", () -> new BaseItem(WHITE)),
    LIFE_CRYSTAL("life_crystal", LifeCrystal::new),
    LIFE_FRUIT("life_fruit", LifeFruit::new),
    MANA_STAR("mana_star", ManaStar::new),
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

    private final RegistryObject<Item> value;

    Materials(String id) {
        this.value = ModItems.ITEMS.register(id, BaseItem::new);
    }

    Materials(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
    }
}
