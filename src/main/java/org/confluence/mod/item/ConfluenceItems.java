package org.confluence.mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.axe.BaseAxeItem;
import org.confluence.mod.item.common.BaseItem;
import org.confluence.mod.item.common.HealingPotion;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.common.TooltipItem;
import org.confluence.mod.item.hammer.HammerAxeItem;
import org.confluence.mod.item.hammer.HammerItem;
import org.confluence.mod.item.magic.MagicMirror;
import org.confluence.mod.item.magic.ManaPotion;
import org.confluence.mod.item.magic.ManaStar;
import org.confluence.mod.item.pickaxe.BasePickaxeItem;
import org.confluence.mod.item.sword.BoardSwordItem;
import org.confluence.mod.item.sword.LightSaber;
import org.confluence.mod.item.sword.ShortSwordItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;


@SuppressWarnings("unused")
public class ConfluenceItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    //  food
    public static final RegistryObject<Item> APPLE_JUICE = ITEMS.register("apple_juice", BaseItem::new);
    public static final RegistryObject<Item> BLACKCURRANT = ITEMS.register("blackcurrant", BaseItem::new);
    public static final RegistryObject<Item> BLOOD_ORANGE = ITEMS.register("blood_orange", BaseItem::new);
    public static final RegistryObject<Item> BLOODY_MOSCATO = ITEMS.register("bloody_moscato", BaseItem::new);
    public static final RegistryObject<Item> ELDERBERRY = ITEMS.register("elderberry", BaseItem::new);
    //  tool
    public static final RegistryObject<MagicMirror> ICE_MIRROR = ITEMS.register("ice_mirror", MagicMirror::new);
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror", MagicMirror::new);
    public static final RegistryObject<Item> ROPE = ITEMS.register("rope", BaseItem::new);
    public static final RegistryObject<Item> ROPE_COIL = ITEMS.register("rope_coil", BaseItem::new);
    //  Potion
    public static final RegistryObject<HealingPotion> LESSER_HEALING_POTION = ITEMS.register("lesser_healing_potion", () -> new HealingPotion(50, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<HealingPotion> HEALING_POTION = ITEMS.register("healing_potion", () -> new HealingPotion(100, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<HealingPotion> GREATER_HEALING_POTION = ITEMS.register("greater_healing_potion", () -> new HealingPotion(200, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<HealingPotion> SUPER_HEALING_POTION = ITEMS.register("super_healing_potion", () -> new HealingPotion(300, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<ManaPotion> LESSER_MANA_POTION = ITEMS.register("lesser_mana_potion", () -> new ManaPotion(50, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<ManaPotion> MANA_POTION = ITEMS.register("mana_potion", () -> new ManaPotion(100, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ManaPotion> GREATER_MANA_POTION = ITEMS.register("greater_mana_potion", () -> new ManaPotion(200, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<ManaPotion> SUPER_MANA_POTION = ITEMS.register("super_mana_potion", () -> new ManaPotion(300, new Item.Properties().rarity(Rarity.EPIC)));
    //  Throwable
    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken", BaseItem::new);


    public enum Icons implements EnumRegister<IconItem> {
        ITEM_ICON("item_icon"),
        BLOCKS_ICON("blocks_icon"),
        MAGIC_ICON("magic_icon"),
        MELEE_ICON("melee_icon"),
        REMOTE_ICON("remote_icon"),
        ARMOR_ICON("armor_icon"),
        CREATIVE_ICON("creative_icon"),
        DEVELOPER_ICON("developer_icon"),
        ENEMY_ICON("enemy_icon"),
        FOOD_ICON("food_icon"),
        NATURE_ICON("nature_icon"),
        POTION_ICON("potion_icon"),
        PRECIOUS_ICON("precious_icon"),
        SPOOKY_WOOD("spooky_wood"),
        SUMMON_ICON("summon_icon"),
        TOOLS_ICON("tools_icon");

        private final RegistryObject<IconItem> value;

        Icons(String id) {
            this.value = ITEMS.register(id, IconItem::new);
        }

        @Override
        public RegistryObject<IconItem> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering icon items");
        }
    }

    public enum Materials implements EnumRegister<Item> {
        RAW_TIN("raw_tin"),
        TIN_INGOT("tin_ingot"),
        RAW_LEAD("raw_lead"),
        LEAD_INGOT("lead_ingot"),
        RAW_SILVER("raw_silver"),
        SILVER_INGOT("silver_ingot"),
        RAW_WOLFRAM("raw_wolfram"),
        WOLFRAM_INGOT("wolfram_ingot"),
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
        ANOTHER_EMERALD("another_emerald"),
        RUBY("ruby"),
        SAPPHIRE("sapphire"),
        TOPAZ("topaz"),

        FALLING_STAR("falling_star"),
        CARRION("carrion"),
        CRYSTAL_SHARDS_ITEM("crystal_shards_item"),
        MANA_STAR("mana_star", ManaStar::new);

        private final RegistryObject<Item> value;

        Materials(String id) {
            this.value = ITEMS.register(id, BaseItem::new);
        }

        Materials(String id, Supplier<Item> item) {
            this.value = ITEMS.register(id, item);
        }

        public RegistryObject<Item> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering materials");
        }
    }

    public enum Swords implements EnumRegister<SwordItem> {
        COPPER_SHORT_SWORD("copper_short_sword", () -> new ShortSwordItem(ConfluenceTiers.COPPER, 2, 3)),
        COPPER_BOARD_SWORD("copper_board_sword", () -> new BoardSwordItem(ConfluenceTiers.COPPER, 4, 1.6F)),
        TIN_SHORT_SWORD("tin_short_sword", () -> new ShortSwordItem(ConfluenceTiers.TIN, 3, 3)),
        TIN_BOARD_SWORD("tin_board_sword", () -> new BoardSwordItem(ConfluenceTiers.TIN, 5, 1.6F)),
        LEAD_SHORT_SWORD("lead_short_sword", () -> new ShortSwordItem(ConfluenceTiers.LEAD, 0, 0)),
        LEAD_BOARD_SWORD("lead_board_sword", () -> new BoardSwordItem(ConfluenceTiers.LEAD, 0, 0)),
        SILVER_SHORT_SWORD("silver_short_sword", () -> new ShortSwordItem(ConfluenceTiers.SILVER, 0, 0)),
        SILVER_BOARD_SWORD("silver_board_sword", () -> new BoardSwordItem(ConfluenceTiers.SILVER, 0, 0)),
        WOLFRAM_SHORT_SWORD("wolfram_short_sword", () -> new ShortSwordItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        WOLFRAM_BOARD_SWORD("wolfram_board_sword", () -> new BoardSwordItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        PLATINUM_SHORT_SWORD("platinum_short_sword", () -> new ShortSwordItem(ConfluenceTiers.PLATINUM, 0, 0)),
        PLATINUM_BOARD_SWORD("platinum_board_sword", () -> new BoardSwordItem(ConfluenceTiers.PLATINUM, 0, 0)),

        RED_LIGHT_SABER("red_light_saber", () -> new LightSaber("red")),
        ORANGE_LIGHT_SABER("orange_light_saber", () -> new LightSaber("orange")),
        YELLOW_LIGHT_SABER("yellow_light_saber", () -> new LightSaber("yellow")),
        GREEN_LIGHT_SABER("green_light_saber", () -> new LightSaber("green")),
        BLUE_LIGHT_SABER("blue_light_saber", () -> new LightSaber("blue")),
        PURPLE_LIGHT_SABER("purple_light_saber", () -> new LightSaber("purple")),
        WHITE_LIGHT_SABER("white_light_saber", () -> new LightSaber("white"));

        private final RegistryObject<SwordItem> value;

        Swords(String id, Supplier<SwordItem> sword) {
            this.value = ITEMS.register(id, sword);
        }

        @Override
        public RegistryObject<SwordItem> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering swords");
        }
    }

    public enum Axes implements EnumRegister<AxeItem> {
        COPPER_AXE("copper_axe", () -> new BaseAxeItem(ConfluenceTiers.COPPER, 2, 1)),
        TIN_AXE("tin_axe", () -> new BaseAxeItem(ConfluenceTiers.TIN, 0, 0)),
        LEAD_AXE("lead_axe", () -> new BaseAxeItem(ConfluenceTiers.LEAD, 0, 0)),
        SILVER_AXE("silver_axe", () -> new BaseAxeItem(ConfluenceTiers.SILVER, 0, 0)),
        WOLFRAM_AXE("wolfram_axe", () -> new BaseAxeItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        PLATINUM_AXE("platinum_axe", () -> new BaseAxeItem(ConfluenceTiers.PLATINUM, 0, 0));

        private final RegistryObject<AxeItem> value;

        Axes(String id, Supplier<AxeItem> axe) {
            this.value = ITEMS.register(id, axe);
        }

        @Override
        public RegistryObject<AxeItem> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering axes");
        }
    }

    public enum Pickaxes implements EnumRegister<PickaxeItem> {
        COPPER_PICKAXE("copper_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.COPPER, 2, 1)),
        TIN_PICKAXE("tin_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.TIN, 0, 0)),
        LEAD_PICKAXE("lead_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.LEAD, 0, 0)),
        SILVER_PICKAXE("silver_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.SILVER, 0, 0)),
        WOLFRAM_PICKAXE("walfram_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        PLATINUM_PICKAXE("platinum_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.PLATINUM, 0, 0));

        private final RegistryObject<PickaxeItem> value;

        Pickaxes(String id, Supplier<PickaxeItem> pickaxe) {
            this.value = ITEMS.register(id, pickaxe);
        }

        @Override
        public RegistryObject<PickaxeItem> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering pickaxes");
        }
    }

    public enum Hammers implements EnumRegister<HammerItem> {
        ;

        private final RegistryObject<HammerItem> value;

        Hammers(String id, Supplier<HammerItem> hammer) {
            this.value = ITEMS.register(id, hammer);
        }

        @Override
        public RegistryObject<HammerItem> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering hammers");
        }
    }

    public enum HammerAxes implements EnumRegister<HammerAxeItem> {
        ;

        private final RegistryObject<HammerAxeItem> value;

        HammerAxes(String id, Supplier<HammerAxeItem> hammerAxe) {
            this.value = ITEMS.register(id, hammerAxe);
        }

        @Override
        public RegistryObject<HammerAxeItem> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering hammer-axes");
        }
    }

    public enum SlimeBalls implements EnumRegister<Item> {
        BLUE_SLIME_BALL("blue_slime_ball", BaseItem::new),
        PINK_SLIME_BALL("pink_slime_ball", BaseItem::new),
        HONEY_SLIME_BALL("honey_slime_ball", BaseItem::new);

        private final RegistryObject<Item> value;

        SlimeBalls(String id, Supplier<Item> item) {
            this.value = ITEMS.register(id, item);
        }

        @Override
        public RegistryObject<Item> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering slime balls");
        }
    }

    public static void register(IEventBus bus) {
        Icons.init();
        Materials.init();
        Swords.init();
        Axes.init();
        Pickaxes.init();
        Hammers.init();
        HammerAxes.init();
        SlimeBalls.init();
        ITEMS.register(bus);
    }
}
