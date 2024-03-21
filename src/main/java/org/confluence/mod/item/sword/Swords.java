package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.ConfluenceTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Swords implements EnumRegister<SwordItem> {
    COPPER_SHORT_SWORD("copper_short_sword", () -> new ShortSwordItem(ConfluenceTiers.COPPER, 2, 3)),
    TIN_SHORT_SWORD("tin_short_sword", () -> new ShortSwordItem(ConfluenceTiers.TIN, 3, 3)),
    LEAD_SHORT_SWORD("lead_short_sword", () -> new ShortSwordItem(ConfluenceTiers.LEAD, 3, 3)),
    SILVER_SHORT_SWORD("silver_short_sword", () -> new ShortSwordItem(ConfluenceTiers.SILVER, 4, 3)),
    TUNGSTEN_SHORT_SWORD("tungsten_short_sword", () -> new ShortSwordItem(ConfluenceTiers.TUNGSTEN, 4, 3)),
    GOLDEN_SHORT_SWORD("golden_short_sword", () -> new ShortSwordItem(ConfluenceTiers.GOLD, 5, 3)),
    PLATINUM_SHORT_SWORD("platinum_short_sword", () -> new ShortSwordItem(ConfluenceTiers.PLATINUM, 5, 3)),

    CACTUS_SWORD("cactus_sword", () -> new BoardSwordItem(ConfluenceTiers.CACTUS, 4, 1.6F)),
    COPPER_BOARD_SWORD("copper_board_sword", () -> new BoardSwordItem(ConfluenceTiers.COPPER, 4, 1.6F)),
    TIN_BOARD_SWORD("tin_board_sword", () -> new BoardSwordItem(ConfluenceTiers.TIN, 4, 1.6F)),
    LEAD_BOARD_SWORD("lead_board_sword", () -> new BoardSwordItem(ConfluenceTiers.LEAD, 5, 1.6F)),
    SILVER_BOARD_SWORD("silver_board_sword", () -> new BoardSwordItem(ConfluenceTiers.SILVER, 5, 1.6F)),
    TUNGSTEN_BOARD_SWORD("tungsten_board_sword", () -> new BoardSwordItem(ConfluenceTiers.TUNGSTEN, 6, 1.6F)),
    GOLDEN_BOARD_SWORD("golden_board_sword", () -> new BoardSwordItem(ConfluenceTiers.GOLD, 7, 1.6F)),
    PLATINUM_BOARD_SWORD("platinum_board_sword", () -> new BoardSwordItem(ConfluenceTiers.PLATINUM, 7, 1.6F)),

    RED_LIGHT_SABER("red_light_saber", () -> new LightSaber("red")),
    ORANGE_LIGHT_SABER("orange_light_saber", () -> new LightSaber("orange")),
    YELLOW_LIGHT_SABER("yellow_light_saber", () -> new LightSaber("yellow")),
    GREEN_LIGHT_SABER("green_light_saber", () -> new LightSaber("green")),
    BLUE_LIGHT_SABER("blue_light_saber", () -> new LightSaber("blue")),
    PURPLE_LIGHT_SABER("purple_light_saber", () -> new LightSaber("purple")),
    WHITE_LIGHT_SABER("white_light_saber", () -> new LightSaber("white"));

    private final RegistryObject<SwordItem> value;

    Swords(String id, Supplier<SwordItem> sword) {
        this.value = ConfluenceItems.ITEMS.register(id, sword);
    }

    @Override
    public RegistryObject<SwordItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering swords");
    }
}
