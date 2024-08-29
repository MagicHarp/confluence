package org.confluence.mod.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.integration.apothic.ApothicHelper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Map;

public final class ModAttributes {
    private static final Hashtable<Attribute, Attribute> MAP = new Hashtable<>();

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Confluence.MODID);

    public static final RegistryObject<Attribute> CRIT_CHANCE = ATTRIBUTES.register("crit_chance", () -> new RangedAttribute("attribute.name.generic.critical_chance", 0.0, 0.0, 10.0).setSyncable(true)); // ADDITION
    public static final RegistryObject<Attribute> RANGED_VELOCITY = ATTRIBUTES.register("ranged_velocity", () -> new RangedAttribute("attribute.name.generic.ranged_velocity", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final RegistryObject<Attribute> RANGED_DAMAGE = ATTRIBUTES.register("ranged_damage", () -> new RangedAttribute("attribute.name.generic.ranged_damage", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final RegistryObject<Attribute> DODGE_CHANCE = ATTRIBUTES.register("dodge_chance", () -> new RangedAttribute("attribute.name.generic.dodge_chance", 0.0, 0.0, 1.0).setSyncable(true)); // ADDITION
    public static final RegistryObject<Attribute> MINING_SPEED = ATTRIBUTES.register("mining_speed", () -> new RangedAttribute("attribute.name.generic.mining_speed", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL

    public static Attribute getCriticalChance() {
        return getCustomAttribute(CRIT_CHANCE.get());
    }

    public static Attribute getRangedVelocity() {
        return getCustomAttribute(RANGED_VELOCITY.get());
    }

    public static Attribute getRangedDamage() {
        return getCustomAttribute(RANGED_DAMAGE.get());
    }

    public static Attribute getDodgeChance() {
        return getCustomAttribute(DODGE_CHANCE.get());
    }

    public static Attribute getMiningSpeed() {
        return getCustomAttribute(MINING_SPEED.get());
    }

    public static Attribute getCustomAttribute(Attribute attribute) {
        Attribute target = MAP.get(attribute);
        if (target == null) return attribute;
        return target;
    }

    public static boolean hasCustomAttribute(Attribute attribute) {
        return MAP.get(attribute) != null;
    }

    public static void applyToArrow(LivingEntity living, AbstractArrow abstractArrow) {
        AttributeInstance attributeInstance = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (attributeInstance != null) {
            abstractArrow.setKnockback((int) Math.ceil(abstractArrow.getKnockback() * (1.0 + attributeInstance.getValue())));
        }

        if (!ModAttributes.hasCustomAttribute(RANGED_VELOCITY.get())) {
            attributeInstance = living.getAttribute(RANGED_VELOCITY.get());
            if (attributeInstance != null) {
                abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().scale(attributeInstance.getValue()));
            }
        }
        if (!abstractArrow.isCritArrow() && !ModAttributes.hasCustomAttribute(CRIT_CHANCE.get())) {
            attributeInstance = living.getAttribute(CRIT_CHANCE.get());
            if (attributeInstance != null) {
                abstractArrow.setCritArrow(living.getRandom().nextFloat() < attributeInstance.getValue());
            }
        }
    }

    public static boolean applyDodge(LivingEntity living) {
        if (hasCustomAttribute(DODGE_CHANCE.get())) return false;
        AttributeInstance attributeInstance = living.getAttribute(DODGE_CHANCE.get());
        if (attributeInstance == null) return false;
        return living.level().random.nextFloat() < attributeInstance.getValue();
    }

    public static float applyRangedDamage(LivingEntity living, DamageSource damageSource, float amount) {
        if (hasCustomAttribute(RANGED_DAMAGE.get())) return amount;
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) return amount;
        AttributeInstance attributeInstance = living.getAttribute(RANGED_DAMAGE.get());
        if (attributeInstance == null) return amount;
        return amount * (float) attributeInstance.getValue();
    }

    public static void readJsonConfig() {
        Map<String, Attribute> available = Map.of(
            "crit_chance", CRIT_CHANCE.get(),
            "ranged_velocity", RANGED_VELOCITY.get(),
            "ranged_damage", RANGED_DAMAGE.get(),
            "dodge_chance", DODGE_CHANCE.get(),
            "mining_speed", MINING_SPEED.get()
        );

        if (ApothicHelper.isAttributesLoaded()) {
            MAP.put(CRIT_CHANCE.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.CRIT_CHANCE));
            MAP.put(RANGED_VELOCITY.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARROW_VELOCITY));
            MAP.put(RANGED_DAMAGE.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARROW_DAMAGE));
            MAP.put(DODGE_CHANCE.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.DODGE_CHANCE));
            MAP.put(MINING_SPEED.get(), ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.MINING_SPEED));
        }

        Path path = Confluence.CONFIG_PATH.resolve("attributes.json");
        if (Files.notExists(path)) return;
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(JsonParser.parseReader(new InputStreamReader(inputStream)), "replaceable");
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                Attribute raw = available.get(entry.getKey());
                if (raw == null) continue;
                ResourceLocation resourceLocation = new ResourceLocation(entry.getValue().getAsString());
                if (ForgeRegistries.ATTRIBUTES.containsKey(resourceLocation)) {
                    MAP.put(raw, ForgeRegistries.ATTRIBUTES.getValue(resourceLocation));
                }
            }
        } catch (Exception ignored) {}
    }
}
