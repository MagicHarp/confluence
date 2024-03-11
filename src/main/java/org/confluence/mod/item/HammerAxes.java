package org.confluence.mod.item;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.hammer.HammerAxeItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum HammerAxes implements EnumRegister<HammerAxeItem> {
    ;

    private final RegistryObject<HammerAxeItem> value;

    HammerAxes(String id, Supplier<HammerAxeItem> hammerAxe) {
        this.value = ConfluenceItems.ITEMS.register(id, hammerAxe);
    }

    @Override
    public RegistryObject<HammerAxeItem> getValue() {
        return value;
    }

    static void init() {
        Confluence.LOGGER.info("Registering hammer-axes");
    }
}
