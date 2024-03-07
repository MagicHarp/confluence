package org.confluence.mod.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Supplier;

@AutoRegisterCapability
public class ManaStorage implements INBTSerializable<CompoundTag> {
    private int stars;
    private int currentMana;
    private transient Integer maxMana;

    public ManaStorage() {
        this.stars = 1;
        this.currentMana = 20;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("stars", stars);
        nbt.putInt("currentMana", currentMana);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.stars = nbt.getInt("stars");
        this.currentMana = nbt.getInt("currentMana");
    }

    public int receiveMana(Supplier<Integer> sup) {
        if (!canReceive()) return 0;
        int received = Math.min(sup.get(), getMaxMana() - currentMana);
        this.currentMana += received;
        return received;
    }

    public int extractMana(Supplier<Integer> sup) {
        if (!canExtract()) return 0;
        int extracted = Math.min(sup.get(), currentMana);
        this.currentMana -= extracted;
        return extracted;
    }

    public int getStars() {
        return stars;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        if (maxMana == null) {
            freshMaxMana();
        }
        return maxMana;
    }

    public void freshMaxMana() {
        this.maxMana = stars * 20;
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
            return true;
        }
        return false;
    }

    public void copyFrom(ManaStorage manaStorage) {
        this.stars = manaStorage.stars;
        this.currentMana = manaStorage.currentMana;
    }
}
