package org.confluence.mod.capability.mana;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.HealthAndMana.IAutoGetMana;
import org.confluence.mod.item.curio.HealthAndMana.IManaReduce;
import org.confluence.mod.item.curio.combat.IMagicAttack;
import org.confluence.mod.item.potion.ManaPotion;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@AutoRegisterCapability
public final class ManaStorage implements INBTSerializable<CompoundTag> {
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
                    int amount = manaPotion.getAmount();
                    if (currentMana + amount < extract) continue;
                    if (toUse == null || amount < ((ManaPotion) toUse.getItem()).getAmount()) toUse = itemStack;
                    if (amount == 50) break;
                }
            }
            if (toUse == null) return false;
            toUse.finishUsingItem(serverPlayer.level(), serverPlayer);
        }
        this.currentMana -= extract;
        return true;
    }

    public int getCurrentMana() {
        return currentMana;
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

    public void freshAbility(LivingEntity living) {
        AtomicDouble bonus = new AtomicDouble(1.0);
        AtomicDouble ratio = new AtomicDouble(1.0);
        AtomicBoolean band = new AtomicBoolean();
        AtomicInteger mana = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                Item item = itemStack.getItem();
                if (item instanceof IMagicAttack iMagicAttack) {
                    bonus.addAndGet(iMagicAttack.getMagicBonus());
                }
                if (item instanceof IManaReduce iManaReduce) {
                    ratio.addAndGet(-iManaReduce.getManaReduce());
                }
                if (item == CurioItems.MANA_REGENERATION_BAND.get()) {
                    band.set(true);
                }
                PrefixProvider.getPrefix(itemStack)
                    .ifPresent(itemPrefix -> mana.addAndGet(itemPrefix.additionalMana));
            }
        });
        this.magicAttackBonus = bonus.get();
        this.extractRatio = ratio.get();
        this.manaRegenerationBand = band.get();
        this.additionalMana = mana.get();
    }

    public double getMagicAttackBonus() {
        return magicAttackBonus;
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
