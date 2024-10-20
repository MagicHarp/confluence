package org.confluence.mod.common.init;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.AABB;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.integration.apothic.ApothicHelper;
import org.confluence.mod.mixin.accessor.RangedAttributeAccessor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.BiConsumer;

// todo
public final class ModAttributes {
    private static final Hashtable<Holder<Attribute>, Holder<Attribute>> MAP = new Hashtable<>();

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Confluence.MODID);

    public static final DeferredHolder<Attribute, Attribute> CRIT_CHANCE = ATTRIBUTES.register("crit_chance", () -> new RangedAttribute("attribute.name.generic.critical_chance", 0.0, 0.0, 10.0).setSyncable(true)); // ADDITION
    public static final DeferredHolder<Attribute, Attribute> RANGED_VELOCITY = ATTRIBUTES.register("ranged_velocity", () -> new RangedAttribute("attribute.name.generic.ranged_velocity", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final DeferredHolder<Attribute, Attribute> RANGED_DAMAGE = ATTRIBUTES.register("ranged_damage", () -> new RangedAttribute("attribute.name.generic.ranged_damage", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final DeferredHolder<Attribute, Attribute> DODGE_CHANCE = ATTRIBUTES.register("dodge_chance", () -> new RangedAttribute("attribute.name.generic.dodge_chance", 0.0, 0.0, 1.0).setSyncable(true)); // ADDITION
    public static final DeferredHolder<Attribute, Attribute> AGGRO = ATTRIBUTES.register("aggro", () -> new RangedAttribute("attribute.name.generic.aggro", 0.0, -10000.0, 10000.0).setSyncable(true)); // ADDITION
    public static final DeferredHolder<Attribute, Attribute> MAGIC_DAMAGE = ATTRIBUTES.register("magic_damage", () -> new RangedAttribute("attribute.name.generic.magic_damage", 1.0, 0.0, 10.0).setSyncable(true)); // MULTIPLY_TOTAL
    public static final DeferredHolder<Attribute, Attribute> ARMOR_PASS = ATTRIBUTES.register("armor_pass", () -> new RangedAttribute("attribute.name.generic.armor_pass", 0.0, 0.0, 10000).setSyncable(true)); // ADDITION
    public static final DeferredHolder<Attribute, Attribute> PICKUP_RANGE = ATTRIBUTES.register("pickup_range", () -> new RangedAttribute("attribute.name.generic.pickup_range", 0.0, 0.0, 64.0).setSyncable(true)); // ADDITION

    public static Holder<Attribute> getCriticalChance() {
        return getCustomAttribute(CRIT_CHANCE);
    }

    public static Holder<Attribute> getRangedVelocity() {
        return getCustomAttribute(RANGED_VELOCITY);
    }

    public static Holder<Attribute> getRangedDamage() {
        return getCustomAttribute(RANGED_DAMAGE);
    }

    public static Holder<Attribute> getDodgeChance() {
        return getCustomAttribute(DODGE_CHANCE);
    }

    public static Holder<Attribute> getAggro() {
        return getCustomAttribute(AGGRO);
    }

    public static Holder<Attribute> getMagicDamage() {
        return getCustomAttribute(MAGIC_DAMAGE);
    }

    public static Holder<Attribute> getArmorPass() {
        return getCustomAttribute(ARMOR_PASS);
    }

    public static Holder<Attribute> getPickupRange() {
        return getCustomAttribute(PICKUP_RANGE);
    }

    public static Holder<Attribute> getCustomAttribute(Holder<Attribute> attribute) {
        Holder<Attribute> target = MAP.get(attribute);
        if (target == null) return attribute;
        return target;
    }

    public static boolean hasCustomAttribute(Holder<Attribute> attribute) {
        return MAP.get(attribute) != null;
    }

    public static void registerAttribute(Holder<Attribute> attribute, BiConsumer<EntityType<? extends LivingEntity>, Holder<Attribute>> consumer) {
        if (!hasCustomAttribute(attribute)) consumer.accept(EntityType.PLAYER, attribute);
    }

    public static void applyToArrow(LivingEntity living, AbstractArrow abstractArrow) {
        AttributeInstance attributeInstance = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (attributeInstance != null) { // todo AbstractArrow.doKnockback
//            abstractArrow.setKnockback((int) Math.ceil(abstractArrow.getKnockback() * (1.0 + attributeInstance.getValue())));
        }

        if (!ModAttributes.hasCustomAttribute(RANGED_VELOCITY)) {
            attributeInstance = living.getAttribute(RANGED_VELOCITY);
            if (attributeInstance != null) {
                abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().scale(attributeInstance.getValue()));
            }
        }
        if (!abstractArrow.isCritArrow() && !ModAttributes.hasCustomAttribute(CRIT_CHANCE)) {
            attributeInstance = living.getAttribute(CRIT_CHANCE);
            if (attributeInstance != null) {
                abstractArrow.setCritArrow(living.getRandom().nextFloat() < attributeInstance.getValue());
            }
        }
    }

    public static boolean applyDodge(LivingEntity living) {
        if (hasCustomAttribute(DODGE_CHANCE)) return false;
        AttributeInstance attributeInstance = living.getAttribute(DODGE_CHANCE);
        if (attributeInstance == null) return false;
        return living.level().random.nextFloat() < attributeInstance.getValue();
    }

    public static float applyRangedDamage(LivingEntity living, DamageSource damageSource, float amount) {
        if (hasCustomAttribute(RANGED_DAMAGE)) return amount;
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) return amount;
        AttributeInstance attributeInstance = living.getAttribute(RANGED_DAMAGE);
        if (attributeInstance == null) return amount;
        return amount * (float) attributeInstance.getValue();
    }

    public static float applyMagicDamage(DamageSource damageSource, float amount) {
        if (hasCustomAttribute(MAGIC_DAMAGE)) return amount;
        if (damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC)) {
            if (damageSource.getEntity() instanceof LivingEntity living) {
                AttributeInstance attributeInstance = living.getAttribute(MAGIC_DAMAGE);
                if (attributeInstance == null) return amount;
                return amount * (float) attributeInstance.getValue();
            }
        }
        return amount;
    }

    public static void applyPickupRange(LivingEntity living) {
        if (hasCustomAttribute(PICKUP_RANGE)) return;
        AttributeInstance attributeInstance = living.getAttribute(PICKUP_RANGE);
        if (attributeInstance == null) return;
        double range = attributeInstance.getValue();
        if (range <= 0.0) return;
        living.level().getEntitiesOfClass(
                ItemEntity.class,
                new AABB(living.getOnPos()).inflate(range),
                itemEntity -> true
        ).forEach(itemEntity -> {
            if (itemEntity.isRemoved()) return;
            itemEntity.addDeltaMovement(living.position().subtract(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ()).normalize().scale(0.05F).add(0, 0.04F, 0));
            itemEntity.move(MoverType.SELF, itemEntity.getDeltaMovement());
        });
    }

    public static void readJsonConfig() {
        Map<String, Holder<Attribute>> available = Map.of(
                "crit_chance", CRIT_CHANCE,
                "ranged_velocity", RANGED_VELOCITY,
                "ranged_damage", RANGED_DAMAGE,
                "dodge_chance", DODGE_CHANCE,
                "aggro", AGGRO,
                "magic_damage", MAGIC_DAMAGE,
                "armor_pass", ARMOR_PASS,
                "pickup_range", PICKUP_RANGE
        );

        ApothicHelper.preset(MAP);

        Path path = Confluence.CONFIG_PATH.resolve("attributes.json");
        if (Files.notExists(path)) return;
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(JsonParser.parseReader(new InputStreamReader(inputStream)), "replaceable");
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                Holder<Attribute> raw = available.get(entry.getKey());
                if (raw == null) continue;
                ResourceLocation resourceLocation = ResourceLocation.parse(entry.getValue().getAsString());
                if (BuiltInRegistries.ATTRIBUTE.containsKey(resourceLocation)) {
                    MAP.put(raw, BuiltInRegistries.ATTRIBUTE.getHolder(resourceLocation).get());
                }
            }
        } catch (Exception ignored) {}
    }

    public static void modifyAttributesUpperLimit() {
        if (!ModList.get().isLoaded("attributefix")) {
            if (Attributes.ARMOR instanceof RangedAttribute rangedAttribute) {
                ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0);
            }
            if (Attributes.ARMOR_TOUGHNESS instanceof RangedAttribute rangedAttribute) {
                ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0);
            }
            if (Attributes.MAX_HEALTH instanceof RangedAttribute rangedAttribute) {
                ((RangedAttributeAccessor) rangedAttribute).setMaxValue(2147483647);
            }
        }
    }
}
