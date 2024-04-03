package org.confluence.mod.item.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Armors implements EnumRegister<ArmorItem> {
    //仙人掌套
    CACTUS_HELMET("cactus_helmet", () -> new CactusArmorItem(ArmorItem.Type.HELMET)),
    CACTUS_CHESTPLATE("cactus_chestplate", () -> new CactusArmorItem(ArmorItem.Type.CHESTPLATE)),
    CACTUS_LEGGINGS("cactus_leggings", () -> new CactusArmorItem(ArmorItem.Type.LEGGINGS)),
    CACTUS_BOOTS("cactus_boots", () -> new CactusArmorItem(ArmorItem.Type.BOOTS)),
    //木套
    PLANK_HELMET("plank_helmet", () -> new PlankArmorItem(ArmorItem.Type.HELMET)),
    PLANK_CHESTPLATE("plank_chestplate", () -> new PlankArmorItem(ArmorItem.Type.CHESTPLATE)),
    PLANK_LEGGINGS("plank_leggings", () -> new PlankArmorItem(ArmorItem.Type.LEGGINGS)),
    PLANK_BOOTS("plank_boots", () -> new PlankArmorItem(ArmorItem.Type.BOOTS)),
    //雨衣
    RAIN_CAP("rain_cap", () -> new RaincoatArmorItem(ArmorItem.Type.HELMET)),

    RAINCOAT("raincoat", () -> new RaincoatArmorItem(ArmorItem.Type.CHESTPLATE)),
    //蓝色防雪衣
    SNOW_CAPS("snow_caps", () -> new SnowArmorItem(ArmorItem.Type.HELMET)),
    SNOW_SUITS("snow_suits", () -> new SnowArmorItem(ArmorItem.Type.CHESTPLATE)),
    INSULATED_PANTS("insulated_pants", () -> new SnowArmorItem(ArmorItem.Type.LEGGINGS)),
    INSULATED_SHOES("insulated_shoes", () -> new SnowArmorItem(ArmorItem.Type.BOOTS)),
    //粉色防雪衣
    SNOW_PINK_CAPS("snow_pink_caps", () -> new SnowPinkArmorItem(ArmorItem.Type.HELMET)),
    SNOW_PINK_SUITS("snow_pink_suits", () -> new SnowPinkArmorItem(ArmorItem.Type.CHESTPLATE)),
    //铜套
    COPPER_HELMET("copper_helmet", () -> new CopperArmorItem(ArmorItem.Type.HELMET)),
    COPPER_CHESTPLATE("copper_chestplate", () -> new CopperArmorItem(ArmorItem.Type.CHESTPLATE)),
    COPPER_LEGGINGS("copper_leggings", () -> new CopperArmorItem(ArmorItem.Type.LEGGINGS)),
    COPPER_BOOTS("copper_boots", () -> new CopperArmorItem(ArmorItem.Type.BOOTS)),
    //锡套
    TIN_HELMET("tin_helmet", () -> new TinArmorItem(ArmorItem.Type.HELMET)),
    TIN_CHESTPLATE("tin_chestplate", () -> new TinArmorItem(ArmorItem.Type.CHESTPLATE)),
    TIN_LEGGINGS("tin_leggings", () -> new TinArmorItem(ArmorItem.Type.LEGGINGS)),
    TIN_BOOTS("tin_boots", () -> new TinArmorItem(ArmorItem.Type.BOOTS)),
    //铅套
    LEAD_HELMET("lead_helmet", () -> new LeadArmorItem(ArmorItem.Type.HELMET)),
    LEAD_CHESTPLATE("lead_chestplate", () -> new LeadArmorItem(ArmorItem.Type.CHESTPLATE)),
    LEAD_LEGGINGS("lead_leggings", () -> new LeadArmorItem(ArmorItem.Type.LEGGINGS)),
    LEAD_BOOTS("lead_boots", () -> new LeadArmorItem(ArmorItem.Type.BOOTS)),
    //银套
    SILVER_HELMET("silver_helmet", () -> new SilverArmorItem(ArmorItem.Type.HELMET)),
    SILVER_CHESTPLATE("silver_chestplate", () -> new SilverArmorItem(ArmorItem.Type.CHESTPLATE)),
    SILVER_LEGGINGS("silver_leggings", () -> new SilverArmorItem(ArmorItem.Type.LEGGINGS)),
    SILVER_BOOTS("silver_boots", () -> new SilverArmorItem(ArmorItem.Type.BOOTS)),
    //钨套
    TUNGSTEN_HELMET("tungsten_helmet", () -> new TungstenArmorItem(ArmorItem.Type.HELMET)),
    TUNGSTEN_CHESTPLATE("tungsten_chestplate", () -> new TungstenArmorItem(ArmorItem.Type.CHESTPLATE)),
    TUNGSTEN_LEGGINGS("tungsten_leggings", () -> new TungstenArmorItem(ArmorItem.Type.LEGGINGS)),
    TUNGSTEN_BOOTS("tungsten_boots", () -> new TungstenArmorItem(ArmorItem.Type.BOOTS)),
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
