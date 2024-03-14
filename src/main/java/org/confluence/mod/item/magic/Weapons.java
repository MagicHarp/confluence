package org.confluence.mod.item.magic;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.common.BaseItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Weapons implements EnumRegister<Item> {

    WOND_OF_SPARKING("wond_of_sparking", BaseItem::new),
    WOND_OF_FROSTING("wond_of_frosting", BaseItem::new),
     RUBY_STAFF("ruby_staff", BaseItem::new),
    AMBER_STAFF("amber_staff", BaseItem::new),
    TOPAZ_STAFF("topaz_staff", BaseItem::new),
    EMERALD_STAFF("emerald_staff", BaseItem::new),
    SAPPHIRE_STAFF("sapphire_staff", BaseItem::new),
   AMETHYST_STAFF("amethyst_staff", BaseItem::new),
    DIAMOND_STAFF("diamond_staff", BaseItem::new),
    AQUA_SCEPTER("aqua_scepter", BaseItem::new),






    ;




    private final RegistryObject<Item> value;

    Weapons(String id,Supplier<Item> item) {
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
