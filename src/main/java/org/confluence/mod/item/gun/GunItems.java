package org.confluence.mod.item.gun;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum GunItems implements EnumRegister<BaseGunItem> {
    HANDGUN("handgun", BaseGunItem::new);

    private final RegistryObject<BaseGunItem> value;

    GunItems(String id, Supplier<BaseGunItem> supplier) {
        this.value = ModItems.ITEMS.register(id, supplier);
    }

    @Override
    public RegistryObject<BaseGunItem> getValue() {
        return value;
    }
}
