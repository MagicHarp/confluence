package org.confluence.mod.capability.prefix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.confluence.mod.misc.ModAttributes;

import java.util.UUID;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;

public final class ItemPrefix implements INBTSerializable<CompoundTag> {
    public static final UUID ATTACK_DAMAGE_UUID_CURIO = UUID.fromString("03FE9AA9-9D2C-1999-95DA-328C223B911E");
    public static final UUID ATTACK_SPEED_UUID_CURIO = UUID.fromString("7E9A8538-D9E5-AC65-D2CC-0AFD1DF1D2A0");
    public static final UUID KNOCK_BACK_UUID_CURIO = UUID.fromString("28DAAD9E-4E15-D1DE-C531-CF5F2C381C7E");
    public static final UUID ARMOR_UUID_CURIO = UUID.fromString("4B03B0AE-75DF-6BF9-36CA-DD6827CB53CD");
    public static final UUID MOVEMENT_SPEED_UUID_CURIO = UUID.fromString("C46AC6E8-A62E-0484-1CB3-10FFDF57F440");
    public static final UUID CRIT_CHANCE_UUID_CURIO = UUID.fromString("025045C0-3DDC-38C8-E52E-2C95A4B00A21");

    public static final UUID ATTACK_DAMAGE_UUID_HAND = UUID.fromString("16F1FD11-CF73-2D60-CE6A-3999A3CD69BA");
    public static final UUID ATTACK_SPEED_UUID_HAND = UUID.fromString("015EAD07-C4A4-22BB-619B-080F0E8DDEEA");
    public static final UUID KNOCK_BACK_UUID_HAND = UUID.fromString("736088F9-0CFC-4431-EF7E-979313E0AC22");
    public static final UUID ENTITY_REACH_UUID_HAND = UUID.fromString("E83F26CD-2AE7-77E5-F576-E444BCE62D1D");
    public static final UUID CRIT_CHANCE_UUID_HAND = UUID.fromString("6891D9DD-923C-B7EF-8D69-D014C21D0423");
    public static final UUID RANGED_DAMAGE_UUID_HAND = UUID.fromString("100AEC02-6BC7-307F-270D-238375C318A0");
    public static final UUID RANGED_VELOCITY_UUID_HAND = UUID.fromString("3B43B681-F382-DEC6-9E19-10FAD2350ECA");

    public transient final ItemStack itemStack;
    public PrefixType type;
    public String name;

    public float attackDamage;
    public float attackSpeed;
    public float criticalChance;
    public float knockBack;
    public int tier;
    public float value;

    public float size;
    public float rangedDamage;
    public float velocity;
    public float manaCost;
    public int armor;
    public int additionalMana;
    public float movementSpeed;

    public ItemPrefix(PrefixType type, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.type = type;
        this.name = "unknown";

        this.attackDamage = 0.0F;
        this.attackSpeed = 0.0F;
        this.criticalChance = 0.0F;
        this.knockBack = 0.0F;
        this.tier = 0;
        this.value = 0.0F;

        this.size = 0.0F;
        this.rangedDamage = 0.0F;
        this.velocity = 0.0F;
        this.manaCost = 0.0F;
        this.armor = 0;
        this.additionalMana = 0;
        this.movementSpeed = 0.0F;
    }

    public ItemPrefix(ItemStack itemStack) {
        this(PrefixType.UNKNOWN, itemStack);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("type", type.name());
        nbt.putString("name", name);
        nbt.putFloat("attackDamage", attackDamage);
        if (type != PrefixType.UNIVERSAL) nbt.putFloat("attackSpeed", attackSpeed);
        nbt.putFloat("criticalChance", criticalChance);
        nbt.putFloat("knockBack", knockBack);
        nbt.putInt("tier", tier);
        nbt.putFloat("value", value);
        switch (type) {
            case MELEE -> nbt.putFloat("size", size);
            case RANGED -> {
                nbt.putFloat("rangedDamage", rangedDamage);
                nbt.putFloat("velocity", velocity);
            }
            case MAGIC -> {
                nbt.putFloat("rangedDamage", rangedDamage);
                nbt.putFloat("manaCost", manaCost);
            }
            case CURIO -> {
                nbt.putInt("armor", armor);
                nbt.putInt("additionalMana", additionalMana);
                nbt.putFloat("movementSpeed", movementSpeed);
            }
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.type = PrefixType.valueOf(nbt.getString("type").replace("_AND_SUMMING", "")); //  todo 移除replace
        this.name = nbt.getString("name");
        this.attackDamage = nbt.getFloat("attackDamage");
        if (type != PrefixType.UNIVERSAL) this.attackSpeed = nbt.getFloat("attackSpeed");
        this.criticalChance = nbt.getFloat("criticalChance");
        this.knockBack = nbt.getFloat("knockBack");
        this.tier = nbt.getInt("tier");
        this.value = nbt.getFloat("value");
        switch (type) {
            case MELEE -> this.size = nbt.getFloat("size");
            case RANGED -> {
                this.rangedDamage = nbt.getFloat("rangedDamage");
                this.velocity = nbt.getFloat("velocity");
            }
            case MAGIC -> {
                this.rangedDamage = nbt.getFloat("rangedDamage");
                this.manaCost = nbt.getFloat("manaCost");
            }
            case CURIO -> {
                this.armor = nbt.getInt("armor");
                this.additionalMana = nbt.getInt("additionalMana");
                this.movementSpeed = nbt.getFloat("movementSpeed");
            }
        }
    }

    public void copyFrom(ModPrefix modPrefix) {
        if (type != PrefixType.UNKNOWN) modPrefix.copyTo(this);
        itemStack.getOrCreateTag().put(PrefixProvider.KEY, serializeNBT());
    }

    public void applyCurioPrefix(LivingEntity living) {
        if (living.level().isClientSide) return;
        AttributeMap attributeMap = living.getAttributes();
        addModifier(attributeMap, attackDamage, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID_CURIO, MULTIPLY_TOTAL);
        addModifier(attributeMap, attackSpeed, Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID_CURIO, MULTIPLY_TOTAL);
        addModifier(attributeMap, knockBack, Attributes.ATTACK_KNOCKBACK, KNOCK_BACK_UUID_CURIO, ADDITION);
        addModifier(attributeMap, armor, Attributes.ARMOR, ARMOR_UUID_CURIO, ADDITION);
        addModifier(attributeMap, movementSpeed, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_UUID_CURIO, MULTIPLY_TOTAL);
        addModifier(attributeMap, criticalChance, ModAttributes.getCriticalChance(), CRIT_CHANCE_UUID_CURIO, ADDITION);
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
        removeModifier(attributeMap, attackDamage, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID_CURIO, MULTIPLY_TOTAL);
        removeModifier(attributeMap, attackSpeed, Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID_CURIO, MULTIPLY_TOTAL);
        removeModifier(attributeMap, knockBack, Attributes.ATTACK_KNOCKBACK, KNOCK_BACK_UUID_CURIO, ADDITION);
        removeModifier(attributeMap, armor, Attributes.ARMOR, ARMOR_UUID_CURIO, ADDITION);
        removeModifier(attributeMap, movementSpeed, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_UUID_CURIO, MULTIPLY_TOTAL);
        removeModifier(attributeMap, criticalChance, ModAttributes.getCriticalChance(), CRIT_CHANCE_UUID_CURIO, ADDITION);
    }

    private static void removeModifier(AttributeMap attributeMap, double pValue, Attribute attribute, UUID uuid, AttributeModifier.Operation operation) {
        if (pValue <= 0.0) return;
        AttributeInstance instance = attributeMap.getInstance(attribute);
        if (instance == null) return;
        AttributeModifier modifier = instance.getModifier(uuid);
        if (modifier == null) return;
        double amount = modifier.getAmount();
        instance.removeModifier(uuid);
        if (amount - pValue <= 0.0) return;
        instance.addTransientModifier(new AttributeModifier(uuid, "Item Prefix", amount - pValue, operation));
    }
}
