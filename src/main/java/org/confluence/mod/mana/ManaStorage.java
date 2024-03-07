package org.confluence.mod.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Supplier;

@AutoRegisterCapability
public class ManaStorage implements INBTSerializable<CompoundTag> {
    private int stars;
    private int regenerateBonus;
    private int additionalMana;
    private int currentMana;
    private transient Integer maxMana;

    public ManaStorage() {
        this.stars = 1;
        this.regenerateBonus = 3;
        this.additionalMana = 0;
        this.currentMana = 20;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("stars", stars);
        nbt.putInt("regenerateBonus", regenerateBonus);
        nbt.putInt("additionalMana", additionalMana);
        nbt.putInt("currentMana", currentMana);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.stars = nbt.getInt("stars");
        this.regenerateBonus = nbt.getInt("regenerateBonus");
        this.additionalMana = nbt.getInt("additionalMana");
        this.currentMana = nbt.getInt("currentMana");
    }

    public int receiveMana(Supplier<Integer> sup) {
        if (!canReceive()) return -1;
        int received = Math.min(sup.get(), getMaxMana() - currentMana);
        this.currentMana += received;
        return received;
    }

    public int extractMana(Supplier<Integer> sup) {
        if (!canExtract()) return -1;
        int extracted = Math.min(sup.get(), currentMana);
        this.currentMana -= extracted;
        return extracted;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getRegenerateBonus() {
        return regenerateBonus;
    }

    public void setRegenerateBonus(int regenerateBonus) {
        this.regenerateBonus = regenerateBonus;
    }

    public void setAdditionalMana(int additionalMana) {
        this.additionalMana = additionalMana;
        freshMaxMana();
    }

    public int getMaxMana() {
        if (maxMana == null) {
            freshMaxMana();
        }
        return maxMana;
    }

    public void freshMaxMana() {
        this.maxMana = stars * 20 + additionalMana;
    }

    public boolean canExtract() {
        return currentMana > 0;
    }

    public boolean canReceive() {
        return currentMana < getMaxMana();
    }

    public boolean addStar() {
        if (stars < 10) {
            this.stars++;
            freshMaxMana();
            return true;
        }
        return false;
    }

    public void copyFrom(ManaStorage manaStorage) {
        this.stars = manaStorage.stars;
        this.currentMana = manaStorage.currentMana;
    }
}
