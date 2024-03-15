package org.confluence.mod.item.hammer;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Hammers implements EnumRegister<HammerItem> {
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
