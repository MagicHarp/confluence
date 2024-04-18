package org.confluence.mod.capability.mana;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.healthandmana.IAutoGetMana;
import org.confluence.mod.item.curio.healthandmana.IManaReduce;
import org.confluence.mod.item.magic.IMagicAttack;
import org.confluence.mod.item.potion.ManaPotion;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

@AutoRegisterCapability
public class ManaStorage implements INBTSerializable<CompoundTag> {
    private int stars;
    private int additionalMana;
    private int currentMana;
    private transient int regenerateDelay;
    private transient Integer maxMana;
    private double magicAttackBonus;
    private double extractRatio;
    private boolean manaRegenerationBand;

    public ManaStorage() {
        this.stars = 1;
        this.additionalMana = 0;
        this.currentMana = 20;
        this.regenerateDelay = 0;
        this.magicAttackBonus = 1.0;
        this.extractRatio = 1.0;
        this.manaRegenerationBand = false;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("stars", stars);
        nbt.putInt("additionalMana", additionalMana);
        nbt.putInt("currentMana", currentMana);
        nbt.putDouble("magicAttackBonus", magicAttackBonus);
        nbt.putDouble("extractRatio", extractRatio);
        nbt.putBoolean("manaRegenerationBand", manaRegenerationBand);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.stars = nbt.getInt("stars");
        this.additionalMana = nbt.getInt("additionalMana");
        this.currentMana = nbt.getInt("currentMana");
        this.magicAttackBonus = nbt.getDouble("magicAttackBonus");
        this.extractRatio = nbt.getDouble("extractRatio");
        this.manaRegenerationBand = nbt.getBoolean("manaRegenerationBand");
    }

    public boolean receiveMana(Supplier<Integer> sup) {
        if (!canReceive()) return false;
        int received = Math.min(sup.get(), getMaxMana() - currentMana);
        this.currentMana += received;
        return true;
    }

    public boolean extractMana(Supplier<Integer> sup, ServerPlayer serverPlayer) {
        if (!canExtract()) return false;
        int extract = (int) (sup.get() * extractRatio);
        if (currentMana < extract) {
            if (CuriosUtils.noSameCurio(serverPlayer, IAutoGetMana.class)) return false;
            ItemStack toUse = null;
            for (ItemStack itemStack : serverPlayer.getInventory().items) {
                if (itemStack.getItem() instanceof ManaPotion manaPotion) {
                    if (currentMana + manaPotion.getAmount() < extract) continue;
                    if (toUse == null) {
                        toUse = itemStack;
                    } else if (manaPotion.getAmount() < ((ManaPotion) toUse.getItem()).getAmount()) {
                        toUse = itemStack;
                    }
                    if (manaPotion.getAmount() == 50) break;
                }
            }
            if (toUse == null) return false;
            else toUse.finishUsingItem(serverPlayer.level(), serverPlayer);
        }
        this.currentMana -= extract;
        return true;
    }

    public int getCurrentMana() {
        return currentMana;
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
        AtomicDouble atomic = new AtomicDouble();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IManaReduce iManaReduce) {
                    atomic.addAndGet(iManaReduce.getManaReduce());
                }
            }
        });
        this.extractRatio = 1.0 - atomic.get();
    }

    public void setManaRegenerationBand(boolean manaRegenerationBand) {
        this.manaRegenerationBand = manaRegenerationBand;
    }

    public boolean hasManaRegenerationBand() {
        return manaRegenerationBand;
    }

    public void copyFrom(ManaStorage manaStorage) {
        this.stars = manaStorage.stars;
        this.additionalMana = manaStorage.additionalMana;
        this.currentMana = manaStorage.currentMana;
        this.magicAttackBonus = manaStorage.magicAttackBonus;
        this.extractRatio = manaStorage.extractRatio;
        this.manaRegenerationBand = manaStorage.manaRegenerationBand;
    }
}
