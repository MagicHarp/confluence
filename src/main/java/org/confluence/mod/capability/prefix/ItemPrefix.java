package org.confluence.mod.capability.prefix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.INBTSerializable;
import org.confluence.mod.item.ModPrefix;

import java.util.UUID;

public final class ItemPrefix implements INBTSerializable<CompoundTag> {
    public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("");
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("");
    public static final UUID KNOCK_BACK_UUID = UUID.fromString("");
    public static final UUID ENTITY_REACH_UUID = UUID.fromString("");
    public static final UUID ARMOR_UUID = UUID.fromString("");
    public static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("");

    public transient final ItemStack itemStack;
    public PrefixType type;
    public String name;

    public double attackDamage;
    public double attackSpeed;
    public double criticalChance;
    public double knockBack;
    public int tier;
    public double value;

    public double size;
    public double velocity;
    public double manaCost;
    public int armor;
    public int additionalMana;
    public double movementSpeed;

    public ItemPrefix(PrefixType type, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.type = type;
        this.name = "UNKNOWN";

        this.attackDamage = 0.0;
        this.attackSpeed = 0.0;
        this.criticalChance = 0.0;
        this.knockBack = 0.0;
        this.tier = 0;
        this.value = 0.0;

        this.size = 0.0;
        this.velocity = 0.0;
        this.manaCost = 0.0;
        this.armor = 0;
        this.additionalMana = 0;
        this.movementSpeed = 0.0;
    }

    public ItemPrefix(ItemStack itemStack) {
        this(PrefixType.UNKNOWN, itemStack);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("type", type.name());
        nbt.putString("name", name);
        nbt.putDouble("attackDamage", attackDamage);
        if (type != PrefixType.UNIVERSAL) nbt.putDouble("attackSpeed", attackSpeed);
        nbt.putDouble("criticalChance", criticalChance);
        nbt.putDouble("knockBack", knockBack);
        nbt.putInt("tier", tier);
        nbt.putDouble("value", value);
        switch (type) {
            case MELEE -> nbt.putDouble("size", size);
            case RANGED -> nbt.putDouble("velocity", velocity);
            case MAGIC_AND_SUMMING -> nbt.putDouble("manaCost", manaCost);
            case CURIO -> {
                nbt.putInt("armor", armor);
                nbt.putInt("additionalMana", additionalMana);
                nbt.putDouble("movementSpeed", movementSpeed);
            }
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.type = PrefixType.valueOf(nbt.getString("type"));
        this.name = nbt.getString("name");
        this.attackDamage = nbt.getDouble("attackDamage");
        if (type != PrefixType.UNIVERSAL) this.attackSpeed = nbt.getDouble("attackSpeed");
        this.criticalChance = nbt.getDouble("criticalChance");
        this.knockBack = nbt.getDouble("knockBack");
        this.tier = nbt.getInt("tier");
        this.value = nbt.getDouble("value");
        switch (type) {
            case MELEE -> this.size = nbt.getDouble("size");
            case RANGED -> this.velocity = nbt.getDouble("velocity");
            case MAGIC_AND_SUMMING -> this.manaCost = nbt.getDouble("manaCost");
            case CURIO -> {
                this.armor = nbt.getInt("armor");
                this.additionalMana = nbt.getInt("additionalMana");
                this.movementSpeed = nbt.getDouble("movementSpeed");
            }
        }
    }

    public void copyFrom(ModPrefix modPrefix) {
        modPrefix.copyTo(this);
        itemStack.getOrCreateTag().put(PrefixProvider.KEY, serializeNBT());
    }

    public void applyPrefix(LivingEntity living) {
        AttributeMap attributeMap = living.getAttributes();
        addModifier(attributeMap, attackDamage, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID);
        addModifier(attributeMap, attackSpeed, Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID);
        addModifier(attributeMap, knockBack, Attributes.ATTACK_KNOCKBACK, KNOCK_BACK_UUID);
        addModifier(attributeMap, size, ForgeMod.ENTITY_REACH.get(), ENTITY_REACH_UUID);
        addModifier(attributeMap, armor, Attributes.ARMOR, ARMOR_UUID);
        addModifier(attributeMap, movementSpeed, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_UUID);
    }

    private static void addModifier(AttributeMap attributeMap, double pValue, Attribute attribute, UUID uuid) {
        if (pValue <= 0.0) return;
        AttributeInstance instance = attributeMap.getInstance(attribute);
        if (instance == null) return;
        AttributeModifier modifier = instance.getModifier(uuid);
        if (modifier == null) {
            instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", pValue, AttributeModifier.Operation.MULTIPLY_TOTAL));
        } else {
            double amount = modifier.getAmount();
            instance.removeModifier(uuid);
            instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", amount + pValue, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    public void expirePrefix(LivingEntity living) {
        AttributeMap attributeMap = living.getAttributes();
        removeModifier(attributeMap, attackDamage, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID);
        removeModifier(attributeMap, attackSpeed, Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID);
        removeModifier(attributeMap, knockBack, Attributes.ATTACK_KNOCKBACK, KNOCK_BACK_UUID);
        removeModifier(attributeMap, size, ForgeMod.ENTITY_REACH.get(), ENTITY_REACH_UUID);
        removeModifier(attributeMap, armor, Attributes.ARMOR, ARMOR_UUID);
        removeModifier(attributeMap, movementSpeed, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_UUID);
    }

    private static void removeModifier(AttributeMap attributeMap, double pValue, Attribute attribute, UUID uuid) {
        if (pValue <= 0.0) return;
        AttributeInstance instance = attributeMap.getInstance(attribute);
        if (instance == null) return;
        AttributeModifier modifier = instance.getModifier(uuid);
        if (modifier == null) return;
        double amount = modifier.getAmount();
        instance.removeModifier(uuid);
        if (amount - pValue <= 0.0) return;
        instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", amount - pValue, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
