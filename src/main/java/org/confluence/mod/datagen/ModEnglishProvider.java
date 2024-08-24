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
        add("curios.tooltip.wall_climb", "Allows the ability to climb walls, hold shift key to slide down");
        add("curios.tooltip.wall_slide", "Allows the ability to slide down walls, hold shift key to slide down quickly");
        add("curios.tooltip.tabi", "Allows the ability to dash while double tap a direction");
        add("curios.tooltip.hurt_evasion", "Gives a chance to dodge attacks");

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
        add("key.confluence.step_stool", "Step Stool");

        add("curios.identifier.accessory", "Accessory");
        add("curios.modifiers.accessory", "When worn as accessory:");

        add("item.confluence.demon_heart.tooltip", "Permanently increases the number of accessory slots");
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
        add("item.confluence.portable_cement_mixer.tooltip", "Decreased 'Right Click Delay' by 1");
        add("item.confluence.brick_layer.tooltip", "Decreased 'Right Click Delay' by 1");
        add("item.confluence.architect_gizmo_pack.tooltip", "Decreased 'Right Click Delay' by 2, cannot stack the decrease of its materials");
        add("item.confluence.climbing_claws.tooltip", "Improved ability if combined with Shoe Spikes");
        add("item.confluence.shoe_spikes.tooltip", "Improved ability if combined with Climbing Claws");
        add("item.confluence.frog_gear.tooltip", "'It ain't easy being green'");
        add("item.confluence.hand_of_creation.tooltip", "Increases mining speed by 25%");
        add("item.confluence.hand_of_creation.tooltip2", "Decreased 'Right Click Delay' by 3, cannot stack the decrease of its material");
        add("item.confluence.hand_of_creation.tooltip3", "Increases pickup range for items");
        add("item.confluence.step_stool.tooltip", "Press ↑ key to stand higher, and press Shift key to down");
        add("item.confluence.step_stool.tooltip2", "Extra Step: %s");

        add("item.confluence.aglet.info", "The Aglet is an accessory that increases the player's movement speed by 5%.");
        add("item.confluence.aglet.info2", "It can be found in Shipwreck Chests.");
        add("item.confluence.amber_horseshoe_balloon.info", "The Amber Horseshoe Balloon is an accessory that increases jump height by 75% (6 blocks → 10.5 blocks), makes the player immune to fall damage, and releases bees when attacked, which seek out and damage enemies.");
        add("item.confluence.amber_horseshoe_balloon.info2", "It also grants +0.05 luck.");
        add("item.confluence.amber_horseshoe_balloon.info3", "It can be crafted at a Crafting Table with a Lucky Horseshoe and a Honey Balloon.");
        add("item.confluence.amphibian_boots.info", "The Amphibian Boots are an accessory crafted by combining the Sailfish Boots and Frog Leg at a Crafting Table.");
        add("item.confluence.amphibian_boots.info2", "Equipping it has the following effects:");// 待删除
        add("item.confluence.amphibian_boots.info3", "Increases the player's maximum movement speed to 22.5 meters/second (31 mph)");// 英文待改
        add("item.confluence.amphibian_boots.info4", "Allows auto-jumping");
        add("item.confluence.amphibian_boots.info5", "Increases the maximum safe falling distance by 10 meters");// 待删除
        add("item.confluence.amphibian_boots.info6", "Increases jump speed by 1.6");
        add("item.confluence.anklet_of_the_wind.info", "The Anklet of the Wind is an accessory that gives a 10% bonus to movement speed.");// 英文待改
        add("item.confluence.anklet_of_the_wind.info2", "The effect also stacks with the movement speed bonus of the Aglet.");
        add("item.confluence.anklet_of_the_wind.info3", "It can be crafted at a Crafting Table with a Spore Blossom and a Vines.");
        add("item.confluence.blizzard_in_a_balloon.info", "It boosts jump height by 33% and jump speed by 30%, increasing your first jump's height to ~10½ blocks (~75% increase), and provides a double jump that now goes 14½ blocks, for a total height of 25 blocks.");// 英文待改
        add("item.confluence.blizzard_in_a_balloon.info2", "It can be crafted at a Crafting Table with a Blizzard in a Bottle and a Shiny Red Balloon.");
        add("item.confluence.blizzard_in_a_bottole.info", "The Blizzard in a Bottle is a pre-Hardmode accessory which works similarly to the Cloud in a Bottle, allowing the player to double-jump.");
        add("item.confluence.blizzard_in_a_bottole.info2", "The Blizzard jump goes 8 meters into the air, totaling 14 meters when put together with the first jump. ");// 英文待改
        add("item.confluence.blizzard_in_a_bottole.info3", "It can be crafted at a Crafting Table with a Cloud in a Bottle and a blue ice.");
        add("item.confluence.blue_horseshoe_balloon.info", "The Blue Horseshoe Balloon is an accessory that grants immunity to fall damage, increases jump height by 75%, grants a double-jump, and grants +0.05 luck.");// 英文待改
        add("item.confluence.blue_horseshoe_balloon.info2", "The height of the first jump is increased from 6 blocks to 10.5 blocks, while the double-jump's height is increased from 5 blocks to 9 blocks, reaching a total of 19.5 blocks with both jumps.");
        add("item.confluence.blue_horseshoe_balloon.info3", "It can be crafted at a Crafting Table with a Cloud in a Balloon and a Lucky Horseshoe.");
        add("item.confluence.bundle_of_balloons.info", "The Bundle of Balloons is an accessory that allows the wearer to perform a quadruple jump, as well as increasing the player's jump duration by 33% and jump speed by 30%.");// 英文待改
        add("item.confluence.bundle_of_balloons.info2", "This item can be crafted at a Crafting Table using a Cloud in a Balloon, a Blizzard in a Balloon, and a Sandstorm in a Balloon.");
        add("item.confluence.bundle_of_horseshoe_balloons.info", "The Bundle of Horseshoe Balloons is an accessory which combines the functionalities of the Bundle of Balloons and the Lucky Horseshoe.");
        add("item.confluence.bundle_of_horseshoe_balloons.info2", "When equipped, it allows the wearer to perform a quadruple jump, as well as increasing the player's jump duration by 33%, jump speed by 30%, and luck by 0.05.");// 英文待改
        add("item.confluence.bundle_of_horseshoe_balloons.info3", "Having it equipped also prevents the player from taking fall damage.");
        add("item.confluence.bundle_of_horseshoe_balloons.info4", "It can be crafted at a Crafting Table with a Bundle of Balloons and a Lucky Horseshoe.");
        add("item.confluence.cloud_in_a_balloon.info", "The Cloud in a Balloon is an accessory that combines the functionality of the Cloud in a Bottle and the Shiny Red Balloon.");
        add("item.confluence.cloud_in_a_balloon.info2", "It boosts jump height by 33%, and jump speed by 30%, increasing the player's first jump's height to 10.5 blocks (~75% increase), and provides a double jump that hits 9 blocks, for a total height of 19.5 blocks.");// 英文待改
        add("item.confluence.cloud_in_a_balloon.info3", "It can be crafted at a Crafting Table with a Cloud in a Bottle and a Shiny Red Balloon.");
        add("item.confluence.cloud_in_a_bottle.info", "The Cloud in a Bottle is an accessory that can be found in Chests in the Mineshaft and Dungeon.");
        add("item.confluence.cloud_in_a_bottle.info2", "When equipped, it allows the player an extra jump.");
        add("item.confluence.cloud_in_a_bottle.info3", "The extra jump has a 75% jump duration compared to a normal jump, increasing the maximum height the player can reach without other accessories by 5 meters, for a total of 11 meters.");// 英文待改
        add("item.confluence.dunerider_boots.info", "The Dunerider Boots are an accessory that increases the player's maximum movement speed");
        add("item.confluence.dunerider_boots.info2", "It have a Chance to be dropped from Husk.");
        add("item.confluence.dunerider_boots.info3", "When equipped, it grants the following bonuses:");
        add("item.confluence.dunerider_boots.info4", "Increases the player's maximum movement speed to 22.5 merters/second");// 英文待改
        add("item.confluence.dunerider_boots.info5", "When moving on any Sand, Sandstone, or Hardened Sand, increases maximum movement speed by 15% and increases acceleration and deceleration by 75%, respectively");// 英文待改
        add("item.confluence.fairy_boots.info", "Fairy Boots are an accessory that are made from the Spectre Boots and Flower Boots at a Crafting Table.");
        add("item.confluence.fairy_boots.info2", "They combine both effects into one pair of boots.");
        add("item.confluence.fart_in_a_balloon.info", "The Fart in a Balloon is an accessory that combines the effects of the Fart in a Jar and the Shiny Red Balloon.");
        add("item.confluence.fart_in_a_balloon.info2", "It boosts jump height by 33% and jump speed by 30%, increasing the player's first jump's height to ~10.5 blocks (~75% increase), and provides a double jump that goes to 18.5 blocks, for a total of 29 blocks.");// 英文待改
        add("item.confluence.fart_in_a_balloon.info3", "Unlike most other double-jump balloons, the Fart in a Balloon's full jump is high enough for the player to take minor fall damage, unless the player has fall damage immunity accessories like the Lucky Horseshoe. ");// 英文待改
        add("item.confluence.fart_in_a_balloon.info4", "It can be crafted at a Crafting Table with a Fart in a Jar and a Shiny Red Balloon.");
        add("item.confluence.fart_in_a_jar.info", "The Fart in a Jar is an accessory that allows the player to perform a double jump.");
        add("item.confluence.fart_in_a_jar.info2", "The Fart jump goes 10.5 meters into the air, totalling 16.5 meters when put together with the first jump.");// 英文待改
        add("item.confluence.fart_in_a_jar.info3", "These effects also apply with wings equipped.");
        add("item.confluence.flipper.info", "The Flipper is an accessory that allows a player to swim in liquid. With the Flipper equipped in an accessory slot, the player can swim upwards in Water, Lava, or Honey, by holding or pressing Jump Key repeatedly — essentially allowing unlimited multi-jumps while within liquid.");
        add("item.confluence.flipper.info2", "It can be found in Chests in the Shipwreck.");
        add("item.confluence.flurry_boots.info", "Flurry Boots are an accessory that can be found in Igloo's chest.");
        add("item.confluence.flurry_boots.info2", "When equipped, they allow the player to gain a speed boost after running for a certain distance continuously.");
        add("item.confluence.frog_flipper.info", "Frog Flipper are an accessory that are made from the Frog Leg and Flipper at a Crafting Table.");
        add("item.confluence.frog_flipper.info2", "It combines the abilities of these two items, increasing the player's jump speed and safe falling distance, allowing auto-jump, and giving the player the ability to swim.");
        add("item.confluence.frog_leg.info", "The Frog Leg have a Chance to be dropped from Frog.");
        add("item.confluence.frog_leg.info2", "The Frog Leg is an accessory that can be fished in any viable body of water. It aids jumping in the following ways:");
        add("item.confluence.frog_leg.info3", "Increases the player's jump speed by 1.6");// 英文待改
        add("item.confluence.frog_leg.info4", "The player automatically jumps while the Jump key is held.");
        add("item.confluence.frog_leg.info5", "This effect also works with Climbing Claws and other climbing accessories.");
        add("item.confluence.frog_leg.info6", "Increases the maximum safe falling distance by 10");// 英文待改
        add("item.confluence.frog_leg.info7", "These effects also apply with wings equipped.");
        add("item.confluence.frostspark_boots.info", "The Frostspark Boots are an accessory crafted by combining Lightning Boots and Ice Skates together at a Crafting Table.");
        add("item.confluence.frostspark_boots.info2", "It provides the benefits of both.");
        add("item.confluence.frostspark_boots.info3", "They also have an 8% increased movement speed.");// 英文待改
        add("item.confluence.green_horseshoe_balloon.info", "The Green Horseshoe Balloon is an accessory that grants immunity to fall damage, increases jump height by 33%, jump speed by 30%, grants a double jump, and grants +0.05 luck.");// 英文待改
        add("item.confluence.green_horseshoe_balloon.info2", "The height of the first jump is increased from 6 blocks to 10.5 blocks (~75% increase), while the double jump's height is increased from 10.5 blocks to 18.5 blocks, reaching a total of 29 blocks with both jumps.");// 英文待改
        add("item.confluence.green_horseshoe_balloon.info3", "It can be crafted at a Crafting Table with a Fart in a Balloon and a Lucky Horseshoe.");
        add("item.confluence.hand_warmer.info", "The Hand Warmer is an immunity accessory that grants the player immunity to the Chilled and Frozen debuffs.");
        add("item.confluence.hand_warmer.info2", "It can be crafted at a Crafting Table with a Wool and a Leather.");
        add("item.confluence.hermes_boots.info", "Hermes Boots are an accessory that can be found in Chests in the Village Armorer.");
        add("item.confluence.hermes_boots.info2", "When equipped, they allow the player to gain a movement speed boost after running for a certain distance continuously.");
        add("item.confluence.hermes_boots.info3", "The speed will continue increasing until maximum speed is attained. At full sprint, they will increase the player's horizontal movement speed to 30 mph.");
        add("item.confluence.hermes_boots.info4", "However, if the player runs into a wall without autojumping, the player will stop and have to speed up again.");
        add("item.confluence.honey_balloon.info", "The Honey Balloon is an accessory that combines the functions of a Shiny Red Balloon and a Honey Comb.");
        add("item.confluence.honey_balloon.info2", "It boosts jump height by 33% and jump speed by 30%, increasing your first jump's height to ~10½ blocks (~75% increase), and releases a small swarm of angry bees when the player is hurt, which seek out and deal small amounts of damage to enemies.");// 英文待改
        add("item.confluence.honey_balloon.info3", "It can be crafted at a Crafting Table with a Shiny Red Balloon and a Honey Comb.");
        add("item.confluence.ice_skates.info", "Ice Skates are a pre-Hardmode accessory that improves a player's movement control on all Ice Blocks.");
        add("item.confluence.ice_skates.info2", "The player accelerates faster, slides shorter distances, and will no longer cause Thin Ice to break when landing from falls or jumps.");
        add("item.confluence.ice_skates.info3", "It can be crafted at a Crafting Table with a Hermes Boots and a Blue Ice.");
        add("item.confluence.lava_charm.info", "The Lava Charm is an accessory that grants immunity to lava for 7 seconds.");
        add("item.confluence.lava_charm.info2", "It have a Chance to be dropped from Blaze.");
        add("item.confluence.lava_waders.info", "The Lava Waders are an accessory that allow the player to walk on the surface of all liquids, including water, honey, shimmer and even lava, without taking lava damage or triggering the On Fire! debuff (unlike the Water Walking Boots and Obsidian Water Walking Boots, which only allow walking on water and honey).");
        add("item.confluence.lava_waders.info2", "Lava Waders also allow the player to submerge in lava safely for 7 seconds (press the Down key to submerge), without incurring lava damage or triggering the On Fire! debuff.");
        add("item.confluence.lava_waders.info3", "All liquids, including lava, can be walked on indefinitely while the Lava Waders are equipped. ");
        add("item.confluence.lava_waders.info4", "The countdown only applies when submerged in lava, whereas the player can stand on its surface indefinitely.");
        add("item.confluence.lava_waders.info5", "It also reduces lava's base damage from 80 to 35.");
        add("item.confluence.lava_waders.info6", "It can be crafted at a Crafting Table using a Obsidian Water Walking Boots, a Lava Charm, and a Obsidian Rose or \n " +
            "a Molten Charm, a Water Walking Boots, and a Obsidian Rose or Molten Charm , a Obsidian Water Walking Boots ,and a Obsidian Rose or \n " +
            "a Molten Skull Rose ,and a Obsidian Water Walking Boots or a Molten Skull Rose ,and a Water Walking Boots.");
        add("item.confluence.lightning_boots.info", "Lightning Boots are an accessory that have essentially the same functionality as Spectre Boots, but with enhanced running speed.");
        add("item.confluence.lightning_boots.info2", "They have an 8% movement speed bonus compared to the Spectre Boots.");
        add("item.confluence.lightning_boots.info3", "This item can be crafted at a Crafting Table using a Spectre Boots, a Anklet of the Wind, and a Aglet.");
        add("item.confluence.lucky_horseshoe.info", "The Lucky Horseshoe is an accessory that negates fall damage and grants +0.05 luck.");
        add("item.confluence.lucky_horseshoe.info2", "It can be discovered in chests found within Desert Pyramids, End City, Jungle Pyramids, or Ancient Cities.");
        add("item.confluence.magiluminescence.info", "The Magiluminescence is a pre-Hardmode accessory that increases the player's maximum movement speed by 15% and their acceleration and deceleration by 75%.");
        add("item.confluence.magiluminescence.info2", "It can be found in End City Chests.");
        add("item.confluence.magiluminescence.info3", "This increase stacks with the bonus of the Hermes Boots and their variants.");
        add("item.confluence.magiluminescence.info4", "The Magiluminescence's movement speed buffs are disabled when the player is not grounded.");
        add("item.confluence.magma_skull.info", "The Magma Skull is a pre-Hardmode immunity accessory that grants the player immunity from blocks that inflict the Burning debuff and grants immunity to lava for 7 seconds.");
        add("item.confluence.magma_skull.info2", "It can be crafted at a Crafting Table with a Obsidian Skull and a Lava Charm.");
        add("item.confluence.molten_charm.info", "The Molten Charm is a pre-Hardmode accessory crafted from a Lava Charm and an Obsidian Skull, combining the effects of both items.");
        add("item.confluence.molten_charm.info2", "The Molten Charm also grants immunity to the burning damage normally inflicted by contact with Meteorite, Hellstone, and Hellstone Brick. ");
        add("item.confluence.molten_charm.info3", "It does not grant immunity to other fire-based damage.");
        add("item.confluence.obsidian_horseshoe.info", "The Obsidian Horseshoe is an accessory that combines the functionality of the Lucky Horseshoe and the Obsidian Skull.");
        add("item.confluence.obsidian_horseshoe.info2", "It grants immunity to the burning damage inflicted by Meteorite, Hellstone, and Hellstone Bricks, negates fall damage and grants +0.05 luck.");
        add("item.confluence.obsidian_horseshoe.info3", "However, it does not provide the extra 1 point of defense that is granted by the Obsidian Skull.");
        add("item.confluence.obsidian_horseshoe.info4", "It can be crafted at a Crafting Table with a Lucky Horseshoe and a Obsidian Skull.");
        add("item.confluence.obsidian_water_walking_boots.info", "Obsidian Water Walking Boots are an accessory which combines the abilities of the Water Walking Boots and the Obsidian Skull.");
        add("item.confluence.obsidian_water_walking_boots.info2", "They allow the wearer to walk on water, honey, and shimmer, while also giving immunity to the Burning debuff.");
        add("item.confluence.obsidian_water_walking_boots.info3", "They do not allow walking on lava.");
        add("item.confluence.obsidian_water_walking_boots.info4", "It can be crafted at a Crafting Table with a Water Walking Boots and a Obsidian Skull.");
        add("item.confluence.pink_horseshoe_balloon.info", "The Pink Horseshoe Balloon is an accessory that grants immunity to fall damage, increases jump height by 33% and jump speed by 30%, grants a double jump, and grants +0.05 luck.");
        add("item.confluence.pink_horseshoe_balloon.info2", "The height of the first jump is increased from 6 blocks to 10.5 blocks (~75% increase), while the double jump's height is increased from 7 blocks to 13 blocks, reaching a total of 23.5 blocks with both jumps.");// 英文待改
        add("item.confluence.pink_horseshoe_balloon.info3", "It can be crafted at a Crafting Table with a Sharkron Balloon and a Lucky Horseshoe.");
        add("item.confluence.rocket_boots.info", "Rocket Boots are an accessory that allows a player to fly for a period of around 1.6 seconds (100 game frames).");
        add("item.confluence.rocket_boots.info2", "They require a jump from midair to activate (a double-jump, or a jump while falling).");
        add("item.confluence.rocket_boots.info3", "This item can be crafted at a Crafting Table using a Iron Boots,Iron Ingots and a Fire Charge.");
        add("item.confluence.rocket_boots.info4", "If the Cloud in a Bottle is also equipped, its double-jump effect will engage first; pressing and holding the jump key afterward while still in midair will then activate the Rocket Boots.");// 英文待改
        add("item.confluence.sailfish_boots.info", "Sailfish Boots are an accessory that allows the user to sprint.");
        add("item.confluence.sailfish_boots.info2", "It can be found in Shipwreck Chests.");
        add("item.confluence.sailfish_boots.info3", "When equipped, they allow the player to gain a speed boost after running for a certain distance continuously.");
        add("item.confluence.sailfish_boots.info4", "By running in one direction, the player's movement speed increases up to 40%.");
        add("item.confluence.sandstorm_in_a_balloon.info", "The Sandstorm in a Balloon is an accessory that combines the functions of the Sandstorm in a Bottle and the Shiny Red Balloon.");
        add("item.confluence.sandstorm_in_a_balloon.info2", "It can be crafted at a Crafting Table with a Sandstorm in a Bottle and a Shiny Red Balloon.");// 英文待改
        add("item.confluence.sandstorm_in_a_balloon.info3", "It boosts jump height by 33% and jump speed by 30%, increasing the player's first jump's height to 10.5 meters, and provides a double jump that can reach a height of 26.5 meters, for a total height of 37 meters.");
        add("item.confluence.sandstorm_in_a_bottle.info", "The Sandstorm in a Bottle is an accessory that can be found in Chests in the Desert Pyramid.");
        add("item.confluence.sandstorm_in_a_bottle.info2", "It is similar to the Cloud in a Bottle, providing a double-jump that allows for some extra height, but instead of a cloud beneath the feet, a sand jet appears and the character visually spins as they ascend on that jet.");
        add("item.confluence.sandstorm_in_a_bottle.info3", "The Sandstorm jump goes 15 meters into the air, totalling 21 meters when put together with the first jump.");// 英文待改
        add("item.confluence.sandstorm_in_a_bottle.info4", "This is nearly twice as high as the height achievable with the Cloud in a Bottle.");
        add("item.confluence.sharkron_balloon.info", "The Sharkron Balloon is an accessory that combines the effects of the Tsunami in a Bottle and the Balloon Pufferfish.");
        add("item.confluence.sharkron_balloon.info2", "It boosts jump height by 33% and jump speed by 30% increasing your first jump's height to ~10½ blocks (~75% increase), and provides a double jump that now goes 13 blocks, for a total height of 23½ blocks.");// 英文待改
        add("item.confluence.shiny_red_balloon.info", "The Shiny Red Balloon is an accessory that increases the player's jump height by 33% and jump speed by 30%, increasing the height that the player can jump from 6 blocks (12 feet) to ~10½ blocks (21 feet), effectively a ~75% boost. However, it oddly reduces jump height when underwater, from 8 blocks (16 feet) to ~7 blocks (14 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info2", "When the player has the Hero of the Village effect, the librarian has a chance to give the player this item.");
        add("item.confluence.shiny_red_balloon.info3", "The Shiny Red Balloon stacks with other jump-boosting accessories:");
        add("item.confluence.shiny_red_balloon.info4", "The Cloud in a Bottle's double-jump height is increased from 5 blocks (10 feet) to 9 blocks (18 feet), for a total jump height of 19½ blocks (39 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info5", "The Blizzard in a Bottle's double-jump height is increased from 8 blocks (16 feet) to 14½ blocks (29 feet), for a total jump height of 25 blocks (50 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info6", "The Sandstorm in a Bottle's double-jump height is increased from 14½ blocks (29 feet) to 26½ blocks (53 feet), for a total jump height of 37 blocks (74 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info7", "The Fart in a Jar's double-jump height is increased from 10½ blocks (21 feet) to 18½ blocks (37 feet), for a total jump height of 29 blocks (58 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info8", "The Tsunami in a Bottle's double-jump height is increased from 7 blocks (14 feet) to 12 blocks (24 feet), for a total jump height of 23½ blocks (47 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info9", "With the Rocket/Spectre Boots, flight is accelerated, making the same flight time carry the player higher and/or farther.");
        add("item.confluence.shiny_red_balloon.info10", "The Frog Leg jump bonus stacks additively with the Shiny Red Balloon bonus, letting the first jump reach 16½ blocks (33 feet).");// 英文待改
        add("item.confluence.shiny_red_balloon.info11", "With wings, flight time is increased.");
        add("item.confluence.shiny_red_balloon.info12", "However, the Honey Balloon and Balloon Pufferfish do not stack with the Shiny Red Balloon; they all grant the same benefits.");
        add("item.confluence.spectre_boots.info", "Spectre Boots are an accessory crafted from Rocket Boots and either Flurry Boots, Hermes Boots, Dunerider Boots, or Sailfish Boots, and combine their functionality.");
        add("item.confluence.spectre_boots.info2", "During flight, they emit a white/blue cloud trail, but unlike the Rocket Boots, they do not produce any light. Their flight time is about 1.6 seconds, identical to Rocket Boots.");
        add("item.confluence.terraspark_boots.info", "The Terraspark Boots are a pre-Hardmode accessory crafted by combining Frostspark Boots and Lava Waders at a Crafting Table, providing the benefits of both items.");
        add("item.confluence.terraspark_boots.info2", "They are the final upgrade of the Hermes/Sailfish/Flurry/Dunerider and Water Walking Boots.");
        add("item.confluence.tsunami_in_a_bottle.info", "The Tsunami in a Bottle is an accessory which can be found in Shipwreck Chests");
        add("item.confluence.tsunami_in_a_bottle.info2", "Similar to the Cloud in a Bottle, it allows the player to double-jump.");
        add("item.confluence.tsunami_in_a_bottle.info3", "The Tsunami jump goes 7 meters into the air, totalling 13 meters when put together with the first jump.");// 英文待改
        add("item.confluence.water_walking_boots.info", "Water Walking Boots are an accessory that allows a player to walk on water, honey, and shimmer.");
        add("item.confluence.water_walking_boots.info2", "The player moves across these liquids at normal speed without sinking.");
        add("item.confluence.water_walking_boots.info3", "They do not allow walking on lava. When the player is on a liquid, they may press Down to fall through the liquid, similar to a platform.");
        add("item.confluence.water_walking_boots.info4", "It can be found in Ocean Ruins Chests.");
        add("item.confluence.white_horseshoe_balloon.info", "The White Horseshoe Balloon is an accessory that grants immunity to fall damage, increases jump height by 33% and jump speed by 30%, grants a double-jump, and grants +0.05 luck.");
        add("item.confluence.white_horseshoe_balloon.info2", "The height of the first jump is increased from 6 blocks to 10.5 blocks (~75% increase), while the double-jump's height is increased from 8 blocks to 14.5 blocks, reaching a total of 25 blocks with both jumps.");// 英文待改
        add("item.confluence.white_horseshoe_balloon.info3", "It can be crafted at a Crafting Table with a Lucky Horseshoe and a Blizzard in a Balloon.");
        add("item.confluence.yellow_horseshoe_balloon.info", "The Yellow Horseshoe Balloon is an accessory that grants immunity to fall damage, increases jump height by 75%, grants a double-jump, and grants +0.05 luck.");
        add("item.confluence.yellow_horseshoe_balloon.info2", "The height of the first jump is increased from 6 blocks to 10.5 blocks, while the double-jump's height is increased from 15 blocks to 26.5 blocks, reaching a total of 37 blocks with both jumps.");// 英文待改
        add("item.confluence.yellow_horseshoe_balloon.info3", "It can be crafted at a Crafting Table with a Lucky Horseshoe and a Sandstorm in a Balloon.");
        add("item.confluence.copper_watch.info", "The Copper Watch is a low-tier watch.");
        add("item.confluence.copper_watch.info2", "When equipped in an accessory slot or simply held in the inventory, a clock will appear in the upper-right corner of the playing screen, revealing the in-game time accurate to the last hour.");
        add("item.confluence.copper_watch.info3", "This item can be crafted at a Crafting Table using redstone, a copper ingot, and a chain.");
        add("item.confluence.tin_watch.info", "The Tin Watch is a low-tier watch.");
        add("item.confluence.tin_watch.info2", "When equipped in an accessory slot or simply held in the inventory, a clock will appear in the upper-right corner of the playing screen, revealing the in-game time accurate to the last hour. ");
        add("item.confluence.tin_watch.info3", "This item can be crafted at a Crafting Table using redstone, a tin ingot, and a chain.");
        add("item.confluence.silver_watch.info", "The Silver Watch is a mid-tier watch.");
        add("item.confluence.silver_watch.info2", "When equipped in an accessory slot or simply held in the inventory, a clock will appear in the upper-right corner of the playing screen, revealing the in-game time accurate to the last half an hour.");
        add("item.confluence.silver_watch.info3", "This item can be crafted at a Crafting Table using redstone, a silver ingot, and a chain.");
        add("item.confluence.tungsten_watch.info", "The Tungsten Watch is a mid-tier watch.");
        add("item.confluence.tungsten_watch.info2", "When equipped in an accessory slot or held in the inventory, a clock icon will appear in the upper-right corner of the playing screen, revealing the in-game time accurate to the last half-hour.");
        add("item.confluence.tungsten_watch.info3", "This item can be crafted at a Crafting Table using redstone, a tungsten ingot, and a chain.");
        add("item.confluence.gold_watch.info", "The Gold Watch is a high-tier watch. The Platinum Watch is an alternative to it.");
        add("item.confluence.gold_watch.info2", "When equipped in an accessory slot or simply held in the inventory, a clock will appear in the upper-right corner of the playing screen, revealing the in-game time accurate to the last minute. ");
        add("item.confluence.gold_watch.info3", "This item can be crafted at a Crafting Table using redstone, a gold ingot, and a chain.");
        add("item.confluence.platinum_watch.info", "The Platinum Watch is a high-tier watch.");
        add("item.confluence.platinum_watch.info2", "When equipped in an accessory slot or simply held in the inventory, a clock will appear in the upper-right corner of the playing screen, revealing the in-game time accurate to the last minute.");
        add("item.confluence.platinum_watch.info3", "This item can be crafted at a Crafting Table using redstone, a platinum ingot, and a chain.");
        add("item.confluence.depth_meter.info", "The Depth Meter is an informational accessory that displays the player's depth.");
        add("item.confluence.depth_meter.info2", "The Depth Meter have a Chance to be dropped from Bats.");
        add("item.confluence.compass.info", "The Compass is an informational accessory that enables an on-screen display of the player's lateral position when equipped.");
        add("item.confluence.compass.info2", "The Compass have a Chance to be dropped from Bats.");
        add("item.confluence.dps_meter.info", "The DPS Meter is an informational accessory that displays the DPS of the weapon that last dealt damage to any entity.");
        add("item.confluence.dps_meter.info2", "The DPS Meter a Chance to be dropped from Creeper.");
        add("item.confluence.fishermans_pocket_guide.info", "The Fisherman's Pocket Guide is an informational accessory that displays the player's current Fishing Power.");
        add("item.confluence.fishermans_pocket_guide.info2", "This item can be located in the chests found within Fisherman Villagers' houses.");
        add("item.confluence.fish_finder.info", "The Fish Finder is an informational accessory that provides the information displayed by the Fisherman's Pocket Guide, which displays the player's current fishing power, Weather Radio, which displays the current weather, and Sextant, which displays the moon's current phase.");
        add("item.confluence.fish_finder.info2", "This item can be crafted at a Crafting Table using a Fisherman's Pocket Guide, a Weather Radio, and a Sextant.");
        add("item.confluence.goblin_tech.info", "The Goblin Tech is an informational accessory, and is a combination of the Stopwatch, DPS Meter, and Metal Detector, displaying the most valuable nearby item, your current DPS, and your speed.");
        add("item.confluence.goblin_tech.info2", "This item can be crafted at a Crafting Table using a Metal Detector, a Stopwatch, and a DPS Meter.");
        add("item.confluence.life_form_analyzer.info", "The Lifeform Analyzer is an informational accessory that displays the name of rare enemies, critters within 47.5 meters around the player.");
        add("item.confluence.life_form_analyzer.info2", "The Lifeform Analyzer have a Chance to be dropped from Glow Squid.");
        add("item.confluence.metal_detector.info", "The Metal Detector is an informational accessory that detects valuable objects, including ores, chests, Life Crystals, and Life Fruit, within 47.5 meters around the player.");
        add("item.confluence.metal_detector.info2", "The Metal Detector have a Chance to be dropped from Warden.");
        add("item.confluence.pda.info", "The PDA is a accessory which combines the functionalities of its ingredients, displaying fishing information, weather, moon phase, elevation, distance east/west, time, nearest valuable treasure, movement speed, current DPS, number of enemies killed, rare nearby creatures, and number of nearby enemies.");
        add("item.confluence.pda.info2", "This item can be crafted at a Crafting Table using a GPS, a Fish Finder, a R.E.K. 3000, and a Goblin Tech.");
        add("item.confluence.radar.info", "The Radar is an informational accessory which displays the number of nearby enemies in a 63.5-meter radius around the player.");
        add("item.confluence.radar.info2", "The Radar have a Chance to be dropped from Bats.");
        add("item.confluence.rek_3000.info", "The R.E.K. 3000 is a post-Skeletron informational accessory which provides information about the number of nearby enemies, the player's enemy kill count, and nearby rare creatures. ");
        add("item.confluence.rek_3000.info2", "This item can be crafted at a Crafting Table using a Tally Counter, a Lifeform Analyzer, and a Radar.");
        add("item.confluence.sextant.info", "The Sextant is an informational accessory that displays the current moon phase.");
        add("item.confluence.sextant.info2", "This item can be located in the chests found within Fisherman Villagers' houses.");
        add("item.confluence.stopwatch.info", "The Stopwatch is an informational accessory that displays the player's current movement speed in miles per hour.");
        add("item.confluence.stopwatch.info2", "This item can be crafted at a Crafting Table using a iron ingot, a redstone, and a Chain.");
        add("item.confluence.tally_counter.info", "The Tally Counter is a post-Skeletron informational accessory which displays how many enemies of a type have been killed.");
        add("item.confluence.tally_counter.info2", "The Tally Counter a Chance to be dropped from Creeper.");
        add("item.confluence.weather_radio.info", "The Weather Radio is an informational accessory that displays the current weather and wind speed on the Surface, even if the player is in the Underground, Cavern, or Underworld layers.");
        add("item.confluence.weather_radio.info2", "This item can be located in the chests found within Fisherman Villagers' houses.");
        add("item.confluence.band_of_regeneration.info", "The Band of Regeneration is an accessory that have a Chance to be dropped from Witch");
        add("item.confluence.band_of_regeneration.info2", "When equipped, it regenerates 0.2 health every second.");
        add("item.confluence.angler_earring.info", "The Angler Earring is an accessory that increases the player's current Fishing Power by 10 when equipped. ");
        add("item.confluence.angler_earring.info2", "This item can be located in the chests found within Fisherman Villagers' houses.");
        add("item.confluence.brain_of_confusion.info", "The Brain of Confusion is a accessory , upon taking damage while equipped, nearby enemies have a 3/5 (60%) chance of receiving the Confused debuff for at minimum 1.5 seconds.");
        add("item.confluence.brain_of_confusion.info2", "The Brain of Confusion have a Chance to be dropped from Zombie Villager.");
        add("item.confluence.gravity_globe.info", "The Gravity Globe is a accessory that allows the holder to flip gravity, so that the player falls up instead of down, and toggle gravity back to normal afterward.");
        add("item.confluence.gravity_globe.info2", "The Gravity Glob have a Chance to be dropped from Shulker.");
        add("item.confluence.hive_pack.info", "The Hive Pack is a accessory , when equipped, grants a 1/2 (50%) chance of replacing friendly Bee projectiles with larger \"Giant Bees\".");
        add("item.confluence.hive_pack.info2", "The Hive Pack have a Chance to be dropped from Bee.");
        add("item.confluence.royal_gel.info", "The Royal Gel is a pre-Hardmode accessory that pacifies most slimes when equipped, causing them to no longer pursue the player, and will deal no contact damage or fire projectiles, even if the player deals damage to them.");
        add("item.confluence.royal_gel.info2", "The Royal Gel have a Chance to be dropped from Slime.");
        add("item.confluence.shield_of_cthulhu.info", "The Shield of Cthulhu is a pre-Hardmode shield accessory that allows the player to perform a dash attack by double-tapping either the Left or Right key.");
        add("item.confluence.shield_of_cthulhu.info2", "It have a Chance to be dropped from Ravager.");
        add("item.confluence.worm_scarf.info", "The Worm Scarf is a pre-Hardmode accessory that reduces incoming damage by 17%.");
        add("item.confluence.worm_scarf.info2", "When a player has a tamed cat, the cat may give the player this item when they wake up.");
        add("item.confluence.ancient_chisel.info", "The Ancient Chisel is an accessory that increases mining speed by 25%.");
        add("item.confluence.ancient_chisel.info2", "The item can be pass the Archaeology to obtained.");
        add("item.confluence.architect_gizmo_pack.info", "The Architect Gizmo Pack is an accessory which increases block and wall placement speed and reach and automatically paints placed objects, essentially granting all of the effects of the accessories used to craft it.");
        add("item.confluence.architect_gizmo_pack.info2", "This item can be crafted at a Crafting Table using a Brick Layer, a Extendo Grip, a Paint Sprayer, and a Portable Cement Mixer.");
        add("item.confluence.brick_layer.info", "The Brick Layer is an accessory which increases the player's placement speed by 50% when placing blocks, furniture, and any other placeable items.");
        add("item.confluence.brick_layer.info2", "This item can be located in the chests found within Toolsmith Villagers' houses.");
        add("item.confluence.extendo_grip.info", "The Extendo Grip is an accessory that extends block mining and placement reach by 3 meters.");
        add("item.confluence.extendo_grip.info2", "It can be discovered in chests found within Desert Pyramids.");
        add("item.confluence.portable_cement_mixer.info", "The Portable Cement Mixer is an accessory that increases the speed at which the player can place background walls by 50%.");
        add("item.confluence.portable_cement_mixer.info2", "This item can be located in the chests found within Toolsmith Villagers' houses.");
        add("item.confluence.toolbelt.info", "The Toolbelt is an accessory item that increases the range of block placement by one meter.");
        add("item.confluence.toolbelt.info2", "This item can be located in the chests found within Toolsmith Villagers' houses.");
        add("item.confluence.toolbox.info", "The Toolbox can be located in the chests found within Toolsmith Villagers' houses.");
        add("item.confluence.toolbox.info2", "It increases item placement and tool range by 1");
        add("item.confluence.flower_boots.info", "Flower Boots are a rare accessory which cause flowers to grow when the player walks on grass.");
        add("item.confluence.flower_boots.info2", "The Flower Boots have a Chance to be dropped from Moss Block.");
        add("item.confluence.treasure_magnet.info", "The Treasure Magnet is a accessory which increases the pickup range of items to 6.25 meters while equipped.");
        add("item.confluence.treasure_magnet.info2", "It can be discovered in chests found within Desert Pyramids, End City, Jungle Pyramids, or Ancient Cities.");
        add("item.confluence.ankh_charm.info", "The Ankh Charm is a Hardmode immunity accessory which combines the effects of the Detoxification Capsule, Nutrient Solution, The Plan, Searchlight, and Explorers Equipment");
        add("item.confluence.ankh_charm.info2", "This item can be crafted at a Crafting Table using a Armor Bracing, a Medicated Bandage, a The Plan , a Countercurse Mantra and a Reflective Shades.");
        add("item.confluence.ankh_shield.info", "The Ankh Shield is a Hardmode shield and immunity accessory which provides immunity to 10 different debuffs, knockback immunity, and an additional 4 defense.");
        add("item.confluence.ankh_shield.info2", "It is crafted from an Obsidian Shield and an Ankh Charm at the Crafting Table.");
        add("item.confluence.avenger_emblem.info", "The Avenger Emblem is a Hardmode accessory that grants 12% increased damage to all weapons.");
        add("item.confluence.avenger_emblem.info2", "This item can be crafted at a Crafting Table using a Warrior / Ranger / Sorcerer / Summoner Emblem, five Soul of Might, five Soul of Sight , and five Soul of Fright.");
        add("item.confluence.bee_cloak.info", "The Bee Cloak is a Hardmode accessory that combines the effects of the Star Cloak and the Honey Comb.");
        add("item.confluence.bee_cloak.info2", "When the player is injured, three stars will fall on the player's current location and bees are released.");
        add("item.confluence.bee_cloak.info3", "It can be crafted at a Crafting Table with a Honey Comb and a Star Cloak.");
        add("item.confluence.berserkers_glove.info", "The Berserker's Glove is a accessory crafted from the Power Glove and Flesh Knuckles, granting the abilities of both.");
        add("item.confluence.berserkers_glove.info2", "It grants the following:");
        add("item.confluence.berserkers_glove.info3", "+8 defense");
        add("item.confluence.berserkers_glove.info4", "+12% increased melee speed");
        add("item.confluence.berserkers_glove.info5", "+100% increased melee knockback");
        add("item.confluence.berserkers_glove.info6", "+10% increased melee weapon size");
        add("item.confluence.berserkers_glove.info7", "+400 aggro");
        add("item.confluence.berserkers_glove.info8", "Autoswing for melee weapons and whips");
        add("item.confluence.bezoar.info", "The Bezoar is an immunity accessory that grants the player immunity to the Poisoned debuff.");
        add("item.confluence.bezoar.info2", "It have a Chance to be dropped from Cave Spider.");
        add("item.confluence.black_belt.info", "The Black Belt is a accessory that grants a 10% chance to dodge an attack, including instances of damage taken from hazards such as traps and lava. ");
        add("item.confluence.black_belt.info2", "It have a Chance to be dropped from Wither Skeleton.");
        add("item.confluence.blindfold.info", "The Blindfold is a immunity accessory that grants the player immunity to the Blindness debuff.");
        add("item.confluence.blindfold.info2", "It can be discovered in chests found within Ancient City.");
        add("item.confluence.celestial_stone.info", "The Celestial Stone is a accessory crafted from a Moon Stone and a Sun Stone at the Crafting Table");
        add("item.confluence.celestial_stone.info2", "It offers the same boosts as its components do, and as such, it is active during both day and night.");
        add("item.confluence.celestial_stone.info3", "It can be combined with the Moon Shell to form a Celestial Shell.");
        add("item.confluence.celestial_stone.info4", "Stat boosts:");
        add("item.confluence.celestial_stone.info5", "+10% melee speed");
        add("item.confluence.celestial_stone.info6", "+10% damage (all types)");
        add("item.confluence.celestial_stone.info7", "+2% critical strike chance");
        add("item.confluence.celestial_stone.info8", "+1 HP/s life regeneration");
        add("item.confluence.celestial_stone.info9", "+4 defense");
        add("item.confluence.celestial_stone.info10", "+15% mining speed");
        add("item.confluence.celestial_stone.info11", "+0.5 minion knockback");
        add("item.confluence.cobalt_shield.info", "The Cobalt Shield is a shield accessory that grants the player immunity to knockback when equipped, and also provides 1 defense.");
        add("item.confluence.cobalt_shield.info2", "It have a Chance to be dropped from Ravager.");
        add("item.confluence.cross_necklace.info", "The Cross Necklace is a accessory that doubles the duration of the invincibility a player is afforded after taking damage, from about 0.67 seconds to about 1.33 seconds.");
        add("item.confluence.cross_necklace.info2", "It have a Chance to be dropped from Evoker.");
        add("item.confluence.destroyer_emblem.info", "The Destroyer Emblem is a accessory that gives the player 10% increased damage and 8% increased critical strike chance.");
        add("item.confluence.destroyer_emblem.info2", "It can be crafted at a Crafting Table with a Avenger Emblem and a Eye of the Golem.");
        add("item.confluence.detoxification_capsule.info", "The Detoxification Capsule is an accessory that grants the player immunity to the Poisoned debuff and Wither debuff.");
        add("item.confluence.detoxification_capsule.info2", "It can be crafted at a Crafting Table with a Bezoar and a Holy Water.");
        add("item.confluence.energy_bar.info", "The Energy Bar is an accessory that grants the player immunity to the Hunger debuff.");
        add("item.confluence.energy_bar.info2", "It have a Chance to be dropped from Zombified Piglin.");
        add("item.confluence.explorers_equipment.info", "The Explorers Equipment is an accessory that grants the player immunity to the Poisoned debuff and Wither debuff.");
        add("item.confluence.explorers_equipment.info2", "It can be crafted at a Crafting Table with a Hand Drill and a Shot Put.");
        add("item.confluence.eye_of_the_golem.info", "The Eye of the Golem is a Hardmode, post-Golem accessory that grants +10% critical strike chance.");
        add("item.confluence.eye_of_the_golem.info2", "It can be discovered in chests found within Jungle Pyramids.");
        add("item.confluence.fast_clock.info", "The Fast Clock is a Hardmode immunity accessory that grants the player immunity to the Slow debuff.");
        add("item.confluence.fast_clock.info2", "It have a Chance to be dropped from Stray.");
        add("item.confluence.feral_claws.info", "Feral Claws are an accessory that increases melee attack speed by 12%, and stacks with all other attack speed boosts.");
        add("item.confluence.feral_claws.info2", "It can be discovered in chests found within Jungle Pyramids.");
        add("item.confluence.fire_gauntlet.info", "The Fire Gauntlet is a Hardmode accessory that boosts melee weapons in the following ways:");
        add("item.confluence.fire_gauntlet.info2", "They cause the Hellfire debuff");
        add("item.confluence.fire_gauntlet.info3", "+100% knockback");
        add("item.confluence.fire_gauntlet.info4", "+12% damage");
        add("item.confluence.fire_gauntlet.info5", "+12% attack speed");
        add("item.confluence.fire_gauntlet.info6", "They gain autoswing");
        add("item.confluence.fire_gauntlet.info7", "+10% size on some melee weapons");
        add("item.confluence.fire_gauntlet.info8", "This item can be crafted at a Crafting Table with a Magma Stone and a Mechanical Glove.");
        add("item.confluence.flashlight.info", "The Explorers Equipment is an accessory that grants the player immunity to the Darkness debuff.");
        add("item.confluence.flashlight.info2", "It can be discovered in chests found within Stronghold.");
        add("item.confluence.flesh_knuckles.info", "Flesh Knuckles are a accessory that grants the player's defense by 8 and aggro by 400.");
        add("item.confluence.flesh_knuckles.info2", "It have a Chance to be dropped from Piglin Brute.");
        add("item.confluence.frozen_shield.info", "The Frozen Shield is a accessory.");
        add("item.confluence.frozen_shield.info2", "It redirects some damage done to teammates to the player ,increases defense by 6, grants immunity to knockback, and provides the player with a damage-absorbing shield when they are at low health.");
        add("item.confluence.frozen_shield.info3", "It can be crafted at a Crafting Table with a Paladin's Shield and a Frozen Turtle Shell.");
        add("item.confluence.frozen_turtle_shell.info", "The Frozen Turtle Shell is a The Frozen Turtle Shell is accessory that grants the Ice Barrier buff when equipped for Gives 25% damage reduction as long as their health remains at or below 50%.");
        add("item.confluence.frozen_turtle_shell.info2", "When the Turtle died for Freezing chance to dropped this item.");
        add("item.confluence.hand_drill.info", "The Hand Drill is an accessory that grants the player immunity to the Mining Fatigue debuff.");
        add("item.confluence.hand_drill.info2", "It have a Chance to be dropped from Elder Guardian.");
        add("item.confluence.hero_shield.info", "The Hero Shield is a accessory that redirects 25% of damage from players on the same team while above 25% health, similar to the effect of the Paladin's Shield.");
        add("item.confluence.hero_shield.info2", "It can be crafted at a Crafting Table with a Paladin's Shield and a Flesh Knuckles.");
        add("item.confluence.holy_water.info", "The Holy Water is an accessory that grants the player immunity to the Wither debuff.");
        add("item.confluence.holy_water.info2", "It have a Chance to be dropped from Wither Skeleton.");
        add("item.confluence.honey_comb.info", "The Honey Comb is a accessory which, when equipped, spawns 1–3 (or 1–4 with a Hive Pack equipped) bee projectiles which attack nearby enemies each time the player takes damage.");
        add("item.confluence.honey_comb.info2", "It have a Chance to be dropped from Wither Bee.");
        add("item.confluence.magic_quiver.info", "The Magic Quiver is a accessory that boosts arrows and stakes.");
        add("item.confluence.magic_quiver.info2", "It grants the following boosts to arrow and stake-firing weapons:");
        add("item.confluence.magic_quiver.info3", "+10% damage");
        add("item.confluence.magic_quiver.info4", "+20% velocity");
        add("item.confluence.magic_quiver.info5", "+20% chance not to consume ammo");
        add("item.confluence.magic_quiver.info6", "It will also increase the knockback of all arrow and stake-firing weapons by 10%.");
        add("item.confluence.magic_quiver.info7", "This item have a Chance to be dropped from Wither Skeleton.");
        add("item.confluence.magma_stone.info", "The Magma Stone is an accessory that drops from Blaze.");
        add("item.confluence.magma_stone.info2", "It causes the wearer's melee attacks to inflict the Hellfire on enemies.");
        add("item.confluence.mechanical_glove.info", "The Mechanical Glove is a accessory that doubles all melee weapons' knockback, and increases melee damage and speed by 12%.");
        add("item.confluence.mechanical_glove.info2", "It can be crafted at a Crafting Table with a Power Glove and a Avenger Emblem.");
        add("item.confluence.molten_quiver.info", "The Molten Quiver is a accessory that has the same effects as the Magic Quiver with the addition of turning Wooden Arrows into Flaming Arrows when fired from a bow.");
        add("item.confluence.molten_quiver.info2", "It can be crafted at a Crafting Table with a Magic Quiver and a Magma Stone.");
        add("item.confluence.molten_skull_rose.info", "The Molten Skull Rose is an accessory that represents the final upgrade of the Obsidian Skull.");
        add("item.confluence.molten_skull_rose.info2", "It grants 7 seconds of immunity to lava, immunity from fire blocks, and reduces damage taken from lava.");
        add("item.confluence.molten_skull_rose.info3", "It can be crafted at a Crafting Table using a Obsidian Skull Rose and a Lava Charm , or a Magma Skull and a Obsidian Rose, or a Magma Skull and a Obsidian Skull Rose.");
        add("item.confluence.moon_stone.info", "The Moon Stone is a accessory which has a chance to dropped from Phantom");
        add("item.confluence.moon_stone.info2", "It grants the same stat bonuses as the Sun Stone, but these bonuses will only activate during the night or during a Solar Eclipse.");
        add("item.confluence.moon_stone.info3", "The stat bonuses are:");
        add("item.confluence.moon_stone.info4", "+10% melee speed");
        add("item.confluence.moon_stone.info5", "+10% damage (all types)");
        add("item.confluence.moon_stone.info6", "+2% critical strike chance");
        add("item.confluence.moon_stone.info7", "+1 HP/s health regeneration");
        add("item.confluence.moon_stone.info8", "+4 defense");
        add("item.confluence.moon_stone.info9", "+15% mining speed");
        add("item.confluence.moon_stone.info10", "+0.5 minion knockback");// 英文待改
        add("item.confluence.nutrient_solution.info", "The Nutrient Solutiont is an accessory that grants the player immunity to the Weakness debuff and Hunger debuff");
        add("item.confluence.nutrient_solution.info2", "It can be crafted at a Crafting Table with a Vitamins and a Energy Bar.");
        add("item.confluence.obsidian_rose.info", "The Obsidian Rose is an accessory that reduces the base damage taken from lava from 80 to and halves the duration of the On Fire! debuff inflicted by it from 7 / 14 / 17.5 seconds to 3.5 / 7 / 8.75 seconds.");
        add("item.confluence.obsidian_rose.info2", "It can be discovered in chests found within Bastion Remnant.");
        add("item.confluence.obsidian_shield.info", "The Obsidian Shield is a shield accessory made from the Cobalt Shield and Obsidian Skull, and combines their functionality as well as their defense boosts.");
        add("item.confluence.obsidian_shield.info2", "It grants immunity from knockback, as well as immunity from the Burning debuff, and provides 2 defense.");
        add("item.confluence.obsidian_skull.info", "The Obsidian Skull is an immunity accessory that grants immunity to the Burning debuff normally inflicted by contact with Meteorite, Hellstone, and Hellstone Bricks.");
        add("item.confluence.obsidian_skull.info2", "It additionally provides 1 defense.");
        add("item.confluence.obsidian_skull.info3", "It can be crafted at a Crafting Table using a Obsidian and a Wither Skeleton Head.");
        add("item.confluence.obsidian_skull_rose.info", "The Obsidian Skull Rose is an accessory crafted from the Obsidian Skull and Obsidian Rose that combines their functionality, granting immunity to fire blocks and reduced damage from lava.");
        add("item.confluence.paladins_shield.info", "The Paladin's Shield is a accessory that functions primarily in multiplayer.");
        add("item.confluence.paladins_shield.info2", "While equipped, and when the wearer's health is above 25%, it grants all nearby players on their team the Paladin's Shield buff: this buff causes 25% of any damage inflicted on them to transfer to the wearer of the shield instead.");
        add("item.confluence.paladins_shield.info3", "In both singleplayer and multiplayer, the Paladin's Shield also provides the wearer immunity to knockback, and provides an additional 6 defense. ");
        add("item.confluence.paladins_shield.info4", "It to be dropped from Ender Dragon.");
        add("item.confluence.panic_necklace.info", "The Panic Necklace is an accessory, that, when equipped, grants the player the Panic! buff for 8 seconds when harmed by any source of damage besides fire blocks, increasing movement speed by 100%.");
        add("item.confluence.panic_necklace.info2", "It can be found in Chests in the Dungeon.");
        add("item.confluence.power_glove.info", "The Power Glove is a Hardmode accessory that doubles melee knockback and adds 12% increased melee speed.");
        add("item.confluence.power_glove.info2", "It can be crafted at a Crafting Table using a Titan Glove and a Feral Claws.");
        add("item.confluence.putrid_scent.info", "The Putrid Scent is a accessory which increases damage and critical strike chance by 5% and decreases aggro by 400.");
        add("item.confluence.putrid_scent.info2", "It can be found in Chests in the Dungeon.");
        add("item.confluence.ranger_emblem.info", "The Ranger Emblem is a Hardmode accessory that increases ranged damage by 15%.");
        add("item.confluence.ranger_emblem.info2", "It have a Chance to be dropped from Skeleton.");
        add("item.confluence.recon_scope.info", "The Recon Scope is a accessory that combines the effects of the Sniper Scope and the aggro-reducing effect of the Putrid Scent.");
        add("item.confluence.recon_scope.info2", "It can be crafted at a Crafting Table using a Sniper Scope and a Putrid Scent.");
        add("item.confluence.rifle_scope.info", "The Rifle Scope is a accessory.");
        add("item.confluence.rifle_scope.info2", "It grants the ability to increase view range while holding a gun.");
        add("item.confluence.rifle_scope.info3", "It have a Chance to be dropped from Enderman.");
        add("item.confluence.searchlight.info", "The Searchlight is an accessory that grants the player immunity to the Drakness debuff and Blindness debuff.");
        add("item.confluence.searchlight.info2", "It can be crafted at a Crafting Table using a Blindfold and a Flashlight.");
        add("item.confluence.shackle.info", "The Shackle is an accessory that grants 1 point of defense.");
        add("item.confluence.shackle.info2", "It can be found in Chests in the Dungeon.");
        add("item.confluence.shark_tooth_necklace.info", "The Shark Tooth Necklace is an accessory which has a chance to drop from Drowned.");
        add("item.confluence.shark_tooth_necklace.info2", "It increases the player's armor penetration by 5 when equipped.");
        add("item.confluence.shot_put.info", "The Shot Put is an accessory that grants the player immunity to the Levitation debuff.");
        add("item.confluence.shot_put.info2", "It can be found in Chests in the Stronghold.");
        add("item.confluence.sniper_scope.info", "The Sniper Scope is a accessory that provides 10% increased ranged damage and 10% increased ranged critical strike chance.");
        add("item.confluence.sniper_scope.info2", "It can be crafted at a Crafting Table using a Rifle Scope and a Destroyer Emblem.");
        add("item.confluence.sorcerer_emblem.info", "The Sorcerer Emblem is a Hardmode accessory that increases magic damage by 15%.");
        add("item.confluence.sorcerer_emblem.info2", "It have a Chance to be dropped from Evoker.");
        add("item.confluence.stalkers_quiver.info", "The Stalker's Quiver is a accessory that combines the effects of the Magic Quiver and the aggro-reducing effect of the Putrid Scent.");
        add("item.confluence.stalkers_quiver.info2", "Unlike the general ranged damage bonuses, it only boosts the damage of the weapons and does not affect damage of the ammo.");
        add("item.confluence.stalkers_quiver.info3", "It can be crafted at a Crafting Table using a Magic Quiver and a Putrid Scent.");
        add("item.confluence.star_cloak.info", "The Star Cloak is a accessory that causes three stars to fall around the player whenever the player takes damage.");
        add("item.confluence.star_cloak.info2", "It can be discovered in chests found within Woodland Mansion.");
        add("item.confluence.star_veil.info", "The Star Veil is a accessory that combines the functions of the Star Cloak and the Cross Necklace, spawning falling star projectiles and increasing the length of the invincibility frames granted when the player is hurt.");
        add("item.confluence.star_veil.info2", "It can be crafted at a Crafting Table using a Star Cloak and a Cross Necklace.");
        add("item.confluence.stinger_necklace.info", "The Stinger Necklace is a accessory.");
        add("item.confluence.stinger_necklace.info2", "This item combines the Shark Tooth Necklace and the Honey Comb accessories, giving the player 5 armor penetration as well as releasing 1–3 bees when the wearer takes damage and granting the Honey buff for 5 seconds.");
        add("item.confluence.stinger_necklace.info3", "It can be crafted at a Crafting Table using a Shark Tooth Necklace and a Honey Comb.");
        add("item.confluence.sun_stone.info", "The Sun Stone is a accessory which has a chance of being dropped by Blaze.");
        add("item.confluence.sun_stone.info2", "The stat bonuses are:");
        add("item.confluence.sun_stone.info3", "+10% melee speed");
        add("item.confluence.sun_stone.info4", "+10% damage (all types)");
        add("item.confluence.sun_stone.info5", "+2% critical strike chance");
        add("item.confluence.sun_stone.info6", "+1 HP/s health regeneration");
        add("item.confluence.sun_stone.info7", "+4 defense");
        add("item.confluence.sun_stone.info8", "+15% mining speed");
        add("item.confluence.sun_stone.info9", "+0.5 minion knockback");// 英文待改
        add("item.confluence.sweetheart_necklace.info", "The Sweetheart Necklace is an accessory which releases a concentrated burst of bees and grants the player the Panic! and  Honey buffs when damaged, increasing movement speed by 100% for 8 seconds and  health regeneration for 5 seconds.");
        add("item.confluence.sweetheart_necklace.info2", "It can be crafted at a Crafting Table using a Honey Comb and a Panic Necklace.");
        add("item.confluence.the_plan.info", "The Plan is a Hardmode immunity accessory which combines the functions of the Fast Clock and the Trifold Map, granting immunity to the Slow and Nausea debuffs.");
        add("item.confluence.the_plan.info2", "It can be crafted at a Crafting Table using a Fast Clock and a Trifold Map.");
        add("item.confluence.titan_glove.info", "The Titan Glove is a accessory that doubles player-inflicted melee knockback.");
        add("item.confluence.titan_glove.info2", "It can be found in Chests in the Mineshaft.");
        add("item.confluence.trifold_map.info", "The Trifold Map is a accessory that grants the player immunity to the Nausea debuff.");
        add("item.confluence.trifold_map.info2", "It can be found in Chests in the Stronghold Library.");
        add("item.confluence.vitamins.info", "Vitamins are a accessory that grants the player immunity to the Weakness debuff.");
        add("item.confluence.vitamins.info2", "It have a Chance to be dropped from Witch.");
        add("item.confluence.warrior_emblem.info", "The Warrior Emblem is a accessory that increases melee damage by 15%.");
        add("item.confluence.warrior_emblem.info2", "It have a Chance to be dropped from Vindicator.");
        add("item.confluence.demon_heart.info", "It to be dropped from Wither.");
        add("item.confluence.climbing_claws.info", "It have a Chance to be dropped from Spider.");
        add("item.confluence.shoe_spikes.info", "It have a Chance to be dropped from Cave Spider.");
        add("item.confluence.tabi.info", "It can be found in Chests in the Fortress.");
        add("item.confluence.step_stool.info", "You can use Step Stool to upgrade its Extra Step from Smithing");

        add("death.attack.star_cloak", "%1$s was squashed by a falling star");

        add("attribute.name.generic.critical_chance", "Critical Chance");

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
