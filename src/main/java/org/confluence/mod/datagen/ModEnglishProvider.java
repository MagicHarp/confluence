package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
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

        add("item.confluence.aglet.tooltip", "5% increased movement speed");
        add("item.confluence.anklet_of_the_wind.tooltip", "10% increased movement speed");
        add("item.confluence.mechanical_lens.tooltip", "Grants improved wire vision");
        add("item.confluence.spectre_goggles.tooltip", "Enables Echo Sight, showing hidden blocks");
        add("item.confluence.cloud_in_a_bottle.tooltip", "Allows the holder to double jump");
        add("item.confluence.band_of_regeneration.tooltip", "Slowly regenerates life");
        add("item.confluence.band_of_starpower.tooltip", "Increases maximum mana by 20");
        add("item.confluence.extendo_grip.tooltip", "Increases block placement & tool range by 3");
        add("item.confluence.hermes_boots.tooltip", "The wearer can run super fast");
        add("item.confluence.flurry_boots.tooltip", "The wearer can run super fast");
        add("item.confluence.sailfish_boots.tooltip", "The wearer can run super fast");
        add("item.confluence.dunerider_boots.tooltip", "The wearer can run super fast, and even faster on sand");
        add("item.confluence.dunerider_boots.tooltip2", "'Walk without rhythm and you won't attract the worm'");
        add("item.confluence.spectre_boots.tooltip", "Allows flight");
        add("item.confluence.spectre_boots.tooltip2", "The wearer can run super fast");
        add("item.confluence.shiny_red_balloon.tooltip", "Increases jump height");
        add("item.confluence.cloud_in_a_balloon.tooltip", "Allows the holder to double jump");
        add("item.confluence.cloud_in_a_balloon.tooltip2", "Increases jump height");

        add("death.attack.falling_star", "%1$s was squashed by a falling star");

        ModBlocks.BLOCKS.getEntries().forEach(block -> {
            Block block1 = block.get();
            if (!(block1 instanceof WallSignBlock)) {
                add(block1, toTitleCase(block.getId().getPath()));
            }
        });
        ModItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (!(item1 instanceof BlockItem)) {
                add(item.get(), toTitleCase(item.getId().getPath()));
            }
        });
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
