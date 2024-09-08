package org.confluence.mod.advancement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.confluence.mod.Confluence;

import java.util.Hashtable;

public final class ModAchievements {
    public static final Hashtable<ResourceLocation, Vec2> DISPLAY_OFFSET = new Hashtable<>();

    public static void initialize() {
        // Collector
        offset("timber", 0, 0);
        /* Benched */
        offset("hammer_time", 2,0);
        offset("heavy_metal", 3, 0);
        offset("star_power", 4, 0);
        offset("hold_on_tight", 0, 1);
        /* Miner for Fire (1, 1) */
        /* Head in the Clouds (2, 1) */
        /* Like a Boss (3, 1) */
        /* Drax Attax (4, 1) */
        /* Temple Raider (0, 2) */
        /* Fashion Statement (1, 2) */
        /* Sword of the Hero (2, 2) */
        /* Dye Hard (3, 2) */
        /* Sick Throw (4, 2) */
        /* The Cavalry (0, 3) */
        /* Completely Awesome (1, 3) */
        /* Prismancer (2, 3) */
        /* Glorious Golden Pole (3, 3) */
        /* Matching Attire (4, 3) */
        /* Infinity +1 Sword (0, 4) */
        offset("boots_of_the_hero", 1, 4);
        /* Feast of Midas (2, 4) */
        offset("black_mirror", 3, 4);
        offset("ankhumulation_complete", 4, 4);

        // Explorer
        /* No Hobo (6, 0) */
        offset("ooo_shinny", 7, 0);
        offset("heart_breaker", 8, 0);
        /* I Am Loot (9, 0) */
        /* Smashing Poppet (10, 0) */
        /* Wheres My Honey (6, 1) */
        // todo 可以按你喜好排序(看到了删掉这行)
    }

    private static void offset(String path, float x, float y) {
        DISPLAY_OFFSET.put(new ResourceLocation(Confluence.MODID, "achievements/" + path), new Vec2(x, y));
    }
}
