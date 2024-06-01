package org.confluence.mod.item.gun;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum GunItems implements EnumRegister<AbstractGunItem> {
    HANDGUN("handgun", HandGunItem::new);

    private final RegistryObject<AbstractGunItem> value;

    GunItems(String id, Supplier<AbstractGunItem> supplier) {
        this.value = ModItems.ITEMS.register(id, supplier);
    }

    @Override
    public RegistryObject<AbstractGunItem> getValue() {
        return value;
    }

    public static void init() {}
}
