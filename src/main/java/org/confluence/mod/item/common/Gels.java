package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

import static org.confluence.mod.misc.ModRarity.WHITE;

public enum Gels implements EnumRegister<Item> {
    BLUE_GEL("blue_gel",() -> new BaseItem(WHITE)),
    PINK_GEL("pink_gel",() -> new BaseItem(WHITE)),
    HONEY_GEL("honey_gel",() -> new BaseItem(WHITE)),
    FROZEN_GEL("frozen_gel",() -> new BaseItem(WHITE));

    private final RegistryObject<Item> value;

    Gels(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {}
}
