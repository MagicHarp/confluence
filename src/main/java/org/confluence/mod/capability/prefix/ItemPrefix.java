package org.confluence.mod.capability.prefix;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.INBTSerializable;
import org.confluence.mod.item.ModPrefix;

public final class ItemPrefix implements INBTSerializable<CompoundTag> {
    public PrefixType type;
    public String name;

    public double attackDamage;
    public double attackSpeed;
    public double criticalChance;
    public double knockBack;
    public int tier;
    public double value;

    public double size;
    public double projectileSpeed;
    public double manaCost;
    public int armor;
    public int additionalMana;
    public double movementSpeed;

    public ItemPrefix(PrefixType type) {
        this.type = type;
        this.name = "UNKNOWN";

        this.attackDamage = 0.0;
        this.attackSpeed = 0.0;
        this.criticalChance = 0.0;
        this.knockBack = 0.0;
        this.tier = 0;
        this.value = 0.0;

        this.size = 0.0;
        this.projectileSpeed = 0.0;
        this.manaCost = 0.0;
        this.armor = 0;
        this.additionalMana = 0;
        this.movementSpeed = 0.0;
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
            case RANGED -> nbt.putDouble("projectileSpeed", projectileSpeed);
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
            case RANGED -> this.projectileSpeed = nbt.getDouble("projectileSpeed");
            case MAGIC_AND_SUMMING -> this.manaCost = nbt.getDouble("manaCost");
            case CURIO -> {
                this.armor = nbt.getInt("armor");
                this.additionalMana = nbt.getInt("additionalMana");
                this.movementSpeed = nbt.getDouble("movementSpeed");
            }
        }
    }

    public Multimap<Attribute, AttributeModifier> getModifiers() {
        return ImmutableMultimap.of();
    }

    public void copyFrom(ModPrefix modPrefix) {
        modPrefix.copyTo(this);
    }

    public void random(RandomSource random) {
        Enum<? extends ModPrefix>[] values = type.available[random.nextInt(type.available.length)];
        copyFrom((ModPrefix) values[random.nextInt(values.length)]);
    }
}
