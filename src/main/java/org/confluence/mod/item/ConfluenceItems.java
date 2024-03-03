package org.confluence.mod.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.axe.BaseAxeItem;
import org.confluence.mod.item.hammer.HammerAxeItem;
import org.confluence.mod.item.pickaxe.BasePickaxeItem;
import org.confluence.mod.item.sword.BoardSwordItem;
import org.confluence.mod.item.sword.ShortSwordItem;
import org.confluence.mod.util.EnumRegister;

@SuppressWarnings("unused")
public class ConfluenceItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    public enum Ores {
        RAW_TIN("raw_tin", new BaseItem()),
        TIN_INGOT("tin_ingot", new BaseItem()),
        RAW_WOLFRAM("raw_wolfram", new BaseItem()),
        WOLFRAM_INGOT("wolfram_ingot", new BaseItem()),
        RAW_SILVER("raw_silver", new BaseItem()),
        SILVER_INGOT("silver_ingot", new BaseItem()),
        RAW_PLATINUM("raw_platinum", new BaseItem()),
        PLATINUM_INGOT("platinum_ingot", new BaseItem()),
        METEORITE_INGOT("meteorite_ingot", new BaseItem());

        private final RegistryObject<Item> value;

        Ores(String id, Item ore) {
            this.value = ITEMS.register(id, () -> ore);
        }

        public RegistryObject<Item> getValue() {
            return value;
        }

        public Item get() {
            return value.get();
        }
    }

    public enum Swords implements EnumRegister<SwordItem> {
        COPPER_SHORT_SWORD("copper_short_sword", new ShortSwordItem(ConfluenceTiers.COPPER, 2, 3)),
        COPPER_BOARD_SWORD("copper_board_sword", new BoardSwordItem(ConfluenceTiers.COPPER, 4, 1.6F)),
        TIN_SHORT_SWORD("tin_short_sword", new ShortSwordItem(ConfluenceTiers.TIN, 3, 3)),
        TIN_BOARD_SWORD("tin_board_sword", new BoardSwordItem(ConfluenceTiers.TIN, 5, 1.6F)),
        WOLFRAM_SHORT_SWORD("wolfram_short_sword", new ShortSwordItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        WOLFRAM_BOARD_SWORD("wolfram_board_sword", new BoardSwordItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        SILVER_SHORT_SWORD("silver_short_sword", new ShortSwordItem(ConfluenceTiers.SILVER, 0, 0)),
        SILVER_BOARD_SWORD("silver_board_sword", new BoardSwordItem(ConfluenceTiers.SILVER, 0, 0)),
        PLATINUM_SHORT_SWORD("platinum_short_sword", new ShortSwordItem(ConfluenceTiers.PLATINUM, 0, 0)),
        PLATINUM_BOARD_SWORD("platinum_board_sword", new BoardSwordItem(ConfluenceTiers.PLATINUM, 0, 0));

        private final RegistryObject<SwordItem> value;

        Swords(String id, SwordItem sword) {
            this.value = ITEMS.register(id, () -> sword);
        }

        @Override
        public RegistryObject<SwordItem> getValue() {
            return value;
        }

        @Override
        public SwordItem get() {
            return value.get();
        }
    }

    public enum Axes implements EnumRegister<AxeItem> {
        COPPER_AXE("copper_axe", new BaseAxeItem(ConfluenceTiers.COPPER, 2, 1)),
        TIN_AXE("tin_axe", new BaseAxeItem(ConfluenceTiers.TIN, 0, 0)),
        WOLFRAM_AXE("wolfram_axe", new BaseAxeItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        SILVER_AXE("silver_axe", new BaseAxeItem(ConfluenceTiers.SILVER, 0, 0)),
        PLATINUM_AXE("platinum_axe", new BaseAxeItem(ConfluenceTiers.PLATINUM, 0, 0));

        private final RegistryObject<AxeItem> value;

        Axes(String id, AxeItem axe) {
            this.value = ITEMS.register(id, () -> axe);
        }

        @Override
        public RegistryObject<AxeItem> getValue() {
            return value;
        }

        @Override
        public AxeItem get() {
            return value.get();
        }
    }

    public enum Pickaxes implements EnumRegister<PickaxeItem> {
        COPPER_PICKAXE("copper_pickaxe", new BasePickaxeItem(ConfluenceTiers.COPPER, 2, 1)),
        TIN_PICKAXE("tin_pickaxe", new BasePickaxeItem(ConfluenceTiers.TIN, 0, 0)),
        WOLFRAM_PICKAXE("wolfram_pickaxe", new BasePickaxeItem(ConfluenceTiers.WOLFRAM, 0, 0)),
        SILVER_PICKAXE("silver_pickaxe", new BasePickaxeItem(ConfluenceTiers.SILVER, 0, 0)),
        PLATINUM_PICKAXE("platinum_pickaxe", new BasePickaxeItem(ConfluenceTiers.PLATINUM, 0, 0));

        private final RegistryObject<PickaxeItem> value;

        Pickaxes(String id, PickaxeItem pickaxe) {
            this.value = ITEMS.register(id, () -> pickaxe);
        }

        @Override
        public RegistryObject<PickaxeItem> getValue() {
            return value;
        }

        @Override
        public PickaxeItem get() {
            return value.get();
        }
    }

    public enum HammerAxes implements EnumRegister<HammerAxeItem> {
        ;

        private final RegistryObject<HammerAxeItem> value;

        HammerAxes(String id, HammerAxeItem hammerAxe) {
            this.value = ITEMS.register(id, () -> hammerAxe);
        }

        @Override
        public RegistryObject<HammerAxeItem> getValue() {
            return value;
        }

        @Override
        public HammerAxeItem get() {
            return value.get();
        }
    }
}
