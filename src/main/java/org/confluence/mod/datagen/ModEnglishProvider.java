package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, Confluence.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.curios", "Confluence | Curios");

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
        add("curios.tooltip.scope", "Increases view range for ranged weapons");
        add("curios.tooltip.scope2", "Hold ranged weapon and crouch to zoom out");

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
        add("info.confluence.weather_radio.clear", "Weather: Clear");
        add("info.confluence.weather_radio.cloudy", "Weather: Cloudy");
        add("info.confluence.weather_radio.rain", "Weather: Rain");
        add("info.confluence.weather_radio.snow", "Weather: Snow");
        add("info.confluence.weather_radio.thunder", "Weather: Thunder");
        add("info.confluence.fishermans_pocket_guide", "Fishing Power: %s");

        add("key.confluence.metal_detector", "Detect Metal");

        add("item.confluence.vitamins.tooltip", "Immunity to Weakness");
        add("item.confluence.fast_clock.tooltip", "Immunity to Slow");
        add("item.confluence.blindfold.tooltip", "Immunity to Blindness");
        add("item.confluence.bezoar.tooltip", "Immunity to Poison");
        add("item.confluence.trifold_map.tooltip", "Immunity to Nausea");
        add("item.confluence.the_plan.tooltip", "Immunity to Slow and Nausea");
        add("item.confluence.holy_water.tooltip", "Immunity to Wither");
        add("item.confluence.energy_bar.tooltip", "Immunity to Hunger");
        add("item.confluence.flashlight.tooltip", "Immunity to Darkness");
        add("item.confluence.hand_drill.tooltip", "Immunity to Mining Fatigue");
        add("item.confluence.shot_put.tooltip", "Immunity to Levitation");
        add("item.confluence.searchlight.tooltip", "Immunity to Blindness and Darkness");
        add("item.confluence.detoxification_capsule.tooltip", "Immunity to Poison and Wither");
        add("item.confluence.explorers_equipment.tooltip", "Immunity to Mining Fatigue and Levitation");
        add("item.confluence.nutrient_solution.tooltip", "Immunity to Weakness and Hunger");
        add("item.confluence.ankh_charm.tooltip", "Grants immunity to most debuffs");
        add("item.confluence.cobalt_shield.tooltip", "Grants immunity to knockback");
        add("item.confluence.band_of_regeneration.tooltip", "Slowly regenerates life");
        add("item.confluence.mechanical_lens.tooltip", "Grants improved wire vision");
        add("item.confluence.spectre_goggles.tooltip", "Enables Echo Sight, showing hidden blocks");
        add("item.confluence.magiluminescence.tooltip", "Increases movement speed and acceleration");
        add("item.confluence.magiluminescence.tooltip3", "'A brief light in my dark life.'");
        add("item.confluence.sandstorm_on_a_bottle.tooltip", "Allows the holder to do an improved double jump");
        add("item.confluence.ice_skates.tooltip", "Provides extra mobility on ice");
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
        add("item.confluence.royal_gel.tooltip", "Slimes become friendly");
        add("item.confluence.arcane_flower.tooltip3", "The enemy is unlikely to target you");
        add("item.confluence.celestial_stone.tooltip", "Slightly increases the attribute value");
        add("item.confluence.charm_of_myths.tooltip", "Provides health regeneration and reduces the cooldown of healing potions");
        add("item.confluence.philosophers_stone.tooltip", "Reduced the cooldown of Healing Potions");
        add("item.confluence.gold_ring.tooltip", "Expanded coin pickup");
        add("item.confluence.gravity_globe.tooltip", "Allows the holder to reverse gravity");
        add("item.confluence.gravity_globe.tooltip2", "Press Jump to change gravity");
        add("item.confluence.ancient_chisel.tooltip", "25% increased mining speed");
        add("item.confluence.ancient_chisel.tooltip2", "“Age-old problems require age-old solutions”");
        add("item.confluence.treasure_magnet.tooltip", "Expanded item pickup");
        add("item.confluence.flower_boots.tooltip", "Flowers grow on the grass you walk on");
        add("item.confluence.sorcerer_emblem.tooltip", "15% increased magic damage");
        add("item.confluence.hand_warmer.tooltip", "Provides immunity to chill and freezing effects");
        add("item.confluence.star_cloak.tooltip", "Causes stars to fall after taking damage");
        add("item.confluence.honey_comb.tooltip", "Releases bees and douses the user in honey when damaged");
        add("item.confluence.sweetheart_necklace.tooltip", "When damaged, the bee is released and the user is immersed in honey and increases movement speed");
        add("item.confluence.hive_pack.tooltip", "Increases the strength of friendly bees");
        add("item.confluence.magic_quiver.tooltip", "Increases arrow damage by 10% and greatly increases arrow speed");
        add("item.confluence.magic_quiver.tooltip2", "20% chance to not consume arrows");
        add("item.confluence.molten_quiver.tooltip", "Lights wooden arrows ablaze");
        add("item.confluence.molten_quiver.tooltip2", "'Quiver in fear!'");
        add("item.confluence.sniper_scope.tooltip", "10% increased ranged damage and critical strike chance");
        add("item.confluence.recon_scope.tooltip", "'Enemy spotted'");

        add("death.attack.star_cloak", "%1$s was squashed by a falling star");

        ModItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (item1 instanceof CustomName customName) add(item1, customName.getGenName());
            else add(item1, toTitleCase(item.getId().getPath()));
        });
        ModEffects.EFFECTS.getEntries().forEach(effect -> add(effect.get(), toTitleCase(effect.getId().getPath())));
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
