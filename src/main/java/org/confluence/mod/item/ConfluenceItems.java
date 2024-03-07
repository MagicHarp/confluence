package org.confluence.mod.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.axe.BaseAxeItem;
import org.confluence.mod.item.hammer.HammerAxeItem;
import org.confluence.mod.item.hammer.HammerItem;
import org.confluence.mod.item.magic.MagicMirror;
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

    public static final RegistryObject<Item> BLOCKS_ICON = ITEMS.register("blocks_icon", BaseItem::new);
    public static final RegistryObject<Item> FALLING_STAR = ITEMS.register("falling_star", BaseItem::new);
    public static final RegistryObject<Item> MAGIC_ICON = ITEMS.register("magic_icon", BaseItem::new);
    public static final RegistryObject<Item> MELEE_ICON = ITEMS.register("melee_icon", BaseItem::new);
    public static final RegistryObject<Item> REMOTE_ICON = ITEMS.register("remote_icon", BaseItem::new);

    public static final RegistryObject<Item> SPOOKY_WOOD = ITEMS.register("spooky_wood", BaseItem::new);
    public static final RegistryObject<Item> SUMMON_ICON = ITEMS.register("summon_icon", BaseItem::new);
    public static final RegistryObject<Item> TOOLS_ICON = ITEMS.register("tools_icon", BaseItem::new);
    public static final RegistryObject<Item> APPLE_JUICE = ITEMS.register("apple_juice", BaseItem::new);
    public static final RegistryObject<Item> BLACKCURRANT = ITEMS.register("blackcurrant", BaseItem::new);
    public static final RegistryObject<Item> BLOOD_ORANGE = ITEMS.register("blood_orange", BaseItem::new);
    public static final RegistryObject<Item> BLOODY_MOSCATO = ITEMS.register("bloody_moscato", BaseItem::new);
    public static final RegistryObject<Item> CARRION = ITEMS.register("carrion", BaseItem::new);
    public static final RegistryObject<Item> CRYSTAL_SHARDS_ITEM = ITEMS.register("crystal_shards_item", BaseItem::new);
    public static final RegistryObject<Item> ELDERBERRY = ITEMS.register("elderberry", BaseItem::new);
    public static final RegistryObject<Item> ICE_MIRROR = ITEMS.register("ice_mirror", BaseItem::new);
    public static final RegistryObject<Item> LESSER_HEALING_POTION = ITEMS.register("lesser_healing_potion", BaseItem::new);
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror", MagicMirror::new);
    public static final RegistryObject<Item> ROPE = ITEMS.register("rope", BaseItem::new);
    public static final RegistryObject<Item> ROPE_COIL = ITEMS.register("rope_coil", BaseItem::new);
    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken", BaseItem::new);
    public static final RegistryObject<ManaStar> MANA_STAR = ITEMS.register("mana_star", ManaStar::new);


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
        METEORITE_INGOT("meteorite_ingot"),

        AMBER("amber"),
        ANOTHER_AMETHYST("another_amethyst"),
        ANOTHER_EMERALD("another_emerald"),
        RUBY("ruby"),
        SAPPHIRE("sapphire"),
        TOPAZ("topaz");

        private final RegistryObject<Item> value;

        Materials(String id) {
            this.value = ITEMS.register(id, BaseItem::new);
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
        BLUE_SLIME_BALL("blue_slime_ball", BaseItem::new);

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
