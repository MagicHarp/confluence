package org.confluence.mod.item.food;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Foods implements EnumRegister<Item> {
    CookedMarshmallow("cooked_marshmallow", () -> new BaseFoodItem(LowGradeFood.CookedMarshmallow));
    ;
    private final RegistryObject<Item> value;

    Foods(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering foods");
    }

}
