package org.confluence.mod.misc;

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
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Confluence.MODID);

    public static final RegistryObject<Attribute> CRIT_CHANCE = ATTRIBUTES.register("crit_chance", () -> new RangedAttribute("attribute.name.generic.critical_chance", 0.0, 0.0, 10.0).setSyncable(true)); // ADDITION
    public static final RegistryObject<Attribute> RANGED_VELOCITY = ATTRIBUTES.register("ranged_velocity", () -> new RangedAttribute("attribute.name.generic.ranged_velocity", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final RegistryObject<Attribute> RANGED_DAMAGE = ATTRIBUTES.register("ranged_damage", () -> new RangedAttribute("attribute.name.generic.ranged_damage", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final RegistryObject<Attribute> DODGE_CHANCE = ATTRIBUTES.register("dodge_chance", () -> new RangedAttribute("attribute.name.generic.dodge_chance", 0.0, 0.0, 1.0).setSyncable(true)); // ADDITION
    public static final RegistryObject<Attribute> MINING_SPEED = ATTRIBUTES.register("mining_speed", () -> new RangedAttribute("attribute.name.generic.mining_speed", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL

    public static Attribute getCriticalChance() {
        return ApothicHelper.isAttributesLoaded() ? ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.CRIT_CHANCE) : CRIT_CHANCE.get();
    }

    public static Attribute getRangedVelocity() {
        return ApothicHelper.isAttributesLoaded() ? ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARROW_VELOCITY) : RANGED_VELOCITY.get();
    }

    public static Attribute getRangedDamage() {
        return ApothicHelper.isAttributesLoaded() ? ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.ARROW_DAMAGE) : RANGED_DAMAGE.get();
    }

    public static Attribute getDodgeChance() {
        return ApothicHelper.isAttributesLoaded() ? ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.DODGE_CHANCE) : DODGE_CHANCE.get();
    }

    public static Attribute getMiningSpeed() {
        return ApothicHelper.isAttributesLoaded() ? ForgeRegistries.ATTRIBUTES.getValue(ApothicHelper.MINING_SPEED) : MINING_SPEED.get();
    }

    public static void applyToArrow(LivingEntity living, AbstractArrow abstractArrow) {
        AttributeInstance attributeInstance = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (attributeInstance != null) {
            abstractArrow.setKnockback((int) Math.ceil(abstractArrow.getKnockback() * (1.0 + attributeInstance.getValue())));
        }

        if (ApothicHelper.isAttributesLoaded()) return;

        attributeInstance = living.getAttribute(RANGED_DAMAGE.get());
        if (attributeInstance != null) {
            abstractArrow.setBaseDamage(abstractArrow.getBaseDamage() * attributeInstance.getValue());
        }
        attributeInstance = living.getAttribute(RANGED_VELOCITY.get());
        if (attributeInstance != null) {
            abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().scale(attributeInstance.getValue()));
        }
        if (!abstractArrow.isCritArrow()) {
            attributeInstance = living.getAttribute(CRIT_CHANCE.get());
            if (attributeInstance != null) {
                abstractArrow.setCritArrow(living.getRandom().nextFloat() < attributeInstance.getValue());
            }
        }
    }

    public static boolean applyDodge(LivingEntity living) {
        if (ApothicHelper.isAttributesLoaded()) return false; // 使用神化的算法
        AttributeInstance attributeInstance = living.getAttribute(ModAttributes.DODGE_CHANCE.get());
        if (attributeInstance == null) return false;
        return living.level().random.nextFloat() < attributeInstance.getValue();
    }
}
