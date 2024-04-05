package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
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
        add("creativetab.confluence.creatures", "Confluence | Creatures");
        add("creativetab.confluence.tools", "Confluence | Tools");
        add("creativetab.confluence.warriors", "Confluence | Warriors");
        add("creativetab.confluence.shooters", "Confluence | Shooters");
        add("creativetab.confluence.mages", "Confluence | Mages");
        add("creativetab.confluence.summoners", "Confluence | Summoners");
        add("creativetab.confluence.creatives", "Confluence | Creatives");
        add("creativetab.confluence.food_and_potions", "Confluence | Food & Potions");
        add("creativetab.confluence.armors", "Confluence | Armors");
        add("creativetab.confluence.functional", "Confluence | Functional");
        add("creativetab.confluence.curios", "Confluence | Curios");

        add("item.confluence.meteorite_ingot.tooltip", "Warm to the touch");

        add("curios.tooltip.speed_boots", "The wearer can run super fast");
        add("curios.tooltip.may_fly", "Allows flight");
        add("curios.tooltip.jump_boost", "Increases jump height");
        add("curios.tooltip.multi_jump", "Allows the holder to double jump");
        add("curios.tooltip.fall_resistance", "Increases fall resistance");
        add("curios.tooltip.negates_fall_damage", "Negates fall damage");
        add("curios.tooltip.watch", "Tell the time");

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

        add("item.confluence.honey_comb.tooltip", "Releases bees and douses the user in honey when damaged(WIP)");
        add("item.confluence.bezoar.tooltip", "Immunity to Poison");
        add("item.confluence.blindfold.tooltip", "Immunity to Darkness");
        add("item.confluence.cobalt_shield.tooltip", "Grants immunity to knockback");
        add("item.confluence.band_of_regeneration.tooltip", "Slowly regenerates life");
        add("item.confluence.band_of_starpower.tooltip", "Increases maximum mana by 20");
        add("item.confluence.mechanical_lens.tooltip", "Grants improved wire vision");
        add("item.confluence.spectre_goggles.tooltip", "Enables Echo Sight, showing hidden blocks");
        add("item.confluence.magiluminescence.tooltip", "Increases movement speed and acceleration");
        add("item.confluence.magiluminescence.tooltip2", "Provides light when worn(WIP)");
        add("item.confluence.magiluminescence.tooltip3", "'A brief light in my dark life.'");
        add("item.confluence.sandstorm_on_a_bottle.tooltip", "Allows the holder to do an improved double jump");
        add("item.confluence.ice_skates.tooltip", "Provides extra mobility on ice");
        add("item.confluence.ice_skates.tooltip2", "Ice will not break when you fall on it");
        add("item.confluence.dunerider_boots.tooltip", "The wearer can run super fast, and even faster on sand");
        add("item.confluence.dunerider_boots.tooltip2", "'Walk without rhythm and you won't attract the worm'");
        add("item.confluence.lucky_horseshoe.tooltip", "'Said to bring good fortune and keep evil spirits at bay'");
        add("item.confluence.lightning_boots.tooltip", "Allows flight, super fast running");
        add("item.confluence.horseshoe_balloon.tooltip", "Increases jump height and negates fall damage");
        add("item.confluence.obsidian_horseshoe.tooltip", "Eliminate fall damage and immune to fire blocks");
        add("item.confluence.obsidian_water_walking_boots.tooltip", "Provides the ability to walk on water" + "Immune to fire blocks");
        add("item.confluence.lava_waders.tooltip", "Provides the ability to walk on water, honey & lava");
        add("item.confluence.lava_waders.tooltip2","Grants immunity to fire blocks and 7 seconds of immunity to lava");
        add("item.confluence.bundle_of_balloons.tooltip", "Allows the holder to quadruple jump");
        add("item.confluence.water_walking_boots.tooltip", "Provides the ability to walk on water and honey");
        add("item.confluence.molten_charm.tooltip", "Immune to fire blocks and magma for 7 seconds");
        add("item.confluence.magma_skull.tooltip", "Immunity to fire blocks, melee attacks deal fire damage");
        add("item.confluence.lava_charm.tooltip", "Immune to lava for 7 seconds");
        add("item.confluence.frostspark_boots.tooltip", "It can fly and run at high speeds");
        add("item.confluence.frostspark_boots.tooltip2", "Provides additional ice surface mobility and does not shatter when falling on ice");
        add("item.confluence.tally_counter.tooltip", "Displays the number of monster kills");
        add("item.confluence.radar.tooltip", "Detect enemies around you");
        add("item.confluence.compass.tooltip", "Displays the horizontal position");
        add("item.confluence.depth_meter.tooltip", "Display depth");
        add("item.confluence.titan_glove.tooltip", "Added melee knockback and enabled melee weapon autoswing");
        add("item.confluence.sun_stone.tooltip", "Slightly increases attribute values during the day");
        add("item.confluence.moon_stone.tooltip", "Slightly increases stat values at night");
        add("item.confluence.putrid_scent.tooltip", "In multiplayer, enemies are unlikely to target you");
        add("item.confluence.putrid_scent.tooltip2", "5% increased damage and critical strike chance");
        add("item.confluence.power_glove.tooltip", "Increases melee knockback and melee speed by 12%");
        add("item.confluence.power_glove.tooltip2", "Enables autoswing of melee weapons");
        add("item.confluence.panic_necklace.tooltip", "Increases movement speed when taking damage");
        add("item.confluence.paladins_shield.tooltip", "When over 25% health, absorb 25% of the damage dealt to players on the team");
        add("item.confluence.paladins_shield.tooltip2", "Immunity to knockback");
        add("item.confluence.obsidian_skull_rose.tooltip", "Immunity to fire blocks, reduces damage from touch with lava");
        add("item.confluence.molten_skull_rose.tooltip", "Immunity to fire blocks, reduces damage from touch with lava");
        add("item.confluence.molten_skull_rose.tooltip2", "Melee attacks deal fire damage");
        add("item.confluence.obsidian_skull.tooltip", "Immune to fire blocks");
        add("item.confluence.obsidian_shield.tooltip", "Immune to knockback, fire block");
        add("item.confluence.obsidian_rose.tooltip", "Reduces damage from touching lava");
        add("item.confluence.magma_stone.tooltip", "Melee attacks deal fire damage");
        add("item.confluence.mechanical_glove.tooltip", "12% increased melee damage and melee speed");
        add("item.confluence.mechanical_glove.tooltip2", "Added melee knockback and enabled melee weapon autoswing");
        add("item.confluence.hero_shield.tooltip", "When over 25% health, absorb 25% of the damage dealt to players on the team");
        add("item.confluence.hero_shield.tooltip2", "In multiplayer, enemies are more likely to target you");
        add("item.confluence.frozen_shield.tooltip", "When health drops below 50%, place a shell around the owner, reducing damage by 25%");
        add("item.confluence.frozen_shield.tooltip2", "When over 25% health, absorb 25% of the damage dealt to players on the team");
        add("item.confluence.frozen_turtle_shell.tooltip", "When health drops below 50%, place a shell around the owner, reducing damage by 25%");
        add("item.confluence.flesh_knuckles.tooltip", "In multiplayer, enemies are more likely to target you");
        add("item.confluence.fire_gauntlet.tooltip", "Increases melee knockback and causes attacks to inflict fire damage");
        add("item.confluence.fire_gauntlet.tooltip2","Increases melee damage and speed by 10%");
        add("item.confluence.eye_of_the_golem.tooltip","10% increased Critical Strike Chance");
        add("item.confluence.berserkers_glove.tooltip","Added melee knockback and enabled melee weapon autoswing");
        add("item.confluence.berserkers_glove.tooltip2","In multiplayer, enemies are more likely to target you");
        add("item.confluence.destroyer_emblem.tooltip","10% increased damage and 8% increased critical strike chance");
        add("item.confluence.cross_necklace.tooltip","Increases the duration of invincibility after taking damage");
        add("item.confluence.black_belt.tooltip","Has a chance to avoid attacks");


        add("death.attack.falling_star", "%1$s was squashed by a falling star");

        ModBlocks.BLOCKS.getEntries().forEach(block -> {
            Block block1 = block.get();
            if (!(block1 instanceof WallSignBlock)) add(block1, toTitleCase(block.getId().getPath()));
        });
        ModItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (!(item1 instanceof BlockItem)) add(item1, toTitleCase(item.getId().getPath()));
        });
        ModEntities.ENTITIES.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
        ModEffects.MOB_EFFECTS.getEntries().forEach(effect -> add(effect.get(), toTitleCase(effect.getId().getPath())));
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
