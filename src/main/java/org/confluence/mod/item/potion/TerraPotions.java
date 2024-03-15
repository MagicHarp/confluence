package org.confluence.mod.item.potion;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum TerraPotions implements EnumRegister<AbstractPotion> {
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
    */
    LESSER_HEALING_POTION("lesser_healing_potion", () -> new HealingPotion(50, new Item.Properties().rarity(Rarity.COMMON))),
    HEALING_POTION("healing_potion", () -> new HealingPotion(100, new Item.Properties().rarity(Rarity.UNCOMMON))),
    GREATER_HEALING_POTION("greater_healing_potion", () -> new HealingPotion(200, new Item.Properties().rarity(Rarity.RARE))),
    SUPER_HEALING_POTION("super_healing_potion", () -> new HealingPotion(300, new Item.Properties().rarity(Rarity.EPIC))),

    LESSER_MANA_POTION("lesser_mana_potion", () -> new ManaPotion(50, new Item.Properties().rarity(Rarity.COMMON))),
    MANA_POTION("mana_potion", () -> new ManaPotion(100, new Item.Properties().rarity(Rarity.UNCOMMON))),
    GREATER_MANA_POTION("greater_mana_potion", () -> new ManaPotion(200, new Item.Properties().rarity(Rarity.RARE))),
    SUPER_MANA_POTION("super_mana_potion", () -> new ManaPotion(300, new Item.Properties().rarity(Rarity.EPIC)));

    private final RegistryObject<AbstractPotion> value;

    TerraPotions(String id, Supplier<AbstractPotion> item) {
        this.value = ConfluenceItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<AbstractPotion> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering Weapons");
    }
}
