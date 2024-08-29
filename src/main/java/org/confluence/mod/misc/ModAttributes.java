package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
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

public final class ModAttributes {
    private static Attribute CRIT_CHANCE_CACHE;
    private static Attribute RANGED_VELOCITY_CACHE;
    private static Attribute RANGED_DAMAGE_CACHE;
    private static Attribute DODGE_CHANCE_CACHE;
    private static Attribute MINING_SPEED_CACHE;

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
        if (attribute == CRIT_CHANCE.get()) {
            if (CRIT_CHANCE_CACHE == null) {
                if (ModConfigs.CRI_CHANCE.getDefault().equals(ModConfigs.CRI_CHANCE.get()) && !ApothicHelper.isAttributesLoaded()) {
                    CRIT_CHANCE_CACHE = attribute;
                } else {
                    CRIT_CHANCE_CACHE = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(ModConfigs.CRI_CHANCE.get()));
                }
            }
            return CRIT_CHANCE_CACHE;
        } else if (attribute == RANGED_VELOCITY.get()) {
            if (RANGED_VELOCITY_CACHE == null) {
                if (ModConfigs.RANGED_VELOCITY.getDefault().equals(ModConfigs.RANGED_VELOCITY.get()) && !ApothicHelper.isAttributesLoaded()) {
                    RANGED_VELOCITY_CACHE = attribute;
                } else {
                    RANGED_VELOCITY_CACHE = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(ModConfigs.RANGED_VELOCITY.get()));
                }
            }
            return RANGED_VELOCITY_CACHE;
        } else if (attribute == RANGED_DAMAGE.get()) {
            if (RANGED_DAMAGE_CACHE == null) {
                if (ModConfigs.RANGED_DAMAGE.getDefault().equals(ModConfigs.RANGED_DAMAGE.get()) && !ApothicHelper.isAttributesLoaded()) {
                    RANGED_DAMAGE_CACHE = attribute;
                } else {
                    RANGED_DAMAGE_CACHE = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(ModConfigs.RANGED_DAMAGE.get()));
                }
            }
            return RANGED_DAMAGE_CACHE;
        } else if (attribute == DODGE_CHANCE.get()) {
            if (DODGE_CHANCE_CACHE == null) {
                if (ModConfigs.DODGE_CHANCE.getDefault().equals(ModConfigs.DODGE_CHANCE.get()) && !ApothicHelper.isAttributesLoaded()) {
                    DODGE_CHANCE_CACHE = attribute;
                } else {
                    DODGE_CHANCE_CACHE = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(ModConfigs.DODGE_CHANCE.get()));
                }
            }
            return DODGE_CHANCE_CACHE;
        } else if (attribute == MINING_SPEED.get()) {
            if (MINING_SPEED_CACHE == null) {
                if (ModConfigs.MINING_SPEED.getDefault().equals(ModConfigs.MINING_SPEED.get()) && !ApothicHelper.isAttributesLoaded()) {
                    MINING_SPEED_CACHE = attribute;
                } else {
                    MINING_SPEED_CACHE = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(ModConfigs.MINING_SPEED.get()));
                }
            }
            return MINING_SPEED_CACHE;
        }
        return attribute;
    }

    public static boolean hasCustomAttribute(Attribute attribute) {
        return getCustomAttribute(attribute) != attribute;
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
}
