package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.mana.ManaStar;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Materials implements EnumRegister<Item> {
    RAW_TIN("raw_tin"),
    TIN_INGOT("tin_ingot"),
    RAW_LEAD("raw_lead"),
    LEAD_INGOT("lead_ingot"),
    RAW_SILVER("raw_silver"),
    SILVER_INGOT("silver_ingot"),
    RAW_TUNGSTEN("raw_tungsten"),
    TUNGSTEN_INGOT("tungsten_ingot"),
    RAW_PLATINUM("raw_platinum"),
    PLATINUM_INGOT("platinum_ingot"),
    RAW_METEORITE("raw_meteorite"),
    METEORITE_INGOT("meteorite_ingot", () -> new TooltipItem(Component.translatable("item.confluence.meteorite_ingot.tooltip"))),
    RAW_EBONY("raw_ebony"),
    EBONY_INGOT("ebony_ingot"),
    RAW_ANOTHER_CRIMSON("raw_another_crimson"),
    ANOTHER_CRIMSON_INGOT("another_crimson_ingot"),
    HELLSTONE_INGOT("hellstone_ingot"),
    RAW_HELLSTONE("raw_hellstone"),
    PRIMORDIAL_HELLSTONE_INGOT("primordial_hellstone_ingot"),

    RAW_COBALT("raw_cobalt"),
    COBALT_INGOT("cobalt_ingot"),
    RAW_PALLADIUM("raw_palladium"),
    PALLADIUM_INGOT("palladium_ingot"),
    RAW_MITHRIL("raw_mithril"),
    MITHRIL_INGOT("mithril_ingot"),
    RAW_ORICHALCUM("raw_orichalcum"),
    ORICHALCUM_INGOT("orichalcum_ingot"),
    RAW_ADAMANTITE("raw_adamantite"),
    ADAMANTITE_INGOT("adamantite_ingot"),
    RAW_TITANIUM("raw_titanium"),
    TITANIUM_INGOT("titanium_ingot"),

    AMBER("amber"),
    ANOTHER_AMETHYST("another_amethyst"),
    //ANOTHER_EMERALD("another_emerald"),
    RUBY("ruby"),
    SAPPHIRE("sapphire"),
    TOPAZ("topaz"),

    FALLING_STAR("falling_star"),
    CARRION("carrion"),
    BLACK_INK("black_ink"),
    PURPLE_MUCUS("purple_mucus"),
    SHARK_FIN("shark_fin"),
    ANTLION_MANDIBLE("antlion_mandible"),
    BLINKROOT_GAINS("blinkroot_gains"),
    DAYBLOOM_POLLEN("daybloom_pollen"),
    DEATHWEED_FLESH("deathweed_flesh"),
    MOONGLOW_PETAL("moonglow_petal"),
    SHIVERTHRON_SHARD("shiverthron_shard"),
    WATERLEAF_POT("waterleaf_pot"),
    FIREBLOSSOM_BUD("fireblossom_bud"),
    CRYSTALLINE_LENS("crystalline_lens"),
    BLACK_LENS("black_lens"),
    LIFE_CRYSTAL("life_crystal", LifeCrystal::new),
    MANA_STAR("mana_star", ManaStar::new),
    SHADOW_SCALE("shadow_scale"),
    TISSUE_SAMPLE("tissue_sample"),

    CRYSTAL_SHARDS_ITEM("crystal_shards_item"),
    CURSED_FLAME("cursed_flame"),
    ICHOR("ichor"),

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

    public static void init() {
        Confluence.LOGGER.info("Registering materials");
    }
}
