package org.confluence.mod.integration.apothic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.misc.ModAttributes;

import java.util.Hashtable;

public class ApothicHelper {
    private static Boolean isAttributesLoaded;
    public static final String ATTRIBUTES_ID = "attributeslib";
    public static final ResourceLocation CRIT_CHANCE = new ResourceLocation(ATTRIBUTES_ID, "crit_chance");
    public static final ResourceLocation ARROW_VELOCITY = new ResourceLocation(ATTRIBUTES_ID, "arrow_velocity");
    public static final ResourceLocation ARROW_DAMAGE = new ResourceLocation(ATTRIBUTES_ID, "arrow_damage");
    public static final ResourceLocation DODGE_CHANCE = new ResourceLocation(ATTRIBUTES_ID, "dodge_chance");
    public static final ResourceLocation MINING_SPEED = new ResourceLocation(ATTRIBUTES_ID, "mining_speed");
    public static final ResourceLocation ARMOR_PIERCE = new ResourceLocation(ATTRIBUTES_ID, "armor_pierce");

    public static boolean isAttributesLoaded() {
        if (isAttributesLoaded == null) {
            isAttributesLoaded = ModList.get().isLoaded(ATTRIBUTES_ID);
        }
        return isAttributesLoaded;
    }

    public static void preset(Hashtable<Attribute, Attribute> map) {
        if (isAttributesLoaded()) {
            map.put(ModAttributes.CRIT_CHANCE.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.CRIT_CHANCE));
            map.put(ModAttributes.RANGED_VELOCITY.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARROW_VELOCITY));
            map.put(ModAttributes.RANGED_DAMAGE.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARROW_DAMAGE));
            map.put(ModAttributes.DODGE_CHANCE.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.DODGE_CHANCE));
            map.put(ModAttributes.MINING_SPEED.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.MINING_SPEED));
            map.put(ModAttributes.ARMOR_PASS.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARMOR_PIERCE));
        }
    }
}
