package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.magic.ManaStar;
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
    CRYSTALLINE_LENS("crystalline_lens"),
    CRYSTAL_SHARDS_ITEM("crystal_shards_item"),
    MANA_STAR("mana_star", ManaStar::new);

    private final RegistryObject<Item> value;

    Materials(String id) {
        this.value = ConfluenceItems.ITEMS.register(id, BaseItem::new);
    }

    Materials(String id, Supplier<Item> item) {
        this.value = ConfluenceItems.ITEMS.register(id, item);
    }

    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering materials");
    }
}
