package org.confluence.mod.item.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Armors implements EnumRegister<ArmorItem> {
    // 仙人掌套
    CACTUS_HELMET("cactus_helmet", () -> new CactusArmorItem(ArmorItem.Type.HELMET)),
    CACTUS_CHESTPLATE("cactus_chestplate", () -> new CactusArmorItem(ArmorItem.Type.CHESTPLATE)),
    CACTUS_LEGGINGS("cactus_leggings", () -> new CactusArmorItem(ArmorItem.Type.LEGGINGS)),
    CACTUS_BOOTS("cactus_boots", () -> new CactusArmorItem(ArmorItem.Type.BOOTS)),
    // 木套
    PLANK_HELMET("plank_helmet", () -> new PlankArmorItem(ArmorItem.Type.HELMET)),
    PLANK_CHESTPLATE("plank_chestplate", () -> new PlankArmorItem(ArmorItem.Type.CHESTPLATE)),
    PLANK_LEGGINGS("plank_leggings", () -> new PlankArmorItem(ArmorItem.Type.LEGGINGS)),
    PLANK_BOOTS("plank_boots", () -> new PlankArmorItem(ArmorItem.Type.BOOTS)),
    // 雨衣
    RAIN_CAP("rain_cap", () -> new RaincoatArmorItem(ArmorItem.Type.HELMET)),
    RAINCOAT("raincoat", () -> new RaincoatArmorItem(ArmorItem.Type.CHESTPLATE)),
    // 蓝色防雪衣
    SNOW_CAPS("snow_caps", () -> new SnowArmorItem(ArmorItem.Type.HELMET)),
    SNOW_SUITS("snow_suits", () -> new SnowArmorItem(ArmorItem.Type.CHESTPLATE)),
    INSULATED_PANTS("insulated_pants", () -> new SnowArmorItem(ArmorItem.Type.LEGGINGS)),
    INSULATED_SHOES("insulated_shoes", () -> new SnowArmorItem(ArmorItem.Type.BOOTS)),
    // 粉色防雪衣
    PINK_SNOW_CAPS("pink_snow_caps", () -> new SnowPinkArmorItem(ArmorItem.Type.HELMET)),
    PINK_SNOW_SUITS("pink_snow_suits", () -> new SnowPinkArmorItem(ArmorItem.Type.CHESTPLATE)),
    PINK_INSULATED_PANTS("pink_insulated_pants", () -> new SnowPinkArmorItem(ArmorItem.Type.LEGGINGS)),
    PINK_INSULATED_SHOES("pink_insulated_shoes", () -> new SnowPinkArmorItem(ArmorItem.Type.BOOTS)),
    // 忍者套
    NINJA_HELMET("ninja_helmet", () -> new NinjaArmorItem(ArmorItem.Type.HELMET)),
    NINJA_CHESTPLATE("ninja_chestplate", () -> new NinjaArmorItem(ArmorItem.Type.CHESTPLATE)),
    NINJA_LEGGINGS("ninja_leggings", () -> new NinjaArmorItem(ArmorItem.Type.LEGGINGS)),
    NINJA_BOOTS("ninja_boots", () -> new NinjaArmorItem(ArmorItem.Type.BOOTS)),
    // 暗影木套
    SHADOW_HELMET("shadow_helmet", () -> new ShadowArmorItem(ArmorItem.Type.HELMET)),
    SHADOW_CHESTPLATE("shadow_chestplate", () -> new ShadowArmorItem(ArmorItem.Type.CHESTPLATE)),
    SHADOW_LEGGINGS("shadow_leggings", () -> new ShadowArmorItem(ArmorItem.Type.LEGGINGS)),
    SHADOW_BOOTS("shadow_boots", () -> new ShadowArmorItem(ArmorItem.Type.BOOTS)),
    // 乌木套
    EBONY_HELMET("ebony_helmet", () -> new EbonyArmorItem(ArmorItem.Type.HELMET)),
    EBONY_CHESTPLATE("ebony_chestplate", () -> new EbonyArmorItem(ArmorItem.Type.CHESTPLATE)),
    EBONY_LEGGINGS("ebony_leggings", () -> new EbonyArmorItem(ArmorItem.Type.LEGGINGS)),
    EBONY_BOOTS("ebony_boots", () -> new EbonyArmorItem(ArmorItem.Type.BOOTS)),
    // 珍珠套
    PEARL_HELMET("pearl_helmet", () -> new PearlArmorItem(ArmorItem.Type.HELMET)),
    PEARL_CHESTPLATE("pearl_chestplate", () -> new PearlArmorItem(ArmorItem.Type.CHESTPLATE)),
    PEARL_LEGGINGS("pearl_leggings", () -> new PearlArmorItem(ArmorItem.Type.LEGGINGS)),
    PEARL_BOOTS("pearl_boots", () -> new PearlArmorItem(ArmorItem.Type.BOOTS)),
    // 铜套
    COPPER_HELMET("copper_helmet", () -> new CopperArmorItem(ArmorItem.Type.HELMET)),
    COPPER_CHESTPLATE("copper_chestplate", () -> new CopperArmorItem(ArmorItem.Type.CHESTPLATE)),
    COPPER_LEGGINGS("copper_leggings", () -> new CopperArmorItem(ArmorItem.Type.LEGGINGS)),
    COPPER_BOOTS("copper_boots", () -> new CopperArmorItem(ArmorItem.Type.BOOTS)),
    // 锡套
    TIN_HELMET("tin_helmet", () -> new TinArmorItem(ArmorItem.Type.HELMET)),
    TIN_CHESTPLATE("tin_chestplate", () -> new TinArmorItem(ArmorItem.Type.CHESTPLATE)),
    TIN_LEGGINGS("tin_leggings", () -> new TinArmorItem(ArmorItem.Type.LEGGINGS)),
    TIN_BOOTS("tin_boots", () -> new TinArmorItem(ArmorItem.Type.BOOTS)),
    // 铅套
    LEAD_HELMET("lead_helmet", () -> new LeadArmorItem(ArmorItem.Type.HELMET)),
    LEAD_CHESTPLATE("lead_chestplate", () -> new LeadArmorItem(ArmorItem.Type.CHESTPLATE)),
    LEAD_LEGGINGS("lead_leggings", () -> new LeadArmorItem(ArmorItem.Type.LEGGINGS)),
    LEAD_BOOTS("lead_boots", () -> new LeadArmorItem(ArmorItem.Type.BOOTS)),
    // 银套
    SILVER_HELMET("silver_helmet", () -> new SilverArmorItem(ArmorItem.Type.HELMET)),
    SILVER_CHESTPLATE("silver_chestplate", () -> new SilverArmorItem(ArmorItem.Type.CHESTPLATE)),
    SILVER_LEGGINGS("silver_leggings", () -> new SilverArmorItem(ArmorItem.Type.LEGGINGS)),
    SILVER_BOOTS("silver_boots", () -> new SilverArmorItem(ArmorItem.Type.BOOTS)),
    // 钨套
    TUNGSTEN_HELMET("tungsten_helmet", () -> new TungstenArmorItem(ArmorItem.Type.HELMET)),
    TUNGSTEN_CHESTPLATE("tungsten_chestplate", () -> new TungstenArmorItem(ArmorItem.Type.CHESTPLATE)),
    TUNGSTEN_LEGGINGS("tungsten_leggings", () -> new TungstenArmorItem(ArmorItem.Type.LEGGINGS)),
    TUNGSTEN_BOOTS("tungsten_boots", () -> new TungstenArmorItem(ArmorItem.Type.BOOTS)),
    // 金套
    GOLDEN_HELMET("golden_helmet", () -> new GoldenArmorItem(ArmorItem.Type.HELMET)),
    GOLDEN_CHESTPLATE("golden_chestplate", () -> new GoldenArmorItem(ArmorItem.Type.CHESTPLATE)),
    GOLDEN_LEGGINGS("golden_leggings", () -> new GoldenArmorItem(ArmorItem.Type.LEGGINGS)),
    GOLDEN_BOOTS("golden_boots", () -> new GoldenArmorItem(ArmorItem.Type.BOOTS)),
    // 铂金套
    PLATINUM_HELMET("platinum_helmet", () -> new PlatinumArmorItem(ArmorItem.Type.HELMET)),
    PLATINUM_CHESTPLATE("platinum_chestplate", () -> new PlatinumArmorItem(ArmorItem.Type.CHESTPLATE)),
    PLATINUM_LEGGINGS("platinum_leggings", () -> new PlatinumArmorItem(ArmorItem.Type.LEGGINGS)),
    PLATINUM_BOOTS("platinum_boots", () -> new PlatinumArmorItem(ArmorItem.Type.BOOTS)),
    // 化石套
    FOSSIL_HELMET("fossil_helmet", () -> new FossilArmorItem(ArmorItem.Type.HELMET)),
    FOSSIL_CHESTPLATE("fossil_chestplate", () -> new FossilArmorItem(ArmorItem.Type.CHESTPLATE)),
    FOSSIL_LEGGINGS("fossil_leggings", () -> new FossilArmorItem(ArmorItem.Type.LEGGINGS)),
    FOSSIL_BOOTS("fossil_boots", () -> new FossilArmorItem(ArmorItem.Type.BOOTS)),
;
    private final RegistryObject<ArmorItem> value;

    Armors(String id, Supplier<ArmorItem> armorItem) {
        this.value = ModItems.ITEMS.register(id, armorItem);
    }

    @Override
    public RegistryObject<ArmorItem> getValue() {
        return value;
    }

    public static void init() {}
}
