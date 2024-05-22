package org.confluence.mod.item.potion;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum TerraPotions implements EnumRegister<AbstractPotionItem> {
    //AMMO_RESERVATION_POTION("ammo_reservation_potion", BaseItem::new),
    ARCHERY_POTION("archery_potion", () -> new EffectPotionItem(ModEffects.ARCHERY, 9600)),
    //BATTLE_POTION("battle_potion", BaseItem::new),
    BUILDER_POTION("builder_potion", () -> new EffectPotionItem(ModEffects.BUILDER, 54000)),
    //CALMING_POTION("calming_potion", BaseItem::new),
    //CRATE_POTION("crate_potion", BaseItem::new),
    //DANGERSENSE_POTION("dangersense_potion", BaseItem::new),
    ENDURANCE_POTION("endurance_potion", () -> new EffectPotionItem(ModEffects.ENDURANCE, 4800)),
    FEATHERFALL_POTION("featherfall_potion", () -> new EffectPotionItem(() -> MobEffects.SLOW_FALLING, 12000)),
    FLIPPER_POTION("flipper_potion", () -> new EffectPotionItem(ModEffects.FLIPPER, 9600)),
    FISHING_POTION("fishing_potion", () -> new EffectPotionItem(ModEffects.FISHING, 9600)),
    GILLS_POTION("gills_potion", () -> new EffectPotionItem(() -> MobEffects.WATER_BREATHING, 4800)),
    GRAVITATION_POTION("gravitation_potion", () -> new EffectPotionItem(ModEffects.GRAVITATION, 3600)),
    HEART_REACH_POTION("heart_reach_potion", () -> new EffectPotionItem(ModEffects.HEART_REACH, 9600)),
    //HUNTER_POTION("hunter_potion", BaseItem::new),
    INFERNO_POTION("inferno_potion", () -> new EffectPotionItem(ModEffects.INFERNO, 4800)),
    INVISIBILITY_POTION("invisibility_potion", () -> new EffectPotionItem(() -> MobEffects.INVISIBILITY, 3600)),
    IRON_SKIN_POTION("iron_skin_potion", () -> new EffectPotionItem(ModEffects.IRON_SKIN, 9600)),
    LIFEFORCE_POTION("lifeforce_potion", () -> new EffectPotionItem(ModEffects.LIFE_FORCE, 9600)),
    LOVE_POTION("love_potion", () -> new EffectThrowablePotionItem(ModEffects.LOVE, 600)),
    LUCK_POTION("luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000)),
    LESSER_LUCK_POTION("lesser_luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000, 1)),
    GREATER_LUCK_POTION("greater_luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000, 2)),
    MANA_REGENERATION_POTION("mana_regeneration_potion", () -> new EffectPotionItem(ModEffects.MANA_REGENERATION, 9600)),
    MAGIC_POWER_POTION("magic_power_potion", () -> new EffectPotionItem(ModEffects.MAGIC_POWER, 4800)),
    MINING_POTION("mining_potion", () -> new EffectPotionItem(() -> MobEffects.DIG_SPEED, 12000, 1)),
    NIGHT_OWL_POTION("night_owl_potion", () -> new EffectPotionItem(() -> MobEffects.NIGHT_VISION, 12000)),
    OBSIDIAN_SKIN_POTION("obsidian_skin_potion", () -> new EffectPotionItem(ModEffects.OBSIDIAN_SKIN, 7200)),
    RAGE_POTION("rage_potion", () -> new EffectPotionItem(ModEffects.RAGE, 4800)),
    RECALL_POTION("recall_potion", RecallPotionItem::new),
    REGENERATION_POTION("regeneration_potion", () -> new EffectPotionItem(() -> MobEffects.REGENERATION, 9600)),
    //SONAR_POTION("sonar_potion", BaseItem::new),
    //SPELUNKER_POTION("spelunker_potion", BaseItem::new),
    //STRANGE_BREW("strange_brew", BaseItem::new),
    //SUMMONING_POTION("summoning_potion", BaseItem::new),
    SWIFTNESS_POTION("swiftness_potion", () -> new EffectPotionItem(() -> MobEffects.MOVEMENT_SPEED, 9600)),
    THORNS_POTION("thorns_potion", () -> new EffectPotionItem(ModEffects.THORNS, 9600)),
    TITAN_POTION("titan_potion", () -> new EffectPotionItem(ModEffects.TITAN, 9600)),
    WATER_WALKING_POTION("water_walking_potion", () -> new EffectPotionItem(ModEffects.WATER_WALKING, 12000)),
    WRATH_POTION("wrath_potion", () -> new EffectPotionItem(ModEffects.WRATH, 4800)),
    LESSER_HEALING_POTION("lesser_healing_potion", () -> new HealingPotionItem(50, Rarity.COMMON)),
    HEALING_POTION("healing_potion", () -> new HealingPotionItem(100, Rarity.UNCOMMON)),
    GREATER_HEALING_POTION("greater_healing_potion", () -> new HealingPotionItem(200, Rarity.RARE)),
    SUPER_HEALING_POTION("super_healing_potion", () -> new HealingPotionItem(300, Rarity.EPIC)),
    LESSER_MANA_POTION("lesser_mana_potion", () -> new ManaPotionItem(50, Rarity.COMMON)),
    MANA_POTION("mana_potion", () -> new ManaPotionItem(100, Rarity.UNCOMMON)),
    GREATER_MANA_POTION("greater_mana_potion", () -> new ManaPotionItem(200, Rarity.RARE)),
    SUPER_MANA_POTION("super_mana_potion", () -> new ManaPotionItem(300, Rarity.EPIC));

    private final RegistryObject<AbstractPotionItem> value;

    TerraPotions(String id, Supplier<AbstractPotionItem> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<AbstractPotionItem> getValue() {
        return value;
    }

    public static void init() {}
}
