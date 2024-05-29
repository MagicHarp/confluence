package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Gels implements EnumRegister<Item> {
    BLUE_GEL("blue_gel",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    PINK_GEL("pink_gel",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    HONEY_GEL("honey_gel",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE))),
    FROZEN_GEL("frozen_gel",() -> new Item(new Item.Properties().rarity(ModRarity.WHITE)));

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
