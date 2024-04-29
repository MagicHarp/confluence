package org.confluence.mod.capability.prefix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.confluence.mod.item.ModPrefix;

import java.util.UUID;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;

public final class ItemPrefix implements INBTSerializable<CompoundTag> {
    public static final UUID ATTACK_DAMAGE_UUID_CURIO = UUID.fromString("03FE9AA9-9D2C-1999-95DA-328C223B911E");
    public static final UUID ATTACK_SPEED_UUID_CURIO = UUID.fromString("7E9A8538-D9E5-AC65-D2CC-0AFD1DF1D2A0");
    public static final UUID KNOCK_BACK_UUID_CURIO = UUID.fromString("28DAAD9E-4E15-D1DE-C531-CF5F2C381C7E");
    public static final UUID ARMOR_UUID_CURIO = UUID.fromString("4B03B0AE-75DF-6BF9-36CA-DD6827CB53CD");
    public static final UUID MOVEMENT_SPEED_UUID_CURIO = UUID.fromString("C46AC6E8-A62E-0484-1CB3-10FFDF57F440");

    public static final UUID ATTACK_DAMAGE_UUID_HAND = UUID.fromString("16F1FD11-CF73-2D60-CE6A-3999A3CD69BA");
    public static final UUID ATTACK_SPEED_UUID_HAND = UUID.fromString("015EAD07-C4A4-22BB-619B-080F0E8DDEEA");
    public static final UUID KNOCK_BACK_UUID_HAND = UUID.fromString("736088F9-0CFC-4431-EF7E-979313E0AC22");
    public static final UUID ENTITY_REACH_UUID_HAND = UUID.fromString("E83F26CD-2AE7-77E5-F576-E444BCE62D1D");

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
        this.name = "unknown";

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

    public void applyCurioPrefix(LivingEntity living) {
        if (living.level().isClientSide) return;
        AttributeMap attributeMap = living.getAttributes();
        addModifier(attributeMap, attackDamage, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID_CURIO, MULTIPLY_TOTAL);
        addModifier(attributeMap, attackSpeed, Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID_CURIO, MULTIPLY_TOTAL);
        addModifier(attributeMap, knockBack, Attributes.ATTACK_KNOCKBACK, KNOCK_BACK_UUID_CURIO, MULTIPLY_TOTAL);
        addModifier(attributeMap, armor, Attributes.ARMOR, ARMOR_UUID_CURIO, ADDITION);
        addModifier(attributeMap, movementSpeed, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_UUID_CURIO, MULTIPLY_TOTAL);
    }

    private static void addModifier(AttributeMap attributeMap, double pValue, Attribute attribute, UUID uuid, AttributeModifier.Operation operation) {
        if (pValue <= 0.0) return;
        AttributeInstance instance = attributeMap.getInstance(attribute);
        if (instance == null) return;
        AttributeModifier modifier = instance.getModifier(uuid);
        if (modifier == null) {
            instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", pValue, operation));
        } else {
            double amount = modifier.getAmount();
            instance.removeModifier(uuid);
            instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", amount + pValue, operation));
        }
    }

    public void expireCurioPrefix(LivingEntity living) {
        if (living.level().isClientSide) return;
        AttributeMap attributeMap = living.getAttributes();
        removeModifier(attributeMap, attackDamage, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID_CURIO);
        removeModifier(attributeMap, attackSpeed, Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID_CURIO);
        removeModifier(attributeMap, knockBack, Attributes.ATTACK_KNOCKBACK, KNOCK_BACK_UUID_CURIO);
        removeModifier(attributeMap, armor, Attributes.ARMOR, ARMOR_UUID_CURIO);
        removeModifier(attributeMap, movementSpeed, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_UUID_CURIO);
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
        instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", amount - pValue, MULTIPLY_TOTAL));
    }
}
