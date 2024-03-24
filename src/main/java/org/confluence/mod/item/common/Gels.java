package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Gels implements EnumRegister<Item> {
    BLUE_GEL("blue_gel", BaseItem::new),
    PINK_GEL("pink_gel", BaseItem::new),
    HONEY_GEL("honey_gel", BaseItem::new),
    FROZEN_GEL("frozen_gel", BaseItem::new);

    private final RegistryObject<Item> value;

    Gels(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering gels");
    }
}
