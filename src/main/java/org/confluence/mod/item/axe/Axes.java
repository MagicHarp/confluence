package org.confluence.mod.item.axe;

import net.minecraft.world.item.AxeItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Axes implements EnumRegister<AxeItem> {
    COPPER_AXE("copper_axe", () -> new BaseAxeItem(ModTiers.COPPER, 2, 1)),
    TIN_AXE("tin_axe", () -> new BaseAxeItem(ModTiers.TIN, 2, 1)),
    LEAD_AXE("lead_axe", () -> new BaseAxeItem(ModTiers.LEAD, 3, 1)),
    SILVER_AXE("silver_axe", () -> new BaseAxeItem(ModTiers.SILVER, 3, 1)),
    TUNGSTEN_AXE("tungsten_axe", () -> new BaseAxeItem(ModTiers.TUNGSTEN, 4, 1)),
    GOLDEN_AXE("golden_axe", () -> new BaseAxeItem(ModTiers.GOLD, 5, 1)),
    PLATINUM_AXE("platinum_axe", () -> new BaseAxeItem(ModTiers.PLATINUM, 5, 1)),
    EBONY_AXE("ebony_axe", () -> new BaseAxeItem(ModTiers.EBONY, 6, 1)),
    ANOTHER_CRIMSON_AXE("another_crimson_axe", () -> new BaseAxeItem(ModTiers.ANOTHER_CRIMSON, 6, 1));

    private final RegistryObject<AxeItem> value;

    Axes(String id, Supplier<AxeItem> axe) {
        this.value = ModItems.ITEMS.register(id, axe);
    }

    @Override
    public RegistryObject<AxeItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering axes");
    }
}
