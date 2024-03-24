package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Swords implements EnumRegister<SwordItem> {
    COPPER_SHORT_SWORD("copper_short_sword", () -> new ShortSwordItem(ModTiers.COPPER, 2, 3)),
    TIN_SHORT_SWORD("tin_short_sword", () -> new ShortSwordItem(ModTiers.TIN, 3, 3)),
    LEAD_SHORT_SWORD("lead_short_sword", () -> new ShortSwordItem(ModTiers.LEAD, 3, 3)),
    SILVER_SHORT_SWORD("silver_short_sword", () -> new ShortSwordItem(ModTiers.SILVER, 4, 3)),
    TUNGSTEN_SHORT_SWORD("tungsten_short_sword", () -> new ShortSwordItem(ModTiers.TUNGSTEN, 4, 3)),
    GOLDEN_SHORT_SWORD("golden_short_sword", () -> new ShortSwordItem(ModTiers.GOLD, 5, 3)),
    PLATINUM_SHORT_SWORD("platinum_short_sword", () -> new ShortSwordItem(ModTiers.PLATINUM, 5, 3)),

    CACTUS_SWORD("cactus_sword", () -> new BoardSwordItem(ModTiers.CACTUS, 4, 1.6F)),
    COPPER_BOARD_SWORD("copper_board_sword", () -> new BoardSwordItem(ModTiers.COPPER, 4, 1.6F)),
    TIN_BOARD_SWORD("tin_board_sword", () -> new BoardSwordItem(ModTiers.TIN, 4, 1.6F)),
    LEAD_BOARD_SWORD("lead_board_sword", () -> new BoardSwordItem(ModTiers.LEAD, 5, 1.6F)),
    SILVER_BOARD_SWORD("silver_board_sword", () -> new BoardSwordItem(ModTiers.SILVER, 5, 1.6F)),
    TUNGSTEN_BOARD_SWORD("tungsten_board_sword", () -> new BoardSwordItem(ModTiers.TUNGSTEN, 6, 1.6F)),
    GOLDEN_BOARD_SWORD("golden_board_sword", () -> new BoardSwordItem(ModTiers.GOLD, 7, 1.6F)),
    PLATINUM_BOARD_SWORD("platinum_board_sword", () -> new BoardSwordItem(ModTiers.PLATINUM, 7, 1.6F)),

    RED_LIGHT_SABER("red_light_saber", LightSaber.Red::new),
    ORANGE_LIGHT_SABER("orange_light_saber", LightSaber.Orange::new),
    YELLOW_LIGHT_SABER("yellow_light_saber", LightSaber.Yellow::new),
    GREEN_LIGHT_SABER("green_light_saber", LightSaber.Green::new),
    BLUE_LIGHT_SABER("blue_light_saber", LightSaber.Blue::new),
    PURPLE_LIGHT_SABER("purple_light_saber", LightSaber.Purple::new),
    WHITE_LIGHT_SABER("white_light_saber", LightSaber.White::new);

    private final RegistryObject<SwordItem> value;

    Swords(String id, Supplier<SwordItem> sword) {
        this.value = ModItems.ITEMS.register(id, sword);
    }

    @Override
    public RegistryObject<SwordItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering swords");
    }
}
