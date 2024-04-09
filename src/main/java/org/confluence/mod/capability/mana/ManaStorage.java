package org.confluence.mod.capability.mana;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.magic.IMagicAttack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

@AutoRegisterCapability
public class ManaStorage implements INBTSerializable<CompoundTag> {
    private int stars;
    private int regenerateBonus;
    private int additionalMana;
    private int currentMana;
    private transient int regenerateDelay;
    private transient Integer maxMana;
    private double magicAttackBonus;
    private float extractRatio;

    public ManaStorage() {
        this.stars = 1;
        this.regenerateBonus = 0;
        this.additionalMana = 0;
        this.currentMana = 20;
        this.regenerateDelay = 0;
        this.magicAttackBonus = 1.0;
        this.extractRatio = 1.0F;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("stars", stars);
        nbt.putInt("regenerateBonus", regenerateBonus);
        nbt.putInt("additionalMana", additionalMana);
        nbt.putInt("currentMana", currentMana);
        nbt.putDouble("magicAttackBonus", magicAttackBonus);
        nbt.putFloat("extractRatio", extractRatio);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.stars = nbt.getInt("stars");
        this.regenerateBonus = nbt.getInt("regenerateBonus");
        this.additionalMana = nbt.getInt("additionalMana");
        this.currentMana = nbt.getInt("currentMana");
        this.magicAttackBonus = nbt.getDouble("magicAttackBonus");
        this.extractRatio = nbt.getFloat("extractRatio");
    }

    public int receiveMana(Supplier<Integer> sup) {
        if (!canReceive()) return -1;
        int received = Math.min(sup.get(), getMaxMana() - currentMana);
        this.currentMana += received;
        return received;
    }

    public int extractMana(Supplier<Integer> sup) {
        if (!canExtract()) return -1;
        int extracted = Math.min((int) (sup.get() * extractRatio), currentMana);
        this.currentMana -= extracted;
        return extracted;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getRegenerateBonus() {
        return regenerateBonus;
    }

    public void setRegenerateBonus(int amount) {
        this.regenerateBonus = amount;
    }

    public int getAdditionalMana() {
        return additionalMana;
    }

    public void setAdditionalMana(int amount) {
        this.additionalMana = amount;
        freshMaxMana();
    }

    public int getRegenerateDelay() {
        return regenerateDelay;
    }

    public void setRegenerateDelay(int regenerateDelay) {
        this.regenerateDelay = regenerateDelay;
    }

    public int getMaxMana() {
        if (maxMana == null) {
            freshMaxMana();
        }
        return maxMana;
    }

    public void freshMaxMana() {
        this.maxMana = stars * 20 + additionalMana;
        if (currentMana > maxMana) {
            this.currentMana = maxMana;
        }
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

    public void freshMagicAttackBonus(LivingEntity living) {
        AtomicDouble atomic = new AtomicDouble(1.0);
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IMagicAttack iMagicAttack) {
                    atomic.addAndGet(iMagicAttack.getBonus());
                }
            }
        });
        this.magicAttackBonus = atomic.get();
    }

    public double getMagicAttackBonus() {
        return magicAttackBonus;
    }

    public void freshExtractRatio(LivingEntity living) {

    }

    public void copyFrom(ManaStorage manaStorage) {
        this.stars = manaStorage.stars;
        this.currentMana = manaStorage.currentMana;
        this.magicAttackBonus = manaStorage.magicAttackBonus;
        this.extractRatio = manaStorage.extractRatio;
    }
}
