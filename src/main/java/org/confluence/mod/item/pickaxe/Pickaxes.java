package org.confluence.mod.item.pickaxe;

import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.ConfluenceTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Pickaxes implements EnumRegister<PickaxeItem> {
    COPPER_PICKAXE("copper_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.COPPER, 2, 1)),
    TIN_PICKAXE("tin_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.TIN, 2, 1)),
    LEAD_PICKAXE("lead_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.LEAD, 3, 1)),
    SILVER_PICKAXE("silver_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.SILVER, 3, 1)),
    WOLFRAM_PICKAXE("wolfram_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.TUNGSTEN, 4, 1)),
    GOLDEN_PICKAXE("golden_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.GOLD, 5, 1)),
    PLATINUM_PICKAXE("platinum_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.PLATINUM, 5, 1));

    private final RegistryObject<PickaxeItem> value;

    Pickaxes(String id, Supplier<PickaxeItem> pickaxe) {
        this.value = ConfluenceItems.ITEMS.register(id, pickaxe);
    }

    @Override
    public RegistryObject<PickaxeItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering pickaxes");
    }
}
