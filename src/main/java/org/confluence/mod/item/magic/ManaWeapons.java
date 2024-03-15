package org.confluence.mod.item.magic;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.common.BaseItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum ManaWeapons implements EnumRegister<Item> {
    WOND_OF_SPARKING("wond_of_sparking", BaseItem::new),
    WOND_OF_FROSTING("wond_of_frosting", BaseItem::new),
    RUBY_STAFF("ruby_staff", () -> new StaffItem(new Item.Properties())),
    AMBER_STAFF("amber_staff", () -> new StaffItem(new Item.Properties())),
    TOPAZ_STAFF("topaz_staff", () -> new StaffItem(new Item.Properties())),
    EMERALD_STAFF("emerald_staff", () -> new StaffItem(new Item.Properties())),
    SAPPHIRE_STAFF("sapphire_staff", () -> new StaffItem(new Item.Properties())),
    AMETHYST_STAFF("amethyst_staff", () -> new StaffItem(new Item.Properties())),
    DIAMOND_STAFF("diamond_staff", () -> new StaffItem(new Item.Properties())),
    AQUA_SCEPTER("aqua_scepter", BaseItem::new);

    private final RegistryObject<Item> value;

    ManaWeapons(String id, Supplier<Item> item) {
        this.value = ConfluenceItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering Weapons");
    }
}
