package org.confluence.mod.integration.apothic;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.fml.ModList;
import org.confluence.mod.common.init.ModAttributes;

import java.util.Hashtable;

public class ApothicHelper {
    private static Boolean isAttributesLoaded;
    public static final String ATTRIBUTES_ID = "attributeslib";
    public static final ResourceLocation CRIT_CHANCE = ResourceLocation.fromNamespaceAndPath(ATTRIBUTES_ID, "crit_chance");
    public static final ResourceLocation ARROW_VELOCITY = ResourceLocation.fromNamespaceAndPath(ATTRIBUTES_ID, "arrow_velocity");
    public static final ResourceLocation ARROW_DAMAGE = ResourceLocation.fromNamespaceAndPath(ATTRIBUTES_ID, "arrow_damage");
    public static final ResourceLocation DODGE_CHANCE = ResourceLocation.fromNamespaceAndPath(ATTRIBUTES_ID, "dodge_chance");
    public static final ResourceLocation ARMOR_PIERCE = ResourceLocation.fromNamespaceAndPath(ATTRIBUTES_ID, "armor_pierce");

    public static boolean isAttributesLoaded() {
        if (isAttributesLoaded == null) {
            isAttributesLoaded = ModList.get().isLoaded(ATTRIBUTES_ID);
        }
        return isAttributesLoaded;
    }

    public static void preset(Hashtable<Holder<Attribute>, Holder<Attribute>> map) {
        if (isAttributesLoaded()) {
            map.put(ModAttributes.CRIT_CHANCE, BuiltInRegistries.ATTRIBUTE.getHolder(ApothicHelper.CRIT_CHANCE).get());
            map.put(ModAttributes.RANGED_VELOCITY, BuiltInRegistries.ATTRIBUTE.getHolder(ApothicHelper.ARROW_VELOCITY).get());
            map.put(ModAttributes.RANGED_DAMAGE, BuiltInRegistries.ATTRIBUTE.getHolder(ApothicHelper.ARROW_DAMAGE).get());
            map.put(ModAttributes.DODGE_CHANCE, BuiltInRegistries.ATTRIBUTE.getHolder(ApothicHelper.DODGE_CHANCE).get());
            map.put(ModAttributes.ARMOR_PASS, BuiltInRegistries.ATTRIBUTE.getHolder(ApothicHelper.ARMOR_PIERCE).get());
        }
    }
}
