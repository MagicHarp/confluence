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
    PLANK_BOOTS("plank_boots", () -> new PlankArmorItem(ArmorItem.Type.BOOTS)),

    RAIN_CAP("rain_cap", () -> new RaincoatArmorItem(ArmorItem.Type.HELMET)),

    RAINCOAT("raincoat", () -> new RaincoatArmorItem(ArmorItem.Type.CHESTPLATE)),

    SNOW_CAPS("snow_caps", () -> new SnowArmorItem(ArmorItem.Type.HELMET)),
    SNOW_SUITS("snow_suits", () -> new SnowArmorItem(ArmorItem.Type.CHESTPLATE)),
    INSULATED_PANTS("insulated_pants", () -> new SnowArmorItem(ArmorItem.Type.LEGGINGS)),
    INSULATED_SHOES("insulated_shoes", () -> new SnowArmorItem(ArmorItem.Type.BOOTS)),

    SNOW_PINK_CAPS("snow_pink_caps", () -> new SnowPinkArmorItem(ArmorItem.Type.HELMET)),
    SNOW_PINK_SUITS("snow_pink_suits", () -> new SnowPinkArmorItem(ArmorItem.Type.CHESTPLATE)),

;
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
