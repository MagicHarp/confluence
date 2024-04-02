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

        add("curios.tooltip.speed_boots", "The wearer can run super fast");
        add("curios.tooltip.may_fly", "Allows flight");
        add("curios.tooltip.jump_boost", "Increases jump height");
        add("curios.tooltip.multi_jump", "Allows the holder to double jump");
        add("curios.tooltip.fall_resistance", "Increases fall resistance");
        add("curios.tooltip.negates_fall_damage", "Negates fall damage");
        add("item.confluence.honey_comb.tooltip", "Releases bees and douses the user in honey when damaged(WIP)");
        add("item.confluence.bezoar.tooltip", "Immunity to Poison");
        add("item.confluence.blindfold.tooltip", "Immunity to Darkness");
        add("item.confluence.cobalt_shield.tooltip", "Grants immunity to knockback");
        add("item.confluence.band_of_regeneration.tooltip", "Slowly regenerates life");
        add("item.confluence.band_of_starpower.tooltip", "Increases maximum mana by 20");
        add("item.confluence.mechanical_lens.tooltip", "Grants improved wire vision");
        add("item.confluence.spectre_goggles.tooltip", "Enables Echo Sight, showing hidden blocks");
        add("item.confluence.magiluminescence.tooltip", "Increases movement speed and acceleration(WIP)");
        add("item.confluence.magiluminescence.tooltip2", "Provides light when worn(WIP)");
        add("item.confluence.magiluminescence.tooltip3", "'A brief light in my dark life.'");
        add("item.confluence.sandstorm_on_a_bottle.tooltip", "Allows the holder to do an improved double jump");
        add("item.confluence.ice_skates.tooltip", "Provides extra mobility on ice");
        add("item.confluence.ice_skates.tooltip2", "Ice will not break when you fall on it");
        add("item.confluence.dunerider_boots.tooltip", "The wearer can run super fast, and even faster on sand");
        add("item.confluence.dunerider_boots.tooltip2", "'Walk without rhythm and you won't attract the worm'");
        add("item.confluence.lucky_horseshoe.tooltip", "'Said to bring good fortune and keep evil spirits at bay'");
        add("item.confluence.lightning_boots.attribute", "8% increased movement speed");
        add("item.confluence.lightning_boots.tooltip", "Allows flight, super fast running");
        add("item.confluence.horseshoe_balloon.tooltip", "Increases jump height and negates fall damage");

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
