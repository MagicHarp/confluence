package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.capability.prefix.ModPrefix;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, Confluence.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.building_blocks", "Confluence | Buildings");
        add("creativetab.confluence.natural_blocks", "Confluence | Naturals");
        add("creativetab.confluence.materials", "Confluence | Materials");
        add("creativetab.confluence.tools", "Confluence | Tools");
        add("creativetab.confluence.warriors", "Confluence | Warriors");
        add("creativetab.confluence.rangers", "Confluence | Rangers");
        add("creativetab.confluence.mages", "Confluence | Mages");
        add("creativetab.confluence.summoners", "Confluence | Summoners");
        add("creativetab.confluence.misc", "Confluence | Miscellaneous");
        add("creativetab.confluence.food_and_potions", "Confluence | Food & Potions");
        add("creativetab.confluence.armors", "Confluence | Armors");
        add("creativetab.confluence.curios", "Confluence | Curios");

        add("item.confluence.meteorite_ingot.tooltip", "Warm to the touch");
        add("item.confluence.alpha.desc", "C418 - alpha");

        add("curios.tooltip.speed_boots", "The wearer can run super fast");
        add("curios.tooltip.may_fly", "Allows flight");
        add("curios.tooltip.jump_boost", "Increases jump height");
        add("curios.tooltip.multi_jump", "Allows the holder to double jump");
        add("curios.tooltip.fall_resistance", "Increases fall resistance");
        add("curios.tooltip.negates_fall_damage", "Negates fall damage");
        add("curios.tooltip.watch", "Tell the time");
        add("curios.tooltip.fire_immune", "Grants immunity to fire blocks");
        add("curios.tooltip.fluid_walk.part", "Provides the ability to walk on water & honey");
        add("curios.tooltip.fluid_walk.all", "Provides the ability t walk on water, honey & lava");
        add("curios.tooltip.lava_immune", "Provides 7 seconds of immunity to lava");
        add("curios.tooltip.lava_hurt_reduce", "Reduces damage from touching lava");
        add("curios.tooltip.fire_attack", "Melee attacks inflict fire damage");
        add("curios.tooltip.auto_attack", "Enables auto swing for melee weapons");
        add("curios.tooltip.aggro_attach", "Enemies are more likely to target you");
        add("curios.tooltip.armor_pass", "Increases armor penetration by %s");
        add("curios.tooltip.projectile_attack", "%s%% increased ranged damage");
        add("curios.tooltip.compass", "Displays horizontal position");
        add("curios.tooltip.depth_meter", "Displays depth");
        add("curios.tooltip.dps_meter", "Displays your damage per second");
        add("curios.tooltip.fishermans_pocket_guide", "Displays fishing information");
        add("curios.tooltip.life_form_analyzer", "Displays the name of rare creatures around you");
        add("curios.tooltip.metal_detector", "Displays the most valuable ore around you");
        add("curios.tooltip.radar", "Detects enemies around you");
        add("curios.tooltip.sextant", "Displays the phase of the moon");
        add("curios.tooltip.stopwatch", "Displays how fast the player is moving");
        add("curios.tooltip.tally_counter", "Displays how many monsters have been killed");
        add("curios.tooltip.weather_radio", "Displays the weather");

        add("info.confluence.time", "Time: [%s:%s]");
        add("info.confluence.radar", "Enemies: %s");
        add("info.confluence.compass.east", "East: %s, ");
        add("info.confluence.compass.west", "West: %s, ");
        add("info.confluence.compass.south", "South: %s");
        add("info.confluence.compass.north", "North: %s");
        add("info.confluence.depth_meter.surface", "Surface: %s");
        add("info.confluence.depth_meter.underground", "Underground: %s");
        add("info.confluence.tally_counter.unknown", "Kill count unavailable");
        add("info.confluence.tally_counter", "Killed '");
        add("info.confluence.life_form_analyzer.none", "No rare creatures nearby!");
        add("info.confluence.life_form_analyzer", "%s detected nearby!");
        add("info.confluence.metal_detector.none", "No treasure nearby!");
        add("info.confluence.metal_detector", "%s detected nearby!");
        add("info.confluence.stopwatch", "Speed: %s m/s");
        add("info.confluence.dps_meter", "DPS: %s");
        add("info.confluence.sextant.0", "Moon phase: Full Moon");
        add("info.confluence.sextant.1", "Moon phase: Waning Gibbous");
        add("info.confluence.sextant.2", "Moon phase: Third Quarter");
        add("info.confluence.sextant.3", "Moon phase: Waning Crescent");
        add("info.confluence.sextant.4", "Moon phase: New Moon");
        add("info.confluence.sextant.5", "Moon phase: Waxing Crescent");
        add("info.confluence.sextant.6", "Moon phase: First Quarter");
        add("info.confluence.sextant.7", "Moon phase: Waxing Gibbous");
        add("info.confluence.weather_radio.clear", "Weather: Clear, Wind Speed: %s");
        add("info.confluence.weather_radio.cloudy", "Weather: Cloudy, Wind Speed: %s");
        add("info.confluence.weather_radio.rain", "Weather: Rain, Wind Speed: %s");
        add("info.confluence.weather_radio.snow", "Weather: Snow, Wind Speed: %s");
        add("info.confluence.weather_radio.thunder", "Weather: Thunder, Wind Speed: %s");
        add("info.confluence.fishermans_pocket_guide", "Fishing Power: %s");
        add("info.confluence.bait", "Bait Power: %s%%");

        add("key.confluence.hook", "Throwing Hook");
        add("key.confluence.metal_detector", "Detect Metal");

        add("item.confluence.adhesive_bandage.tooltip", "Immunity to Bleeding");
        add("item.confluence.medicated_bandage.tooltip", "Immunity to Poison and Bleeding");
        add("item.confluence.armor_bracing.tooltip", "Immunity to Weakness and Broken Armor");
        add("item.confluence.armor_polish.tooltip", "Immunity to Broken Armor");
        add("item.confluence.vitamins.tooltip", "Immunity to Weakness");
        add("item.confluence.the_plan.tooltip", "Immunity to Slow and Confusion");
        add("item.confluence.fast_clock.tooltip", "Immunity to Slow");
        add("item.confluence.trifold_map.tooltip", "Immunity to Confusion");
        add("item.confluence.contercurse_mantra.tooltip", "Immunity to Silence and Curse");
        add("item.confluence.megaphone.tooltip", "Immunity to Silence");
        add("item.confluence.nazar.tooltip", "Immunity to Curse");
        add("item.confluence.reflective_shades.tooltip", "Immunity to Blindness and Petrification");
        add("item.confluence.blindfold.tooltip", "Immunity to Blindness");
        add("item.confluence.pocket_mirror.tooltip", "Immunity to Petrification");
        add("item.confluence.ankh_charm.tooltip", "Grants immunity to most debuffs");
        add("item.confluence.ankh_shield.tooltip", "Grants immunity to most debuffs");
        add("item.confluence.honey_comb.tooltip", "Releases bees and douses the user in honey when damaged");
        add("item.confluence.bezoar.tooltip", "Immunity to Poison");
        add("item.confluence.cobalt_shield.tooltip", "Grants immunity to knockback");
        add("item.confluence.band_of_regeneration.tooltip", "Slowly regenerates life");
        add("item.confluence.band_of_starpower.tooltip", "Increases maximum mana by 20");
        add("item.confluence.mechanical_lens.tooltip", "Grants improved wire vision");
        add("item.confluence.spectre_goggles.tooltip", "Enables Echo Sight, showing hidden blocks");
        add("item.confluence.magiluminescence.tooltip", "Increases movement speed and acceleration");
        add("item.confluence.magiluminescence.tooltip2", "Provides light when worn");
        add("item.confluence.magiluminescence.tooltip3", "'A brief light in my dark life.'");
        add("item.confluence.sandstorm_on_a_bottle.tooltip", "Allows the holder to do an improved double jump");
        add("item.confluence.ice_skates.tooltip", "Provides extra mobility on ice");
        add("item.confluence.ice_skates.tooltip2", "Ice will not break when you fall on it");
        add("item.confluence.dunerider_boots.tooltip", "The wearer can run super fast, and even faster on sand");
        add("item.confluence.dunerider_boots.tooltip2", "'Walk without rhythm and you won't attract the worm'");
        add("item.confluence.lucky_horseshoe.tooltip", "'Said to bring good fortune and keep evil spirits at bay'");
        add("item.confluence.lightning_boots.tooltip", "Allows flight, super fast running");
        add("item.confluence.horseshoe_balloon.tooltip", "Increases jump height and negates fall damage");
        add("item.confluence.lava_waders.tooltip2", "Grants immunity to fire blocks and 7 seconds of immunity to lava");
        add("item.confluence.bundle_of_balloons.tooltip", "Allows the holder to quadruple jump");
        add("item.confluence.bundle_of_horseshoe_balloon.tooltip", "Allows the holder to quadruple jump");
        add("item.confluence.bundle_of_horseshoe_balloon.tooltip2", "Increases jump height and negates fall damage");
        add("item.confluence.water_walking_boots.tooltip", "Provides the ability to walk on water & honey");
        add("item.confluence.magma_skull.tooltip", "Immunity to fire blocks, melee attacks deal fire damage");
        add("item.confluence.frostspark_boots.tooltip", "Allows flight, super fast running, and extra mobility on ice");
        add("item.confluence.sun_stone.tooltip", "During daytime, grants minor increase");
        add("item.confluence.moon_stone.tooltip", "During nighttime, grants minor increase");
        add("item.confluence.putrid_scent.tooltip", "Enemies are less likely to target you");
        add("item.confluence.putrid_scent.tooltip2", "5% increased damage and critical strike chance");
        add("item.confluence.panic_necklace.tooltip", "Increases movement speed after taking damage");
        add("item.confluence.paladins_shield.tooltip", "Absorbs 25% of damage done to players on your team when above 25% life");
        add("item.confluence.frozen_shield.tooltip", "Absorbs 25% of damage done to players on your team when above 25% life");
        add("item.confluence.frozen_shield.tooltip2", "Puts a shell around the owner when below 50% life that reduces damage by 25%");
        add("item.confluence.frozen_turtle_shell.tooltip", "Puts a shell around the owner when below 50% life that reduces damage by 25%");
        add("item.confluence.fire_gauntlet.tooltip", "Increases melee knockback and melee attacks inflict fire damage");
        add("item.confluence.eye_of_the_golem.tooltip", "10% increased critical strike chance");
        add("item.confluence.destroyer_emblem.tooltip", "8% increased critical strike chance");
        add("item.confluence.cross_necklace.tooltip", "Increases length of invincibility after taking damage");
        add("item.confluence.black_belt.tooltip", "Gives a chance to dodge attacks");
        add("item.confluence.terraspark_boots.tooltip2", "Grants immunity to fire blocks and 7 seconds of immunity to lava");
        add("item.confluence.fledgling_wings.tooltip", "Allows flight and slow fall");
        add("item.confluence.worm_scarf.tooltip", "Reduces damage taken by 17%");
        add("item.confluence.shield_of_cthulhu.tooltip", "Allows the player to dash into the enemy, sprinting to dsh");
        add("item.confluence.brain_of_confusion.tooltip", "Has a chance to create illusions and dodge an attack");
        add("item.confluence.brain_of_confusion.tooltip2", "Temporarily increase critical chance after dodge");
        add("item.confluence.brain_of_confusion.tooltip3", "May confuse nearby enemies after being struck");
        add("item.confluence.royal_gel.tooltip", "Slimes cannot damage you");
        add("item.confluence.magic_cuffs.tooltip", "Maximum mana is increased by 20");
        add("item.confluence.magic_cuffs.tooltip2", "Restores mana when injured");
        add("item.confluence.magnet_flower.tooltip", "8% reduced mana cost");
        add("item.confluence.magnet_flower.tooltip2", "Automatically uses mana potions when needed");
        add("item.confluence.magnet_flower.tooltip3", "Increases the pickup range of Mana Stars");
        add("item.confluence.mana_flower.tooltip", "8% reduced mana cost");
        add("item.confluence.mana_flower.tooltip2", "Automatically uses mana potions when needed");
        add("item.confluence.arcane_flower.tooltip", "8% reduced mana cost");
        add("item.confluence.arcane_flower.tooltip2", "Automatically uses mana potions when needed");
        add("item.confluence.arcane_flower.tooltip3", "The enemy is unlikely to target you");
        add("item.confluence.celestial_cuffs.tooltip", "Increases the pickup range of Mana Stars");
        add("item.confluence.celestial_cuffs.tooltip2", "Restores mana when injured");
        add("item.confluence.celestial_cuffs.tooltip3", "Maximum mana is increased by 20");
        add("item.confluence.celestial_magnet.tooltip", "Increases the pickup range of Mana Stars");
        add("item.confluence.celestial_emblem.tooltip", "Increases the pickup range of Mana Stars");
        add("item.confluence.celestial_emblem.tooltip2", "15% increased magic damage");
        add("item.confluence.celestial_stone.tooltip", "Slightly increases the attribute value");
        add("item.confluence.charm_of_myths.tooltip", "Provides health regeneration and reduces the cooldown of healing potions");
        add("item.confluence.philosophers_stone.tooltip", "Reduced the cooldown of Healing Potions");
        add("item.confluence.mana_regeneration_band.tooltip", "Maximum mana is increased by 20");
        add("item.confluence.mana_regeneration_band.tooltip2", "Increases mana regeneration rate");
        add("item.confluence.natures_gift.tooltip", "6% reduced mana cost");
        add("item.confluence.gold_ring.tooltip", "Expanded coin pickup");
        add("item.confluence.gravity_globe.tooltip", "Allows the holder to reverse gravity");
        add("item.confluence.gravity_globe.tooltip2", "Press Jump to change gravity");
        add("item.confluence.sorcerer_emblem.tooltip", "15% increased magic damage");
        add("item.confluence.sweetheart_necklace.tooltip", "When damaged, the bee is released and the user is immersed in honey and increases movement speed");
        add("item.confluence.ancient_chisel.tooltip", "25% increased mining speed");
        add("item.confluence.ancient_chisel.tooltip2", "“Age-old problems require age-old solutions”");
        add("item.confluence.lucky_coin.tooltip", "Hitting an enemy can sometimes drop extra coins");
        add("item.confluence.coin_ring.tooltip", "Increasing the coin pickup range and hitting enemies will sometimes drop additional coins");
        add("item.confluence.hive_pack.tooltip", "Increases the strength of friendly bees");
        add("item.confluence.treasure_magnet.tooltip", "Expanded item pickup");
        add("item.confluence.flower_boots.tooltip", "Flowers grow on the grass you walk on");
        add("item.confluence.high_test_fishing_line.tooltip", "Line never breaks");
        add("item.confluence.angler_earring.tooltip", "Increases fishing power");
        add("item.confluence.fishing_bobber.tooltip", "Increases fishing power");
        add("item.confluence.glowing_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.lava_moss_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.helium_moss_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.neon_moss_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.argon_moss_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.krypton_moss_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.xenon_moss_fishing_bobber.tooltip", "Increases fishing power,Your bobber now glows");
        add("item.confluence.tackle_box.tooltip", "−5% bait consumption chance");
        add("item.confluence.angler_tackle_bag.tooltip", "Line never breaks and −5% bait consumption chance");
        add("item.confluence.lavaproof_fishing_hook.tooltip", "Allows fishing in lava regardless of bait or rod used.");
        add("item.confluence.lavaproof_tackle_bag.tooltip1", "Allows fishing in lava regardless of bait or rod used.");
        add("item.confluence.lavaproof_tackle_bag.tooltip2", "Line never breaks and −5% bait consumption chance");
        add("item.confluence.hand_warmer.tooltip", "Provides immunity to chill and freezing effects");
        add("item.confluence.mana_cloak.tooltip", "Stars restore mana when collected");
        add("item.confluence.star_cloak.tooltip", "Causes stars to fall after taking damage");

        add("death.attack.falling_star", "%1$s was squashed by a falling star");

        add("painting.confluence.magic_harp.title", "MAGIC_HARP");
        add("painting.confluence.magic_harp.author", "BiliBili_魔法竖琴waaa，Looks silly...");
        add("painting.confluence.amanita.title", "AMANITA");
        add("painting.confluence.amanita.author", "BiliBili_蘑菇人怎么还不来，A mushroom lady !");
        add("painting.confluence.westernat.title", "WESTERNAT");
        add("painting.confluence.westernat.author", "BiliBili_Westernat233，MC 21st century, the most impressionist birch painting!");
        add("painting.confluence.cooobrid.title", "COOOBRID");
        add("painting.confluence.cooobrid.author", "BiliBili_事一只一只一只鸽子，咕咕咕咕~");
        add("painting.confluence.nakinosi.title", "NAKINOSI");
        add("painting.confluence.nakinosi.author", "BiliBili_咕咕咕的屑枕头");
        add("painting.confluence.maker.title", "MAKER");
        add("painting.confluence.maker.author", "BiliBili_Maker-2333");
        add("painting.confluence.serious_observers.title", "SERIOUS_OBSERVERS");
        add("painting.confluence.serious_observers.author", "BiliBili_严肃的侦测器，Quite serious indeed");
        add("painting.confluence.a_pigeon_delight.title", "A_PIGEON_DELIGHT");
        add("painting.confluence.a_pigeon_delight.author", "BiliBili_一只鸽子悦");

        add("advancements.start.title", "Old World, New Journey!");
        add("advancements.start.descr", "The afterlife of convergence and exchange");
        add("advancements.copper_short_sword.title", "Take up arms");
        add("advancements.copper_short_sword.descr", "You've got your first weapon, wield it as you face monsters!");
        add("advancements.copper_pickaxe.title", "Go deep underground");
        add("advancements.copper_pickaxe.descr", "You've got your first pickaxe to break hard stone!");
        add("advancements.copper_axe.title", "Collect resources");
        add("advancements.copper_axe.descr", "You've got your first axe, and you've got this important resource, logs!");
        add("advancements.falling_star.title", "Seek the Fallen Light");
        add("advancements.falling_star.descr", "When it meets, the night sky is marked with a special kind of star");
        add("advancements.mana_star.title", "Gather the power of the stars");
        add("advancements.mana_star.descr", "The stars will increase your magical powers");

        add("prefix.confluence.quick", "Quick");
        add("prefix.confluence.hasty", "Hasty");
        add("prefix.confluence.deadly", "Deadly");

        add("prefix.confluence.tooltip.plus", "+%s%% %s");
        add("prefix.confluence.tooltip.take", "-%s%% %s");
        add("prefix.confluence.tooltip.add", "+%s %s");
        add("prefix.confluence.tooltip.attack_damage", "Attack Damage");
        add("prefix.confluence.tooltip.attack_speed", "Attack Speed");
        add("prefix.confluence.tooltip.critical_chance", "Critical Chance");
        add("prefix.confluence.tooltip.knock_back", "Knock Back");
        add("prefix.confluence.tooltip.velocity", "Velocity");
        add("prefix.confluence.tooltip.mana_cost", "Mana Cost");
        add("prefix.confluence.tooltip.armor", "Armor");
        add("prefix.confluence.tooltip.additional_mana", "Additional Mana");
        add("prefix.confluence.tooltip.movement_speed", "Movement Speed");

        add("fluid_type.confluence.shimmer", "Shimmer");
        add("fluid_type.confluence.honey", "Honey");
        add("title.confluence.shimmer_transmutation", "Shimmer Transmutation");

        ModBlocks.BLOCKS.getEntries().forEach(block -> {
            Block block1 = block.get();
            if (block1 instanceof WallSignBlock || block1 instanceof WallTorchBlock) return;
            add(block1, toTitleCase(block.getId().getPath()));
        });
        ModItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (item1 instanceof BlockItem) return;
            if (item1 instanceof CustomName customName) add(item1, customName.getGenName());
            else add(item1, toTitleCase(item.getId().getPath()));
        });
        ModEntities.ENTITIES.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
        ModEffects.EFFECTS.getEntries().forEach(effect -> add(effect.get(), toTitleCase(effect.getId().getPath())));
        for (ModPrefix.Universal universal : ModPrefix.Universal.values()) {
            add("prefix.confluence." + universal.name().toLowerCase(), toTitleCase(universal.name()));
        }
        for (ModPrefix.Common common : ModPrefix.Common.values()) {
            if (common == ModPrefix.Common.QUICK || common == ModPrefix.Common.DEADLY) continue;
            add("prefix.confluence." + common.name().toLowerCase(), toTitleCase(common.name()));
        }
        for (ModPrefix.Melee melee : ModPrefix.Melee.values()) {
            add("prefix.confluence." + melee.name().toLowerCase(), toTitleCase(melee.name()));
        }
        for (ModPrefix.Ranged ranged : ModPrefix.Ranged.values()) {
            if (ranged == ModPrefix.Ranged.HASTY || ranged == ModPrefix.Ranged.DEADLY) continue;
            add("prefix.confluence." + ranged.name().toLowerCase(), toTitleCase(ranged.name()));
        }
        for (ModPrefix.MagicAndSumming magicAndSumming : ModPrefix.MagicAndSumming.values()) {
            add("prefix.confluence." + magicAndSumming.name().toLowerCase(), toTitleCase(magicAndSumming.name()));
        }
        for (ModPrefix.Curio curio : ModPrefix.Curio.values()) {
            if (curio == ModPrefix.Curio.HASTY || curio == ModPrefix.Curio.QUICK) continue;
            add("prefix.confluence." + curio.name().toLowerCase(), toTitleCase(curio.name()));
        }
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
