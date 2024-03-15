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
    COPPER_BOARD_SWORD("copper_board_sword", () -> new BoardSwordItem(ConfluenceTiers.COPPER, 4, 1.6F)),
    TIN_SHORT_SWORD("tin_short_sword", () -> new ShortSwordItem(ConfluenceTiers.TIN, 3, 3)),
    TIN_BOARD_SWORD("tin_board_sword", () -> new BoardSwordItem(ConfluenceTiers.TIN, 5, 1.6F)),
    LEAD_SHORT_SWORD("lead_short_sword", () -> new ShortSwordItem(ConfluenceTiers.LEAD, 0, 0)),
    LEAD_BOARD_SWORD("lead_board_sword", () -> new BoardSwordItem(ConfluenceTiers.LEAD, 0, 0)),
    SILVER_SHORT_SWORD("silver_short_sword", () -> new ShortSwordItem(ConfluenceTiers.SILVER, 0, 0)),
    SILVER_BOARD_SWORD("silver_board_sword", () -> new BoardSwordItem(ConfluenceTiers.SILVER, 0, 0)),
    WOLFRAM_SHORT_SWORD("wolfram_short_sword", () -> new ShortSwordItem(ConfluenceTiers.TUNGSTEN, 0, 0)),
    WOLFRAM_BOARD_SWORD("wolfram_board_sword", () -> new BoardSwordItem(ConfluenceTiers.TUNGSTEN, 0, 0)),
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
