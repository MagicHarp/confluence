package org.confluence.mod.item.gun;

import net.minecraftforge.registries.RegistryObject;

import static org.confluence.mod.item.ModItems.ITEMS;

public final class GunItems {
    public static final RegistryObject<HandGunItem> HANDGUN = ITEMS.register("handgun", HandGunItem::new);
    public static final RegistryObject<MusketGunItem> MUSKET = ITEMS.register("musket", MusketGunItem::new);

    public static void init() {}
}
