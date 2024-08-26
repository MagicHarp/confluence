package org.confluence.mod.integration.apothic;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class ApothicHelper {
    private static Boolean isAttributesLoaded;
    public static final String ATTRIBUTES_ID = "attributeslib";
    public static final ResourceLocation CRIT_CHANCE = new ResourceLocation(ATTRIBUTES_ID, "crit_chance");
    public static final ResourceLocation ARROW_VELOCITY = new ResourceLocation(ATTRIBUTES_ID, "arrow_velocity");
    public static final ResourceLocation ARROW_DAMAGE = new ResourceLocation(ATTRIBUTES_ID, "arrow_damage");
    public static final ResourceLocation DODGE_CHANCE = new ResourceLocation(ATTRIBUTES_ID, "dodge_chance");
    public static final ResourceLocation DRAW_SPEED = new ResourceLocation(ATTRIBUTES_ID, "draw_speed");
    public static final ResourceLocation MINING_SPEED = new ResourceLocation(ATTRIBUTES_ID, "mining_speed");

    public static boolean isAttributesLoaded() {
        if (isAttributesLoaded == null) {
            isAttributesLoaded = ModList.get().isLoaded(ATTRIBUTES_ID);
        }
        return isAttributesLoaded;
    }
}
