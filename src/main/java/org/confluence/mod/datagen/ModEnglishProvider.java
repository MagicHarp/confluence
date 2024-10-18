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
import org.confluence.mod.item.potion.VanillaPotionItem;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, Confluence.MODID, "en_us");
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    @Override
    protected void addTranslations() {
        add("config.jade.plugin_confluence.jade_mechanical_component", "Mechanical Info");

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
        add("creativetab.confluence.accessories", "Confluence | Accessories");
        add("creativetab.confluence.mechanical", "Confluence | Mechanical");
        add("creativetab.confluence.developer", "Confluence | Developer");

        add("item.confluence.meteorite_ingot.tooltip", "Warm to the touch");
        add("item.confluence.alpha.desc", "C418 - alpha");

        add("bossevent.confluence.cthulhu_eye.generate", "The CthulhuEye has awakened!");
        add("bossevent.confluence.cthulhu_eye.death", "The CthulhuEye been defeated!");
        add("bossevent.confluence.cthulhu_eye.leave", "The CthulhuEye leaved!");

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
        add("curios.tooltip.scope2", "Hold ranged weapon and crouch to zoom multiOut");
        add("curios.tooltip.wall_climb", "Allows the ability to climb walls, hold shift key to slide down");
        add("curios.tooltip.wall_slide", "Allows the ability to slide down walls, hold shift key to slide down quickly");
        add("curios.tooltip.tabi", "Allows the ability to dash while double tap a direction");
        add("curios.tooltip.dodge", "Gives a chance to dodge attacks");

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
        add("info.confluence.network", "#%s Signal: %s");
        add("info.confluence.potion_mana", "Potion Mana: %s");

        add("key.confluence.hook", "Throwing Hook");
        add("key.confluence.metal_detector", "Detect Metal");
        add("key.confluence.step_stool", "Step Stool");

        add("curios.identifier.hook", "Hook");
        add("curios.identifier.accessory", "Accessory");
        add("curios.modifiers.accessory", "When worn as accessory:");

        add("item.confluence.demon_heart.tooltip", "Permanently increases the number of accessory slots");
        add("item.confluence.adhesive_bandage.tooltip", "Immunity to Bleeding");
        add("item.confluence.medicated_bandage.tooltip", "Immunity to Poison and Bleeding");
        add("item.confluence.armor_bracing.tooltip", "Immunity to Weakness and Broken Armor");
        add("item.confluence.armor_polish.tooltip", "Immunity to Broken Armor");
        add("item.confluence.vitamins.tooltip", "Immunity to Weakness");
        add("item.confluence.the_plan.tooltip", "Immunity to Slow and Confusion");
        add("item.confluence.fast_clock.tooltip", "Immunity to Slow");
        add("item.confluence.trifold_map.tooltip", "Immunity to Confusion");
        add("item.confluence.countercurse_mantra.tooltip", "Immunity to Silence and Curse");
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
        add("item.confluence.panic_necklace.tooltip", "Increases movement speed after taking damage");
        add("item.confluence.paladins_shield.tooltip", "Absorbs 25% of damage done to players on your team when above 25% life");
        add("item.confluence.frozen_shield.tooltip", "Absorbs 25% of damage done to players on your team when above 25% life");
        add("item.confluence.frozen_shield.tooltip2", "Puts a shell around the owner when below 50% life that reduces damage by 25%");
        add("item.confluence.frozen_turtle_shell.tooltip", "Puts a shell around the owner when below 50% life that reduces damage by 25%");
        add("item.confluence.fire_gauntlet.tooltip", "Increases melee knockback and melee attacks inflict fire damage");
        add("item.confluence.cross_necklace.tooltip", "Increases TOTAL_LENGTH of invincibility after taking damage");
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
        add("item.confluence.sweetheart_necklace.tooltip", "When damaged, the bee is released and the user is immersed in honey and increases movement speed");
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
        add("item.confluence.magic_quiver.tooltip2", "20% chance to not consume arrows");
        add("item.confluence.molten_quiver.tooltip", "Lights wooden arrows ablaze");
        add("item.confluence.molten_quiver.tooltip2", "'Quiver in fear!'");
        add("item.confluence.recon_scope.tooltip", "'Enemy spotted'");
        add("item.confluence.portable_cement_mixer.tooltip", "Decreased 'Right Click Delay' by 1");
        add("item.confluence.brick_layer.tooltip", "Decreased 'Right Click Delay' by 1");
        add("item.confluence.architect_gizmo_pack.tooltip", "Decreased 'Right Click Delay' by 2, cannot stack the decrease of its materials");
        add("item.confluence.climbing_claws.tooltip", "Improved ability if combined with Shoe Spikes");
        add("item.confluence.shoe_spikes.tooltip", "Improved ability if combined with Climbing Claws");
        add("item.confluence.frog_gear.tooltip", "'It ain't easy being green'");
        add("item.confluence.hand_of_creation.tooltip2", "Decreased 'Right Click Delay' by 3, cannot stack the decrease of its material");
        add("item.confluence.hand_of_creation.tooltip3", "Increases pickup range for items");
        add("item.confluence.step_stool.tooltip", "Press ↑ key to stand higher, and press Shift key to down");
        add("item.confluence.step_stool.tooltip2", "Extra Step: %s");
        add("item.confluence.vanilla_potion", "Potion");

        add("item.confluence.magic_conch.pos", "Pos: [%s, %s, %s]");

        add("death.attack.falling_star", "%1$s got a response from a meteor");
        add("death.attack.star_cloak", "%1$s was squashed by a falling star");
        add("death.attack.boulder", "%1$s  is crushed by boulder");

        add("painting.confluence.magic_harp.title", "MAGIC_HARP");
        add("painting.confluence.magic_harp.author", "BiliBili_魔法竖琴waaa，看上去傻傻的...");
        add("painting.confluence.westernat.title", "WESTERNAT");
        add("painting.confluence.westernat.author", "BiliBili_Westernat233，MC21世纪以来，最具有印象派主义的白桦树绘画!");
        add("painting.confluence.cooobrid.title", "COOOBRID");
        add("painting.confluence.cooobrid.author", "BiliBili_事一只一只一只鸽子，事一只只会咕咕咕的鸽子");
        add("painting.confluence.nakinosi.title", "NAKINOSI");
        add("painting.confluence.nakinosi.author", "BiliBili_咕咕咕的屑枕头，世界上最好看的渐变头发！");
        add("painting.confluence.maker.title", "MAKER");
        add("painting.confluence.maker.author", "BiliBili_Maker-2333，是Maker不是Marker！");
        add("painting.confluence.mustard_oasis.title", "MUSTARD_OASIS");
        add("painting.confluence.mustard_oasis.author", "BiliBili_芥末Oasis，芥末配fish，豪赤😋");
        add("painting.confluence.a_pigeon_delight.title", "A_PIGEON_DELIGHT");
        add("painting.confluence.a_pigeon_delight.author", "BiliBili_一只鸽子悦");
        add("painting.confluence.sheep_mink.title", "SHEEP_MINK");
        add("painting.confluence.sheep_mink.author", "BiliBili_眠羊敏克，“啊？我打json？”");
        add("painting.confluence.voila.title", "VOILA");
        add("painting.confluence.voila.author", "BiliBili_风起下片灬");
        add("painting.confluence.xuanyu_1725.title", "XUANYU");
        add("painting.confluence.xuanyu_1725.author", "BiliBili_轩宇1725");
        add("painting.confluence.shadow_end.title", "SHADOW_END");
        add("painting.confluence.shadow_end.author", "BiliBili_影末子");
        add("painting.confluence.kl_jiana.title", "KL_JIANA");
        add("painting.confluence.kl_jiana.author", "BiliBili_KL_JIANA");
        add("painting.confluence.hunao.title", "HUNAO");
        add("painting.confluence.hunao.author", "BiliBili_小胡闹鸭");
        add("painting.confluence.sihuai_2412.title", "SIHUAI_2412");
        add("painting.confluence.sihuai_2412.author", "BiliBili_思怀_2412");
        add("painting.confluence.old_sheep.title", "OLD_SHEEP");
        add("painting.confluence.old_sheep.author", "BiliBili_我叫老绵羊");
        add("painting.confluence.slime_dragon.title", "SLIME_DRAGON");
        add("painting.confluence.slime_dragon.author", "BiliBili_小史龙吖Slime_Dragon");
        add("painting.confluence.khaki_coffee_beans.title", "KHAKI_COFFEE_BEANS");
        add("painting.confluence.khaki_coffee_beans.author", "BiliBili_卡其色咖啡豆");
        add("painting.confluence.uqtqu_day.title", "UQTQU_DAY");
        add("painting.confluence.uqtqu_day.author", "BiliBili__昼泽_");
        add("painting.confluence.emerald_shenyi.title", "EMERALD_SHENYI");
        add("painting.confluence.emerald_shenyi.author", "BiliBili_Emerald_审翼");
        add("painting.confluence.chromatic.title", "CHROMATIC");
        add("painting.confluence.chromatic.author", "BiliBili_陌林_Chromatic");

        // new
        add("achievements.toast.complete", "Achievement achieved!");
        add("achievements.confluence.new_world.title", "Old World, New Journey!");
        add("achievements.confluence.new_world.description", "The afterlife of convergence and exchange");
        add("achievements.confluence.timber.title", "Timber!! ");
        add("achievements.confluence.timber.description", "Chop down your first tree.");
        add("achievements.confluence.benched.title", "Benched");
        add("achievements.confluence.benched.description", "Craft your first Crafting Table.");
        add("achievements.confluence.star_power.title", "Star Power");
        add("achievements.confluence.star_power.description", "Craft a mana crystal multiOut of fallen stars, and consume it.");
        add("achievements.confluence.ooo_shinny.title", "Ooo! Shiny!");
        add("achievements.confluence.ooo_shinny.description", "Mine your first nugget of ore with a pickaxe.");
        add("achievements.confluence.i_am_loot.title", "I Am Loot!");
        add("achievements.confluence.i_am_loot.description", "Discover a golden chest underground and take a peek at its contents.");
        add("achievements.confluence.hold_on_tight.title", "Hold on Tight!");
        add("achievements.confluence.hold_on_tight.description", "Equip your first grappling hook.");
        add("achievements.confluence.heavy_metal.title", "Heavy Metal");
        add("achievements.confluence.heavy_metal.description", "Obtain an anvil made from iron or lead.");
        add("achievements.confluence.heart_breaker.title", "Heart Breaker");
        add("achievements.confluence.heart_breaker.description", "Discover and smash your first heart crystal underground.");
        add("achievements.confluence.hammer_time.title", "Stop! Hammer Time! ");
        add("achievements.confluence.hammer_time.description", "Obtain your first hammer via crafting or otherwise.");
        add("achievements.confluence.boots_of_the_hero.title", "Boots of the Hero");
        add("achievements.confluence.boots_of_the_hero.description", "Forged from the finest boots of fire and ice.");
        add("achievements.confluence.black_mirror.title", "Black Mirror");
        add("achievements.confluence.black_mirror.description", "You'll never leave home without it again.");
        add("achievements.confluence.ankhumulation_complete.title", "Ankhumulation Complete");
        add("achievements.confluence.ankhumulation_complete.description", "The finest protection from unpleasant maladies and ailments.");
        add("achievements.confluence.a_shimmer_in_the_dark.title", "A Shimmer In The Dark");
        add("achievements.confluence.a_shimmer_in_the_dark.description", "Shimmer an item into another item. What other transmutations can you find?");

        add("achievements.confluence.pretty_in_pink.title", "Pretty in Pink");
        add("achievements.confluence.pretty_in_pink.description", "Kill pinky.");
        add("achievements.confluence.slippery_shinobi.title", "Slippery Shinobi ");
        add("achievements.confluence.slippery_shinobi.description", "Defeat King Slime, the lord of all things slimy.");
        add("achievements.confluence.eye_on_you.title", "Eye on You");
        add("achievements.confluence.eye_on_you.description", "“Defeat the Eye of Cthulhu, an ocular menace who only appears at night.");

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
        add("condition.confluence.shimmer_transmutation", "Required game phase: %s");

        add("item.confluence.aglet.info", "It can be found in surface Chests, Wooden Crates and Pearlwood Crates.");
        add("item.confluence.amber_horseshoe_balloon.info", "It can be crafted at a Tinkerer's Workshop with a Lucky Horseshoe and a Honey Balloon.");
        add("item.confluence.amber_horseshoe_balloon.info2", "On the Desktop version Desktop version, Console version Console version, and Mobile version Mobile version, it also grants the Honey buff after being damaged.");
        add("item.confluence.amphibian_boots.info", "The Amphibian Boots are crafted by combining the Sailfish Boots and Frog Leg at a Tinkerer's Workshop. It provides the benefits of both.");
        add("item.confluence.anklet_of_the_wind.info", "The effect also stacks with the movement speed bonus of the Aglet.");
        add("item.confluence.anklet_of_the_wind.info2", "It can be found in Jungle and Bramble Crates.");
        add("item.confluence.balloon_pufferfish.info", "It can be obtained via fishing in any biome.");
        add("item.confluence.blizzard_in_a_bottle.info", "The Blizard in a Bottle has a chance of being found inside Frozen Chests in the Ice biome, as well as chance of being obtained from Frozen and Boreal Crates.");
        add("item.confluence.cloud_in_a_bottle.info", "It can be found in Gold Chests in the Underground and Cavern layers.");
        add("item.confluence.dunerider_boots.info", "The Dunerider Boots has a chance of being found in Sandstone Chests in the Underground Desert and a chance of being found in Oasis Crates and Mirage Crates obtained from fishing in the Oasis.");
        add("item.confluence.fledgling_wings.info", "The Fledgling Wings can be obtained from Skyware Chests on Floating Islands, as well as Sky Crates and Azure Crates from fishing.");
        add("item.confluence.flippers.info", "It can be found in Water Chests and Ocean/Seaside Crates.");
        add("item.confluence.flurry_boots.info", "It can be found in Frozen Chests in the underground Snow Biome, as well as Boreal Crates.");
        add("item.confluence.frog_leg.info", "The Frog Leg is that can be fished in any viable body of water.");
        add("item.confluence.hand_warmer.info", "It is obtained from Presents.");
        add("item.confluence.hermes_boots.info", "Hermes Boots are that can be found in Chests in the Underground and Cavern layers.");
        add("item.confluence.ice_skates.info", "Ice Skates have a chance of being found inside Frozen Chests in the Ice biome, as well as chance of being obtained from Boreal Crates.");
        add("item.confluence.lava_charm.info", "It can only be found in Chests in the lower portion of the Caverns where lava pools can naturally spawn, as opposed to Chests throughout the entirety of the Caverns.");
        add("item.confluence.lucky_horseshoe.info", "The Lucky Horseshoe has a chance found inside Skyware Chests on Floating Islands, as well as chance of being obtained from Sky Crates and Azure Crates.");
        add("item.confluence.sailfish_boots.info", "This item is obtained from Wooden Crates and Iron Crates.");
        add("item.confluence.sandstorm_in_a_bottle.info", "The Sandstorm in a Bottle is found inside Gold Chests within Pyramids.");
        add("item.confluence.shoe_spike.info", "It can be found in Chests in the Underground and Cavern layers.");
        add("item.confluence.step_stool.info", "It can be obtained from wooden box and in a chest on the surface.");;
        add("item.confluence.tsunami_in_a_bottle.info", "The Tsunami in a Bottle is which can be found in Wooden, Iron, Pearlwood, and Mythril Crates.");
        add("item.confluence.water_walking_boots.info", "Water Walking Boots have a chance of being obtained from Water Chests, Ocean Crates, and Seaside Crates.");
        add("item.confluence.compass.info", "It can be");
        add("item.confluence.depth_meter.info", "It can be");
        add("item.confluence.dps_meter.info", "It can be");
        add("item.confluence.fishermans_pocket_guide.info", "The Fisherman's Pocket Guide have chance as a reward for completing the fisherman's fishing quests.");
        add("item.confluence.fish_finder.info", "The Fish Finder have chance as a reward for completing the fisherman's fishing quests.");
        add("item.confluence.mechanical_lens.info", "The Mechanical Lens by purchased from the mechanic villager.");
        add("item.confluence.metal_detector.info", "It can be");
        add("item.confluence.radar.info", "It can be");
        add("item.confluence.sextant.info", "The Sextant have chance as a reward for completing the fisherman's fishing quests.");
        add("item.confluence.tally_counter.info", "It can be");
        add("item.confluence.weather_radio.info", "The Weather Radio have chance as a reward for completing the fisherman's fishing quests.");
        add("item.confluence.band_of_regeneration.info", "It can be ");
        add("item.confluence.band_of_starpower.info", "It can be ");
        add("item.confluence.natures_gift.info", "It is acquired by harvesting a rare flower that grows on Jungle grass in the Underground Jungle, using any weapon or tool. The flower appears as a blue version of the Jungle Rose.");
        add("item.confluence.philosophers_stone.info", "It has a chance to be dropped by Mimics.");
        add("item.confluence.adhesive_bandage.info", "It has a chance to drop from Rusty Armored Bones, Werewolves, and Angler Fish.");
        add("item.confluence.armor_polish.info", "It has a chance to drop from Armored Skeletons and Blue Armored Bones.");
        add("item.confluence.bezoar.info", "It has a chance to drop from Hornets, Moss Hornets, and Toxic Sludges.");
        add("item.confluence.black_belt.info", "It has a chance to drop from Bone Lees in the post-Plantera Dungeon.");
        add("item.confluence.blindfold.info", "It has a chance to drop from Corrupt Slimes, Slimelings, Crimslimes, Dark Mummies, and Blood Mummies.");
        add("item.confluence.cobalt_shield.info", "It has a chance of being obtained from the Locked Gold Chests found in the Dungeon, as well as a 1/7 (14.29%) chance of being obtained from Golden Lock Boxes.");
        add("item.confluence.cross_necklace.info", "It has a chance to be dropped by Mimics.");
        add("item.confluence.eye_of_the_golem.info2", "It has a chance of being dropped by Golem.");
        add("item.confluence.fast_clock.info", "It has a chance to drop from Pixies, Mummies, and Wraiths.");
        add("item.confluence.feral_claws.info", "Feral Claws can be found in Jungle Crates, Bramble Crates, and Ivy Chests located in Jungle Shrines and at the base of a Living Mahogany Tree in the Underground Jungle.");
        add("item.confluence.flesh_knuckles.info", "It has a chance to be dropped by Crimson Mimics.");
        add("item.confluence.frozen_turtle_shell.info", "It has a chance of being dropped by Ice Tortoises in the Ice biome.");
        add("item.confluence.honey_comb.info3", "It has a chance to be dropped by the Queen Bee.");
        add("item.confluence.magma_stone.info", "The Magma Stone is that drops from Lava Bats and Hellbats in The Underworld.");
        add("item.confluence.megaphone.info", "It has a chance to drop from Pixies, Green Jellyfish, Dark Mummies, and Blood Mummies.");
        add("item.confluence.nazar.info", "It has a chance to drop from Cursed Skulls, Giant Cursed Skulls, Cursed Hammers, Crimson Axes, and Enchanted Swords.");
        add("item.confluence.obsidian_rose.info", "It has a chance of being dropped by Fire Imps, an enemy found in The Underworld.");
        add("item.confluence.paladins_shield.info", "The Paladin's Shield has a chance of being dropped by Paladins in the post-Plantera Dungeon.");
        add("item.confluence.panic_necklace.info", "The Panic Necklace has a chance of being obtained from destroying Crimson Hearts found in the Crimson or a chance from Crimson Crates and Hematic Crates, which are fished in the Crimson.");
        add("item.confluence.pocket_mirror.info", "It has a chance of being dropped by Medusas.");
        add("item.confluence.putrid_scent.info", "It has a chance to be dropped by Corrupt Mimics.");
        add("item.confluence.shackle.info", "It is dropped by most kinds of Zombies with a chance.");
        add("item.confluence.shark_tooth_necklace.info", "It has a chance to drop from Blood Zombies and Dripplers found during a Blood Moon.");
        add("item.confluence.sorcerer_emblem.info", "It has a chance to drop from the Wall of Flesh.");
        add("item.confluence.star_cloak.info", "The Star Cloak has a chance to be dropped by Mimics.");
        add("item.confluence.sun_stone.info", "The Sun Stone is a Hardmode, post-Golem accessory which has a chance of being dropped by Golem.");
        add("item.confluence.titan_glove.info", "The Titan Glove has a chance of being dropped by Mimics.");
        add("item.confluence.trifold_map.info2", "It has a chance to drop from Clowns, Giant Bats, Light Mummies.");
        add("item.confluence.vitamins.info", "It has a chance to drop from Corruptors and Floaty Grosses.");
        add("item.confluence.warrior_emblem.info", "It has a 1/4 (25%) chance to drop from the Wall of Flesh.");
        add("item.confluence.high_test_fishing_line.info", "Without the High Test Fishing Line equipped, there is a 1/7 (14.29%) chance that the line will break on any reel-in that was supposed to yield a catch.");
        add("item.confluence.lavaproof_fishing_hook.info", "The Lavaproof Fishing Hook is that can be found in Obsidian Crates and Hellstone Crates, both of which can be obtained from fishing in lava.");
        add("item.confluence.gold_ring.info", "The Gold Ring has chance to.");
        add("item.confluence.lucky_coin.info", "The Lucky Coin has chance to.");
        add("item.confluence.spectre_goggles.info", "The Spectre Goggles.");
        add("item.confluence.treasure_magnet.info", "The Treasure Magnet has chance to.");
        add("item.confluence.brain_of_confusion.info", "The Brain of Confusion is always dropped from the Brain of Cthulhu's Treasure Bag in Expert Mode.");
        add("item.confluence.gravity_globe.info", " The Gravity Globe is always dropped from the Moon Lord's Treasure Bag in Expert Mode.");
        add("item.confluence.hive_pack.info", "The Hive Pack is always dropped from the Queen Bee's Treasure Bag in Expert Mode.");
        add("item.confluence.royal_gel.info", "The Royal Gel is always dropped from King Slime's Treasure Bag in Expert Mode.");
        add("item.confluence.shield_of_cthulhu.info", "The Shield of Cthulhu is always dropped from the Eye of Cthulhu's Treasure Bag in Expert Mode.");
        add("item.confluence.worm_scarf.info", "The Worm Scarf is always dropped from the Eater of Worlds' Treasure Bag in Expert Mode.");

        add("block.confluence.timers_block_1_1", "1 Second Timer");
        add("block.confluence.timers_block_3_1", "3 Second Timer");
        add("block.confluence.timers_block_5_1", "5 Second TImer");
        add("block.confluence.timers_block_1_2", "1/2 Second Timer");
        add("block.confluence.timers_block_1_4", "1/4 Second TImer");
        add("block.confluence.base_chest_block.locked_golden", "§rLocked Golden Chest");
        add("block.confluence.base_chest_block.unlocked_golden", "§rGolden Chest");
        add("block.confluence.base_chest_block.death_golden", "§rDeath Golden Chest");
        add("block.confluence.base_chest_block.locked_shadow", "§rLocked Shadow Chest");
        add("block.confluence.base_chest_block.unlocked_shadow", "§rShadow Chest");
        add("block.confluence.base_chest_block.death_shadow", "§rDeath Shadow Chest");
        add("block.confluence.base_chest_block.unlocked_lvy", "§rLvy Chest");
        add("block.confluence.base_chest_block.death_lvy", "§rDeath Lvy Chest");
        add("block.confluence.base_chest_block.unlocked_frozen", "§rFrozen Chest");
        add("block.confluence.base_chest_block.death_frozen", "§rDeath Frozen Chest");
        add("block.confluence.base_chest_block.unlocked_water", "§rWater Chest");
        add("block.confluence.base_chest_block.death_water", "§rDeath Water Chest");
        add("block.confluence.base_chest_block.unlocked_skyware", "§rSkyware Chest");
        add("block.confluence.base_chest_block.death_skyware", "§rDeath Skyware Chest");

        add("resourcepack.terraria_art", "Terraria Art");
        add("resourcepack.mainstream_connected_ores", "Mainstream Connected Ores");
        add("resourcepack.ter_armor", "Mainstream Terraria Armor Textures");

        add("event.confluence.blood_moon", "The Blood Moon is rising...");

        add("attribute.name.generic.critical_chance", "Critical Chance");
        add("attribute.name.generic.ranged_velocity", "Ranged Velocity");
        add("attribute.name.generic.ranged_damage", "Ranged Damage");
        add("attribute.name.generic.dodge_chance", "Dodge Chance");
        add("attribute.name.generic.mining_speed", "Mining Speed");
        add("attribute.name.generic.aggro", "Aggro");
        add("attribute.name.generic.magic_damage", "Magic Damage");
        add("attribute.name.generic.armor_pass", "Armor Pass");
        add("attribute.name.generic.pickup_range", "Pickup Range");

        add("entity.minecraft.villager.confluence.sky_miller", "Sky Miller");

        add("container.confluence.workshop", "Workshop");
        add("title.confluence.workshop", "Workshop");

        add("generator.confluence.corruption", "The Corruption");
        add("generator.confluence.tr_crimson", "The Crimson");



        ModBlocks.BLOCKS.getEntries().forEach(block -> {
            Block block1 = block.get();
            if (block1 instanceof WallSignBlock || block1 instanceof WallTorchBlock) return;
            if (block1 instanceof CustomName customName) {
                if (customName.getGenName() != null) {
                    add(block1, customName.getGenName());
                }
            } else {
                add(block1, toTitleCase(block.getId().getPath()));
            }
        });
        ModItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (item1 instanceof BlockItem) return;
            if (item1 instanceof VanillaPotionItem) return;
            if (item1 instanceof CustomName customName) {
                if (customName.getGenName() != null) {
                    add(item1, customName.getGenName());
                }
            } else {
                add(item1, toTitleCase(item.getId().getPath()));
            }
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
        for (ModPrefix.Magic magicAndSumming : ModPrefix.Magic.values()) {
            add("prefix.confluence." + magicAndSumming.name().toLowerCase(), toTitleCase(magicAndSumming.name()));
        }
        for (ModPrefix.Curio curio : ModPrefix.Curio.values()) {
            if (curio == ModPrefix.Curio.HASTY || curio == ModPrefix.Curio.QUICK) continue;
            add("prefix.confluence." + curio.name().toLowerCase(), toTitleCase(curio.name()));
        }
    }
}
