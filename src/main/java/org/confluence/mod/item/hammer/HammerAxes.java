package org.confluence.mod.item.hammer;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum HammerAxes implements EnumRegister<HammerAxeItem>  {
    ;

    private final RegistryObject<HammerAxeItem> value;

    HammerAxes(String id, Supplier<HammerAxeItem> hammerAxe) {
        this.value = ModItems.ITEMS.register(id, hammerAxe);
    }

    @Override
    public RegistryObject<HammerAxeItem> getValue() {
        return value;
    }

    public static void init() {}
}
