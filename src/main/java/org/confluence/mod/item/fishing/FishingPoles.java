package org.confluence.mod.item.fishing;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum FishingPoles implements EnumRegister<Item> {
    HOTLINE_FISHING_HOOK("hotline_fishing_hook", HotlineFishingHook::new);

    private final RegistryObject<Item> value;

    FishingPoles(String id, Supplier<Item> supplier) {
        this.value = ModItems.ITEMS.register(id, supplier);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {}
}
