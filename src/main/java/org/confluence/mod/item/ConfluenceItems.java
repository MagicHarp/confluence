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
import org.confluence.mod.item.pickaxe.BasePickaxeItem;
import org.confluence.mod.item.sword.BoardSwordItem;
import org.confluence.mod.item.sword.ShortSwordItem;

@SuppressWarnings("unused")
public class ConfluenceItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", BaseItem::new);
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", BaseItem::new);
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", BaseItem::new);
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot", BaseItem::new);

    public enum Swords {
        COPPER_SHORT_SWORD("copper_short_sword", new ShortSwordItem(ConfluenceTiers.COPPER, 2, 3)),
        COPPER_BOARD_SWORD("copper_board_sword", new BoardSwordItem(ConfluenceTiers.COPPER, 4, 1.6F));

        private final RegistryObject<SwordItem> value;

        Swords(String id, SwordItem sword) {
            this.value = ITEMS.register(id, () -> sword);
        }

        public RegistryObject<SwordItem> getValue() {
            return value;
        }

        public SwordItem get() {
            return value.get();
        }
    }

    public enum Axes {
        COPPER_AXE("copper_axe", new BaseAxeItem(ConfluenceTiers.COPPER, 2, 1));

        private final RegistryObject<AxeItem> value;

        Axes(String id, AxeItem axe) {
            this.value = ITEMS.register(id, () -> axe);
        }

        public RegistryObject<AxeItem> getValue() {
            return value;
        }

        public AxeItem get() {
            return value.get();
        }
    }

    public enum Pickaxes {
        COPPER_PICKAXE("copper_pickaxe", new BasePickaxeItem(ConfluenceTiers.COPPER, 2, 1));

        private final RegistryObject<PickaxeItem> value;

        Pickaxes(String id, PickaxeItem pickaxe) {
            this.value = ITEMS.register(id, () -> pickaxe);
        }

        public RegistryObject<PickaxeItem> getValue() {
            return value;
        }

        public PickaxeItem get() {
            return value.get();
        }
    }
}
