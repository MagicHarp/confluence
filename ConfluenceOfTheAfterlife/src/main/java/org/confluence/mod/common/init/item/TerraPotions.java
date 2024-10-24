package org.confluence.mod.common.init.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModEffects;
import org.confluence.mod.common.item.potion.*;

public class TerraPotions{
    public static final DeferredRegister.Items POTIONS = DeferredRegister.createItems(Confluence.MODID);

    public static final DeferredItem<AbstractPotionItem> ARCHERY_POTION = POTIONS.register("archery_potion", () -> new EffectPotionItem(ModEffects.ARCHERY, 9600));
    public static final DeferredItem<AbstractPotionItem> BUILDER_POTION = POTIONS.register("builder_potion", () -> new EffectPotionItem(ModEffects.BUILDER, 54000));
    public static final DeferredItem<AbstractPotionItem> DANGERSENSE_POTION = POTIONS.register("dangersense_potion", () -> new EffectPotionItem(ModEffects.DANGER_SENSE, 4800));
    public static final DeferredItem<AbstractPotionItem> ENDURANCE_POTION = POTIONS.register("endurance_potion", () -> new EffectPotionItem(ModEffects.ENDURANCE, 4800));
    public static final DeferredItem<AbstractPotionItem> FEATHERFALL_POTION = POTIONS.register("featherfall_potion", () -> new EffectPotionItem(MobEffects.SLOW_FALLING, 12000));
    public static final DeferredItem<AbstractPotionItem> FLIPPER_POTION = POTIONS.register("flipper_potion", () -> new EffectPotionItem(ModEffects.FLIPPER, 9600));
    public static final DeferredItem<AbstractPotionItem> FISHING_POTION = POTIONS.register("fishing_potion", () -> new EffectPotionItem(ModEffects.FISHING, 9600));
    public static final DeferredItem<AbstractPotionItem> GILLS_POTION = POTIONS.register("gills_potion", () -> new EffectPotionItem(MobEffects.WATER_BREATHING, 4800));
    public static final DeferredItem<AbstractPotionItem> GRAVITATION_POTION = POTIONS.register("gravitation_potion", () -> new EffectPotionItem(ModEffects.GRAVITATION, 3600));
    public static final DeferredItem<AbstractPotionItem> HEART_REACH_POTION = POTIONS.register("heart_reach_potion", () -> new EffectPotionItem(ModEffects.HEART_REACH, 9600));
    public static final DeferredItem<AbstractPotionItem> HUNTER_POTION = POTIONS.register("hunter_potion", ()->new EffectPotionItem(ModEffects.HUNTER, 9600));
    public static final DeferredItem<AbstractPotionItem> INFERNO_POTION = POTIONS.register("inferno_potion", () -> new EffectPotionItem(ModEffects.INFERNO, 4800));
    public static final DeferredItem<AbstractPotionItem> INVISIBILITY_POTION = POTIONS.register("invisibility_potion", () -> new EffectPotionItem(MobEffects.INVISIBILITY, 3600));
    public static final DeferredItem<AbstractPotionItem> IRON_SKIN_POTION = POTIONS.register("iron_skin_potion", () -> new EffectPotionItem(ModEffects.IRON_SKIN, 9600));
    public static final DeferredItem<AbstractPotionItem> LIFEFORCE_POTION = POTIONS.register("lifeforce_potion", () -> new EffectPotionItem(ModEffects.LIFE_FORCE, 9600));
    public static final DeferredItem<EffectThrowablePotionItem> LOVE_POTION = POTIONS.register("love_potion", () -> new EffectThrowablePotionItem(ModEffects.LOVE, 600));
    public static final DeferredItem<AbstractPotionItem> LUCK_POTION = POTIONS.register("luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000));
    public static final DeferredItem<AbstractPotionItem> LESSER_LUCK_POTION = POTIONS.register("lesser_luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000, 1));
    public static final DeferredItem<AbstractPotionItem> GREATER_LUCK_POTION = POTIONS.register("greater_luck_potion", () -> new EffectPotionItem(ModEffects.LUCK_EFFECT, 6000, 2));
    public static final DeferredItem<AbstractPotionItem> MANA_REGENERATION_POTION = POTIONS.register("mana_regeneration_potion", () -> new EffectPotionItem(ModEffects.MANA_REGENERATION, 9600));
    public static final DeferredItem<AbstractPotionItem> MAGIC_POWER_POTION = POTIONS.register("magic_power_potion", () -> new EffectPotionItem(ModEffects.MAGIC_POWER, 4800));
    public static final DeferredItem<AbstractPotionItem> MINING_POTION = POTIONS.register("mining_potion", () -> new EffectPotionItem(MobEffects.DIG_SPEED, 12000, 1));
    public static final DeferredItem<AbstractPotionItem> NIGHT_OWL_POTION = POTIONS.register("night_owl_potion", () -> new EffectPotionItem(MobEffects.NIGHT_VISION, 6000));
    public static final DeferredItem<AbstractPotionItem> OBSIDIAN_SKIN_POTION = POTIONS.register("obsidian_skin_potion", () -> new EffectPotionItem(ModEffects.OBSIDIAN_SKIN, 7200));
    public static final DeferredItem<AbstractPotionItem> RAGE_POTION = POTIONS.register("rage_potion", () -> new EffectPotionItem(ModEffects.RAGE, 4800));
    public static final DeferredItem<AbstractPotionItem> RECALL_POTION = POTIONS.register("recall_potion", RecallPotionItem::new);
    public static final DeferredItem<AbstractPotionItem> REGENERATION_POTION = POTIONS.register("regeneration_potion", () -> new EffectPotionItem(MobEffects.REGENERATION, 9600));
    public static final DeferredItem<AbstractPotionItem> SHINE_POTION = POTIONS.register("shine_potion", () -> new EffectPotionItem(ModEffects.SHINE, 12000));
    public static final DeferredItem<AbstractPotionItem> SPELUNKER_POTION = POTIONS.register("spelunker_potion", ()->new EffectPotionItem(ModEffects.SPELUNKER, 6000));
    public static final DeferredItem<AbstractPotionItem> SWIFTNESS_POTION = POTIONS.register("swiftness_potion", () -> new EffectPotionItem( MobEffects.MOVEMENT_SPEED, 9600));
    public static final DeferredItem<AbstractPotionItem> THORNS_POTION = POTIONS.register("thorns_potion", () -> new EffectPotionItem(ModEffects.THORNS, 9600));
    public static final DeferredItem<AbstractPotionItem> TITAN_POTION = POTIONS.register("titan_potion", () -> new EffectPotionItem(ModEffects.TITAN, 9600));
    public static final DeferredItem<AbstractPotionItem> WATER_WALKING_POTION = POTIONS.register("water_walking_potion", () -> new EffectPotionItem(ModEffects.WATER_WALKING, 12000));
    public static final DeferredItem<AbstractPotionItem> WRATH_POTION = POTIONS.register("wrath_potion", () -> new EffectPotionItem(ModEffects.WRATH, 4800));
    public static final DeferredItem<AbstractPotionItem> LESSER_HEALING_POTION = POTIONS.register("lesser_healing_potion", () -> new HealingPotionItem(50, Rarity.COMMON));
    public static final DeferredItem<AbstractPotionItem> HEALING_POTION = POTIONS.register("healing_potion", () -> new HealingPotionItem(100, Rarity.UNCOMMON));
    public static final DeferredItem<AbstractPotionItem> GREATER_HEALING_POTION = POTIONS.register("greater_healing_potion", () -> new HealingPotionItem(200, Rarity.RARE));
    public static final DeferredItem<AbstractPotionItem> SUPER_HEALING_POTION = POTIONS.register("super_healing_potion", () -> new HealingPotionItem(300, Rarity.EPIC));
    public static final DeferredItem<AbstractPotionItem> LESSER_MANA_POTION = POTIONS.register("lesser_mana_potion", () -> new ManaPotionItem(50, Rarity.COMMON));
    public static final DeferredItem<AbstractPotionItem> MANA_POTION = POTIONS.register("mana_potion", () -> new ManaPotionItem(100, Rarity.UNCOMMON));
    public static final DeferredItem<AbstractPotionItem> GREATER_MANA_POTION = POTIONS.register("greater_mana_potion", () -> new ManaPotionItem(200, Rarity.RARE));
    public static final DeferredItem<AbstractPotionItem> SUPER_MANA_POTION = POTIONS.register("super_mana_potion", () -> new ManaPotionItem(300, Rarity.EPIC));
    public static final DeferredItem<AbstractPotionItem> RANDOM_TELEPORT_POTION = POTIONS.register("random_teleport_potion", RandomTeleportPotionItem::new);
}
