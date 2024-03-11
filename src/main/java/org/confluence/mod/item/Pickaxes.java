package org.confluence.mod.item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.pickaxe.BasePickaxeItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Pickaxes implements EnumRegister<PickaxeItem> {
    COPPER_PICKAXE("copper_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.COPPER, 2, 1)),
    TIN_PICKAXE("tin_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.TIN, 0, 0)),
    LEAD_PICKAXE("lead_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.LEAD, 0, 0)),
    SILVER_PICKAXE("silver_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.SILVER, 0, 0)),
    WOLFRAM_PICKAXE("walfram_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.TUNGSTEN, 0, 0)),
    PLATINUM_PICKAXE("platinum_pickaxe", () -> new BasePickaxeItem(ConfluenceTiers.PLATINUM, 0, 0));

    private final RegistryObject<PickaxeItem> value;

    Pickaxes(String id, Supplier<PickaxeItem> pickaxe) {
        this.value = ConfluenceItems.ITEMS.register(id, pickaxe);
    }

    @Override
    public RegistryObject<PickaxeItem> getValue() {
        return value;
    }

    static void init() {
        Confluence.LOGGER.info("Registering pickaxes");
    }
}
