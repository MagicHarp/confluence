package org.confluence.mod.item.hammer;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Hammers implements EnumRegister<HammerItem> {
    COPPER_HAMMER("copper_hammer", () -> new HammerItem(ModTiers.COPPER, 2, 1)),
    TIN_HAMMER("tin_hammer", () -> new HammerItem(ModTiers.TIN, 2, 1)),
    LEAD_HAMMER("lead_hammer", () -> new HammerItem(ModTiers.LEAD, 3, 1)),
    SILVER_HAMMER("silver_hammer", () -> new HammerItem(ModTiers.SILVER, 3, 1)),
    TUNGSTEN_HAMMER("tungsten_hammer", () -> new HammerItem(ModTiers.TUNGSTEN, 4, 1)),
    GOLDEN_HAMMER("golden_hammer", () -> new HammerItem(ModTiers.GOLD, 5, 1)),
    PLATINUM_HAMMER("platinum_hammer", () -> new HammerItem(ModTiers.PLATINUM, 5, 1)),
    EBONY_HAMMER("ebony_hammer", () -> new HammerItem(ModTiers.EBONY, 6, 1)),
    ANOTHER_CRIMSON_HAMMER("another_crimson_hammer", () -> new HammerItem(ModTiers.ANOTHER_CRIMSON, 6, 1));

    private final RegistryObject<HammerItem> value;

    Hammers(String id, Supplier<HammerItem> hammer) {
        this.value = ModItems.ITEMS.register(id, hammer);
    }

    @Override
    public RegistryObject<HammerItem> getValue() {
        return value;
    }

    public static void init() {}
}
