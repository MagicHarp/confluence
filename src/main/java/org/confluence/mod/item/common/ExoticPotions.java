package org.confluence.mod.item.common;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum ExoticPotions implements EnumRegister<Item> {
    /*

    AMMO_RESERVATION_POTION("ammo_reservation_potion", BaseItem::new),
    ARCHERY_POTION("archery_potion", BaseItem::new),
    BATTLE_POTION("battle_potion", BaseItem::new),
    BOTTLED_HONEY("bottled_honey", BaseItem::new),
    BUILDER_POTION("builder_potion", BaseItem::new),
    CALMING_POTION("calming_potion", BaseItem::new),
    CRATE_POTION("crate_potion", BaseItem::new),
    DANGERSENSE_POTION("dangersense_potion", BaseItem::new),
    ENDURANCE_POTION("endurance_potion", BaseItem::new),
    FEATHERFALL_POTION("featherfall_potion", BaseItem::new),
    FISHING_POTION("fishing_potion", BaseItem::new),
    GILLS_POTION("gills_potion", BaseItem::new),
    GRAVITATION_POTION("gravitation_potion", BaseItem::new),
    GREATER_HEALING_POTION("greater_healing_potion", BaseItem::new),
    LUCK_POTION("luck_potion", BaseItem::new),
    LESSER_LUCK_POTION("lesser_luck_potion", BaseItem::new),
    GREATER_LUCK_POTION("greater_luck_potion", BaseItem::new),
    HEARTREACH_POTION("heartreach_potion", BaseItem::new),
    HUNTER_POTION("hunter_potion", BaseItem::new),
    INFERNO_POTION("inferno_potion", BaseItem::new),
    INVISIBILITY_POTION("invisibility_potion", BaseItem::new),
    IRON_SKIN_POTION("iron_skin_potion", BaseItem::new),
    LIFEFORCE_POTION("lifeforce_potion", BaseItem::new),
    MINING_POTION("mining_potion", BaseItem::new),
    SONAR_POTION("sonar_potion", BaseItem::new),
    SPELUNKER_POTION("spelunker_potion", BaseItem::new),
    STRANGE_BREW("strange_brew", BaseItem::new),
    SUMMONING_POTION("summoning_potion", BaseItem::new),
    SWIFTNESS_POTION("swiftness_potion", BaseItem::new),
    TITAN_POTION("titan_potion", BaseItem::new),
    WARMTH_POTION("warmth_potion", BaseItem::new),
    WATER_WALKING_POTION("water_walking_potion", BaseItem::new),
    WRATH_POTION("wrath_potion", BaseItem::new),



    ;
*/

;

    private final RegistryObject<Item> value;

    ExoticPotions(String id, Supplier<Item> item) {
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
