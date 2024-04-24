package org.confluence.mod.item.food;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Foods implements EnumRegister<Item> {
    //常规食物
    COOKED_MARSHMALLOW("cooked_marshmallow", () -> new BaseFoodItem(FoodType.MEDIUM)),
    //水果
    APRICOT("apricot", () -> new BaseFoodItem(FoodType.LOW)),
    BANANA("banana", () -> new BaseFoodItem(FoodType.LOW)),
    CHERRY("cherry", () -> new BaseFoodItem(FoodType.LOW)),
    COCONUT("coconut", () -> new BaseFoodItem(FoodType.LOW)),
    DRAGON_FRUIT("dragon_fruit", () -> new BaseFoodItem(FoodType.LOW)),
    GRAPE_FRUIT("grape_fruit", () -> new BaseFoodItem(FoodType.LOW)),
    LEMON("lemon", () -> new BaseFoodItem(FoodType.LOW)),
    MANGO("mango", () -> new BaseFoodItem(FoodType.LOW)),
    PEACH("peach", () -> new BaseFoodItem(FoodType.LOW)),
    PINEAPPLE("pineapple", () -> new BaseFoodItem(FoodType.LOW)),
    PLUM("plum", () -> new BaseFoodItem(FoodType.LOW)),
    SPICY_PEPPER("spicy_pepper", () -> new BaseFoodItem(FoodType.LOW)),
    STAR_FRUIT("star_fruit", () -> new BaseFoodItem(FoodType.LOW)),
    POMEGRANATE("pomegranate", () -> new BaseFoodItem(FoodType.LOW)),
    RAMBUTAN("rambutan", () -> new BaseFoodItem(FoodType.LOW)),
    BLOOD_ORANGE("blood_orange", () -> new BaseFoodItem(FoodType.LOW)),
    ELDERBERRY("elderberry", () -> new BaseFoodItem(FoodType.LOW)),
    BLACKCURRANT("blackcurrant", () -> new BaseFoodItem(FoodType.LOW)),
    //果汁
    BLOODY_MOSCATO("bloody_moscato", () -> new BottleFoodItem(FoodType.MEDIUM)),
    APPLE_JUICE("apple_juice", () -> new BottleFoodItem(FoodType.MEDIUM)),
    //鱼
    SEA_BASS("sea_bass", () -> new BaseFoodItem(FoodType.FISH)),
    ATLANTIC_COD("atlantic_cod", () -> new BaseFoodItem(FoodType.FISH)),
    FROSTY_MINNOW("frosty_minnow", () -> new BaseFoodItem(FoodType.FISH)),
    PISCES_FIN_COD("pisces_fin_cod", () -> new BaseFoodItem(FoodType.FISH)),
    PARTIAL_MOUTH_FISH("partial_mouth_fish", () -> new BaseFoodItem(FoodType.FISH)),
    ROCK_LOBSTER("rock_lobster", () -> new BaseFoodItem(FoodType.FISH)),
    SHRIMP("shrimp", () -> new BaseFoodItem(FoodType.FISH)),
    SALMON("salmon", () -> new BaseFoodItem(FoodType.FISH)),
    TUNA("tuna", () -> new BaseFoodItem(FoodType.FISH)),
    RED_SNAPPER("red_snapper", () -> new BaseFoodItem(FoodType.FISH)),
    TROUT("trout", () -> new BaseFoodItem(FoodType.FISH)),
    ARMORED_CAVE_FISH("armored_cave_fish", () -> new BaseFoodItem(FoodType.FISH)),
    MIRROR_FISH("mirror_fish", () -> new BaseFoodItem(FoodType.FISH)),
    STINKY_FISH("stinky_fish", () -> new BaseFoodItem(FoodType.FISH)),
    NEON_GREASE_CARP("neon_grease_carp", () -> new BaseFoodItem(FoodType.FISH)),
    DAMSEL_FISH("damsel_fish", () -> new BaseFoodItem(FoodType.FISH)),
    EBONY_KOI("ebony_koi", () -> new BaseFoodItem(FoodType.FISH)),
    SCARLET_TIGER_FISH("scarlet_tiger_fish", () -> new BaseFoodItem(FoodType.FISH)),
    BLOODY_PIRANHAS("bloody_piranhas", () -> new BaseFoodItem(FoodType.FISH)),
    PRINCESS_FISH("princess_fish", () -> new BaseFoodItem(FoodType.FISH)),
    COLORFUL_MINERAL_FISH("colorful_mineral_fish", () -> new BaseFoodItem(FoodType.FISH)),
    CHAOS_FISH("chaos_fish", () -> new BaseFoodItem(FoodType.FISH)),
    MOTTLED_OILFISH("mottled_oilfish", () -> new BaseFoodItem(FoodType.FISH)),
    GOLDEN_CARP("golden_carp", () -> new BaseFoodItem(FoodType.GOLDEN_CARP)),
    OBSIDIAN_FISH("obsidian_fish", () -> new BaseFoodItem(FoodType.FISH)),
    FLASHFIN_KOI("flashfin_koi", () -> new BaseFoodItem(FoodType.FISH)),

    ;
    private final RegistryObject<Item> value;

    Foods(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering foods");
    }

}
