package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.mana.ManaStar;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Materials implements EnumRegister<Item> {
    RAW_TIN("raw_tin",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    TIN_INGOT("tin_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    RAW_LEAD("raw_lead",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    LEAD_INGOT("lead_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    RAW_SILVER("raw_silver",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    SILVER_INGOT("silver_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    RAW_TUNGSTEN("raw_tungsten",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    TUNGSTEN_INGOT("tungsten_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    RAW_PLATINUM("raw_platinum",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    PLATINUM_INGOT("platinum_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    RAW_METEORITE("raw_meteorite",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    METEORITE_INGOT("meteorite_ingot", () -> new TooltipItem(Component.translatable("item.confluence.meteorite_ingot.tooltip"))),
    RAW_EBONY("raw_ebony",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    EBONY_INGOT("ebony_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    RAW_ANOTHER_CRIMSON("raw_another_crimson",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    ANOTHER_CRIMSON_INGOT("another_crimson_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    HELLSTONE_INGOT("hellstone_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.ORANGE))),
    RAW_HELLSTONE("raw_hellstone",() -> new Item(new Item.Properties().rarity(ModRarity.ORANGE))),
    PRIMORDIAL_HELLSTONE_INGOT("primordial_hellstone_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.ORANGE))),

    RAW_COBALT("raw_cobalt",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    COBALT_INGOT("cobalt_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    RAW_PALLADIUM("raw_palladium",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    PALLADIUM_INGOT("palladium_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    RAW_MITHRIL("raw_mithril",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    MITHRIL_INGOT("mithril_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    RAW_ORICHALCUM("raw_orichalcum",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    ORICHALCUM_INGOT("orichalcum_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    RAW_ADAMANTITE("raw_adamantite",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    ADAMANTITE_INGOT("adamantite_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    RAW_TITANIUM("raw_titanium",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),
    TITANIUM_INGOT("titanium_ingot",() -> new Item(new Item.Properties().rarity(ModRarity.LIGHT_RED))),

    AMBER("amber",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    ANOTHER_AMETHYST("another_amethyst",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    //ANOTHER_EMERALD("another_emerald"),
    RUBY("ruby",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    SAPPHIRE("sapphire",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    TOPAZ("topaz",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),

    FALLING_STAR("falling_star",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    CARRION("carrion",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    BLACK_INK("black_ink",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    PURPLE_MUCUS("purple_mucus",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    SHARK_FIN("shark_fin",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    ANTLION_MANDIBLE("antlion_mandible",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    BLINKROOT_GAINS("blinkroot_gains",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    DAYBLOOM_POLLEN("daybloom_pollen",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    DEATHWEED_FLESH("deathweed_flesh",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    MOONGLOW_PETAL("moonglow_petal",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    SHIVERTHRON_SHARD("shiverthron_shard",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    WATERLEAF_POT("waterleaf_pot",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    FIREBLOSSOM_BUD("fireblossom_bud",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    LENS("lens",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    BLACK_LENS("black_lens",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    LIFE_CRYSTAL("life_crystal", LifeCrystal::new),
    LIFE_FRUIT("life_fruit", LifeFruit::new),
    MANA_STAR("mana_star", ManaStar::new),
    SHADOW_SCALE("shadow_scale",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    TISSUE_SAMPLE("tissue_sample",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),

    CRYSTAL_SHARDS_ITEM("crystal_shards_item",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    CURSED_FLAME("cursed_flame",() -> new Item(new Item.Properties().rarity(ModRarity.ORANGE))),
    ICHOR("ichor",() -> new Item(new Item.Properties().rarity(ModRarity.ORANGE))),

    PEARL("pearl",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    BLACK_PEARL("black_pearl",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE))),
    PINK_PEARL("pink_pearl",() -> new Item(new Item.Properties().rarity(ModRarity.BLUE)));

    ;

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

    public static void init() {}
}
