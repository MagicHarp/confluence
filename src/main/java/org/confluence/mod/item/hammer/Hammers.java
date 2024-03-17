package org.confluence.mod.item.hammer;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.ConfluenceTiers;
import org.confluence.mod.item.pickaxe.BasePickaxeItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Hammers implements EnumRegister<HammerItem> {
    COPPER_HAMMER("copper_hammer",()-> new HammerItem(ConfluenceTiers.COPPER, 2, 1)),
    TIN_HAMMER("tin_hammer", () -> new HammerItem(ConfluenceTiers.TIN, 2, 1)),
    LEAD_HAMMER("lead_hammer", () -> new HammerItem(ConfluenceTiers.LEAD, 3, 1)),
    SILVER_HAMMER("silver_hammer", () -> new HammerItem(ConfluenceTiers.SILVER, 3, 1)),
    WOLFRAM_HAMMER("wolfram_hammer", () -> new HammerItem(ConfluenceTiers.TUNGSTEN, 4, 1)),
    PLATINUM_HAMMER("platinum_hammer", () -> new HammerItem(ConfluenceTiers.PLATINUM, 5, 1));
    ;

    private final RegistryObject<HammerItem> value;

    Hammers(String id, Supplier<HammerItem> hammer) {
        this.value = ConfluenceItems.ITEMS.register(id, hammer);
    }

    @Override
    public RegistryObject<HammerItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering hammers");
    }
}
