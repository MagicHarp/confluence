package org.confluence.mod.common.init.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModEffects;
import org.confluence.mod.common.item.potion.*;


import java.util.function.Supplier;

public class TerraPotions{
    public static final DeferredRegister.Items POTIONS = DeferredRegister.createItems(Confluence.MODID);

    //TODO AI

    public static DeferredItem<AbstractPotionItem> ARCHERY_POTION = POTIONS.register("archery_potion", () -> new EffectPotionItem(ModEffects.ARCHERY, 9600));
    public static DeferredItem<AbstractPotionItem> BUILDER_POTION = POTIONS.register("builder_potion", () -> new EffectPotionItem(ModEffects.BUILDER, 54000));
    public static DeferredItem<AbstractPotionItem> DANGERSENSE_POTION = POTIONS.register("dangersense_potion", () -> new EffectPotionItem(ModEffects.DANGER_SENSE, 4800));
    public static DeferredItem<AbstractPotionItem> ENDURANCE_POTION = POTIONS.register("endurance_potion", () -> new EffectPotionItem(ModEffects.ENDURANCE, 4800));
    public static DeferredItem<AbstractPotionItem> FEATHERFALL_POTION = POTIONS.register("featherfall_potion", () -> new EffectPotionItem(MobEffects.SLOW_FALLING, 12000));
    public static DeferredItem<AbstractPotionItem> FLIPPER_POTION = POTIONS.register("flipper_potion", () -> new EffectPotionItem(ModEffects.FLIPPER, 9600));
    public static DeferredItem<AbstractPotionItem> FISHING_POTION = POTIONS.register("fishing_potion", () -> new EffectPotionItem(ModEffects.FISHING, 9600));
    public static DeferredItem<AbstractPotionItem> GILLS_POTION = POTIONS.register("gills_potion", () -> new EffectPotionItem(MobEffects.WATER_BREATHING, 4800));
    public static DeferredItem<AbstractPotionItem> GRAVITATION_POTION = POTIONS.register("gravitation_potion", () -> new EffectPotionItem(ModEffects.GRAVITATION, 3600));
    public static DeferredItem<AbstractPotionItem> HEART_REACH_POTION = POTIONS.register("heart_reach_potion", () -> new EffectPotionItem(ModEffects.HEART_REACH, 9600));
    public static DeferredItem<AbstractPotionItem> HUNTER_POTION = POTIONS.register("hunter_potion", ()->new EffectPotionItem(ModEffects.HUNTER, 9600));
    public static DeferredItem<AbstractPotionItem> INFERNO_POTION = POTIONS.register("inferno_potion", () -> new EffectPotionItem(ModEffects.INFERNO, 4800));
    public static DeferredItem<AbstractPotionItem> INVISIBILITY_POTION = POTIONS.register("invisibility_potion", () -> new EffectPotionItem(MobEffects.INVISIBILITY, 3600));
    public static DeferredItem<AbstractPotionItem> IRON_SKIN_POTION = POTIONS.register("iron_skin_potion", () -> new EffectPotionItem(ModEffects.IRON_SKIN, 9600));
    public static DeferredItem<AbstractPotionItem> LIFEFORCE_POTION = POTIONS.register("lifeforce_potion", () -> new EffectPotionItem(ModEffects.LIFE_FORCE, 9600));
    public static DeferredItem<EffectThrowablePotionItem> LOVE_POTION = POTIONS.register("love_potion", () -> new EffectThrowablePotionItem(ModEffects.LOVE, 600));
    public static DeferredItem<AbstractPotionItem> LUCK_POTION = POTIONS.register("luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000));
    public static DeferredItem<AbstractPotionItem> LESSER_LUCK_POTION = POTIONS.register("lesser_luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000, 1));
    public static DeferredItem<AbstractPotionItem> GREATER_LUCK_POTION = POTIONS.register("greater_luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000, 2));
    public static DeferredItem<AbstractPotionItem> MANA_REGENERATION_POTION = POTIONS.register("mana_regeneration_potion", () -> new EffectPotionItem(ModEffects.MANA_REGENERATION, 9600));
    public static DeferredItem<AbstractPotionItem> MAGIC_POWER_POTION = POTIONS.register("magic_power_potion", () -> new EffectPotionItem(ModEffects.MAGIC_POWER, 4800));
    public static DeferredItem<AbstractPotionItem> MINING_POTION = POTIONS.register("mining_potion", () -> new EffectPotionItem(MobEffects.DIG_SPEED, 12000, 1));
    public static DeferredItem<AbstractPotionItem> NIGHT_OWL_POTION = POTIONS.register("night_owl_potion", () -> new EffectPotionItem(MobEffects.NIGHT_VISION, 6000));
    public static DeferredItem<AbstractPotionItem> OBSIDIAN_SKIN_POTION = POTIONS.register("obsidian_skin_potion", () -> new EffectPotionItem(ModEffects.OBSIDIAN_SKIN, 7200));
    public static DeferredItem<AbstractPotionItem> RAGE_POTION = POTIONS.register("rage_potion", () -> new EffectPotionItem(ModEffects.RAGE, 4800));
    public static DeferredItem<AbstractPotionItem> RECALL_POTION = POTIONS.register("recall_potion", RecallPotionItem::new);
    public static DeferredItem<AbstractPotionItem> REGENERATION_POTION = POTIONS.register("regeneration_potion", () -> new EffectPotionItem(MobEffects.REGENERATION, 9600));
    public static DeferredItem<AbstractPotionItem> SHINE_POTION = POTIONS.register("shine_potion", () -> new EffectPotionItem(ModEffects.SHINE, 12000));
    public static DeferredItem<AbstractPotionItem> SPELUNKER_POTION = POTIONS.register("spelunker_potion", ()->new EffectPotionItem(ModEffects.SPELUNKER, 6000));
    public static DeferredItem<AbstractPotionItem> SWIFTNESS_POTION = POTIONS.register("swiftness_potion", () -> new EffectPotionItem( MobEffects.MOVEMENT_SPEED, 9600));
    public static DeferredItem<AbstractPotionItem> THORNS_POTION = POTIONS.register("thorns_potion", () -> new EffectPotionItem(ModEffects.THORNS, 9600));
    public static DeferredItem<AbstractPotionItem> TITAN_POTION = POTIONS.register("titan_potion", () -> new EffectPotionItem(ModEffects.TITAN, 9600));
    public static DeferredItem<AbstractPotionItem> WATER_WALKING_POTION = POTIONS.register("water_walking_potion", () -> new EffectPotionItem(ModEffects.WATER_WALKING, 12000));
    public static DeferredItem<AbstractPotionItem> WRATH_POTION = POTIONS.register("wrath_potion", () -> new EffectPotionItem(ModEffects.WRATH, 4800));
    public static DeferredItem<AbstractPotionItem> LESSER_HEALING_POTION = POTIONS.register("lesser_healing_potion", () -> new HealingPotionItem(50, Rarity.COMMON));
    public static DeferredItem<AbstractPotionItem> HEALING_POTION = POTIONS.register("healing_potion", () -> new HealingPotionItem(100, Rarity.UNCOMMON));
    public static DeferredItem<AbstractPotionItem> GREATER_HEALING_POTION = POTIONS.register("greater_healing_potion", () -> new HealingPotionItem(200, Rarity.RARE));
    public static DeferredItem<AbstractPotionItem> SUPER_HEALING_POTION = POTIONS.register("super_healing_potion", () -> new HealingPotionItem(300, Rarity.EPIC));
    public static DeferredItem<AbstractPotionItem> LESSER_MANA_POTION = POTIONS.register("lesser_mana_potion", () -> new ManaPotionItem(50, Rarity.COMMON));
    public static DeferredItem<AbstractPotionItem> MANA_POTION = POTIONS.register("mana_potion", () -> new ManaPotionItem(100, Rarity.UNCOMMON));
    public static DeferredItem<AbstractPotionItem> GREATER_MANA_POTION = POTIONS.register("greater_mana_potion", () -> new ManaPotionItem(200, Rarity.RARE));
    public static DeferredItem<AbstractPotionItem> SUPER_MANA_POTION = POTIONS.register("super_mana_potion", () -> new ManaPotionItem(300, Rarity.EPIC));
    public static DeferredItem<AbstractPotionItem> RANDOM_TELEPORT_POTION = POTIONS.register("random_teleport_potion", RandomTeleportPotionItem::new);
//    public static DeferredItem<AbstractPotionItem> VANILLA_POTION = POTIONS.register("vanilla_potion", VanillaPotionItem::new);


    /*
    //AMMO_RESERVATION_POTION("ammo_reservation_potion", BaseItem::new),
    ARCHERY_POTION("archery_potion", () -> new EffectPotionItem(ModEffects.ARCHERY, 9600)),
    //BATTLE_POTION("battle_potion", BaseItem::new),
    BUILDER_POTION("builder_potion", () -> new EffectPotionItem(ModEffects.BUILDER, 54000)),
    //CALMING_POTION("calming_potion", BaseItem::new),
    //CRATE_POTION("crate_potion", BaseItem::new),
    DANGERSENSE_POTION("dangersense_potion", () -> new EffectPotionItem(ModEffects.DANGER_SENSE, 4800)),
    ENDURANCE_POTION("endurance_potion", () -> new EffectPotionItem(ModEffects.ENDURANCE, 4800)),
    FEATHERFALL_POTION("featherfall_potion", () -> new EffectPotionItem(() -> MobEffects.SLOW_FALLING, 12000)),
    FLIPPER_POTION("flipper_potion", () -> new EffectPotionItem(ModEffects.FLIPPER, 9600)),
    FISHING_POTION("fishing_potion", () -> new EffectPotionItem(ModEffects.FISHING, 9600)),
    GILLS_POTION("gills_potion", () -> new EffectPotionItem(() -> MobEffects.WATER_BREATHING, 4800)),
    GRAVITATION_POTION("gravitation_potion", () -> new EffectPotionItem(ModEffects.GRAVITATION, 3600)),
    HEART_REACH_POTION("heart_reach_potion", () -> new EffectPotionItem(ModEffects.HEART_REACH, 9600)),
    HUNTER_POTION("hunter_potion", ()->new EffectPotionItem(ModEffects.HUNTER, 9600)),
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
    NIGHT_OWL_POTION("night_owl_potion", () -> new EffectPotionItem(() -> MobEffects.NIGHT_VISION, 6000)),
    OBSIDIAN_SKIN_POTION("obsidian_skin_potion", () -> new EffectPotionItem(ModEffects.OBSIDIAN_SKIN, 7200)),
    RAGE_POTION("rage_potion", () -> new EffectPotionItem(ModEffects.RAGE, 4800)),
    RECALL_POTION("recall_potion", RecallPotionItem::new),
    REGENERATION_POTION("regeneration_potion", () -> new EffectPotionItem(() -> MobEffects.REGENERATION, 9600)),
    SHINE_POTION("shine_potion", () -> new EffectPotionItem(ModEffects.SHINE, 12000)),
    //SONAR_POTION("sonar_potion", BaseItem::new),
    SPELUNKER_POTION("spelunker_potion", ()->new EffectPotionItem(ModEffects.SPELUNKER, 6000)),//洞穴探险药水
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
    SUPER_MANA_POTION("super_mana_potion", () -> new ManaPotionItem(300, Rarity.EPIC)),
    RANDOM_TELEPORT_POTION("random_teleport_potion", RandomTeleportPotionItem::new),
    VANILLA_POTION("vanilla_potion", VanillaPotionItem::new);
*/



}
