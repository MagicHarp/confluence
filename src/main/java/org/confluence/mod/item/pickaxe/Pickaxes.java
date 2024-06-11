package org.confluence.mod.item.pickaxe;

import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Pickaxes implements EnumRegister<PickaxeItem> {
    CACTUS_PICKAXE("cactus_pickaxe", () -> new BasePickaxeItem(ModTiers.CACTUS, 2, 1)),
    COPPER_PICKAXE("copper_pickaxe", () -> new BasePickaxeItem(ModTiers.COPPER, 2, 1)),
    TIN_PICKAXE("tin_pickaxe", () -> new BasePickaxeItem(ModTiers.TIN, 2, 1)),
    LEAD_PICKAXE("lead_pickaxe", () -> new BasePickaxeItem(ModTiers.LEAD, 3, 1)),
    SILVER_PICKAXE("silver_pickaxe", () -> new BasePickaxeItem(ModTiers.SILVER, 3, 1)),
    TUNGSTEN_PICKAXE("tungsten_pickaxe", () -> new BasePickaxeItem(ModTiers.TUNGSTEN, 4, 1)),
    GOLDEN_PICKAXE("golden_pickaxe", () -> new BasePickaxeItem(ModTiers.GOLD, 5, 1)),
    PLATINUM_PICKAXE("platinum_pickaxe", () -> new BasePickaxeItem(ModTiers.PLATINUM, 5, 1)),;
//其他镐
    private final RegistryObject<PickaxeItem> value;

    Pickaxes(String id, Supplier<PickaxeItem> pickaxe) {
        this.value = ModItems.ITEMS.register(id, pickaxe);
    }

    @Override
    public RegistryObject<PickaxeItem> getValue() {
        return value;
    }

    public static void init() {}
}
