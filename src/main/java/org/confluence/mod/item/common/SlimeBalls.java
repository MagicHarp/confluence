package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum SlimeBalls implements EnumRegister<Item> {
    BLUE_SLIME_BALL("blue_slime_ball", BaseItem::new),
    PINK_SLIME_BALL("pink_slime_ball", BaseItem::new),
    HONEY_SLIME_BALL("honey_slime_ball", BaseItem::new);

    private final RegistryObject<Item> value;

    SlimeBalls(String id, Supplier<Item> item) {
        this.value = ConfluenceItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering slime balls");
    }
}
