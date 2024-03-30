package org.confluence.mod.item.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Armors implements EnumRegister<ArmorItem> {
    CACTUS_HELMET("cactus_helmet", () -> new CactusArmorItem(ArmorItem.Type.HELMET)),
    CACTUS_CHESTPLATE("cactus_chestplate", () -> new CactusArmorItem(ArmorItem.Type.CHESTPLATE)),
    CACTUS_LEGGINGS("cactus_leggings", () -> new CactusArmorItem(ArmorItem.Type.LEGGINGS)),
    CACTUS_BOOTS("cactus_boots", () -> new CactusArmorItem(ArmorItem.Type.BOOTS)),

    PLANK_HELMET("plank_helmet", () -> new PlankArmorItem(ArmorItem.Type.HELMET)),
    PLANK_CHESTPLATE("plank_chestplate", () -> new PlankArmorItem(ArmorItem.Type.CHESTPLATE)),
    PLANK_LEGGINGS("plank_leggings", () -> new PlankArmorItem(ArmorItem.Type.LEGGINGS)),
    PLANK_BOOTS("plank_boots", () -> new PlankArmorItem(ArmorItem.Type.BOOTS));

    private final RegistryObject<ArmorItem> value;

    Armors(String id, Supplier<ArmorItem> armorItem) {
        this.value = ModItems.ITEMS.register(id, armorItem);
    }

    @Override
    public RegistryObject<ArmorItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering armors");
    }
}
