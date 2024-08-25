package org.confluence.mod.capability.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.HealthAndMana.IAutoGetMana;
import org.confluence.mod.item.curio.HealthAndMana.IManaReduce;
import org.confluence.mod.item.curio.combat.IMagicAttack;
import org.confluence.mod.item.potion.ManaPotionItem;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

@AutoRegisterCapability
public final class ManaStorage implements INBTSerializable<CompoundTag> {
    private int stars;
    private int additionalMana;
    private int currentMana;
    private transient int regenerateDelay;
    private transient Integer maxMana;
    private float magicAttackBonus;
    private float extractRatio;
    private boolean manaRegenerationBand;

    private boolean arcaneCrystalUsed;

    public ManaStorage() {
        this.stars = 1;
        this.additionalMana = 0;
        this.currentMana = 20;
        this.regenerateDelay = 0;
        this.magicAttackBonus = 1.0F;
        this.extractRatio = 1.0F;
        this.manaRegenerationBand = false;

        this.arcaneCrystalUsed = false;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("stars", stars);
        nbt.putInt("additionalMana", additionalMana);
        nbt.putInt("currentMana", currentMana);
        nbt.putFloat("magicAttackBonus", magicAttackBonus);
        nbt.putFloat("extractRatio", extractRatio);
        nbt.putBoolean("manaRegenerationBand", manaRegenerationBand);
        nbt.putBoolean("arcaneCrystalUsed", arcaneCrystalUsed);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.stars = nbt.getInt("stars");
        this.additionalMana = nbt.getInt("additionalMana");
        this.currentMana = nbt.getInt("currentMana");
        this.magicAttackBonus = nbt.getFloat("magicAttackBonus");
        this.extractRatio = nbt.getFloat("extractRatio");
        this.manaRegenerationBand = nbt.getBoolean("manaRegenerationBand");
        this.arcaneCrystalUsed = nbt.getBoolean("arcaneCrystalUsed");
    }

    public boolean receiveMana(IntSupplier sup) {
        if (!canReceive()) return false;
        int received = Math.min(sup.getAsInt(), getMaxMana() - currentMana);
        this.currentMana += received;
        return true;
    }

    public boolean extractMana(IntSupplier sup, ServerPlayer serverPlayer) {
        if (!canExtract()) return false;
        int extract = (int) (sup.getAsInt() * extractRatio);
        if (currentMana < extract) {
            if (CuriosUtils.noSameCurio(serverPlayer, IAutoGetMana.class)) return false;
            ItemStack toUse = null;
            for (ItemStack itemStack : serverPlayer.getInventory().items) {
                if (itemStack.getItem() instanceof ManaPotionItem manaPotion) {
                    int amount = manaPotion.getAmount();
                    if (currentMana + amount < extract) continue;
                    if (toUse == null || amount < ((ManaPotionItem) toUse.getItem()).getAmount()) toUse = itemStack;
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

    public void flushAbility(LivingEntity living) {
        MutableFloat bonus = new MutableFloat(1.0);
        MutableFloat ratio = new MutableFloat(1.0);
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
        this.magicAttackBonus = bonus.getValue();
        this.extractRatio = ratio.getValue();
        this.manaRegenerationBand = band.get();
        this.additionalMana = mana.get();
    }

    public float getMagicAttackBonus() {
        return magicAttackBonus;
    }

    public boolean hasManaRegenerationBand() {
        return manaRegenerationBand;
    }

    public void setArcaneCrystalUsed() {
        this.arcaneCrystalUsed = true;
    }

    public boolean isArcaneCrystalUsed() {
        return arcaneCrystalUsed;
    }

    public void copyFrom(ManaStorage manaStorage) {
        this.stars = manaStorage.stars;
        this.additionalMana = manaStorage.additionalMana;
        this.currentMana = manaStorage.currentMana;
        this.magicAttackBonus = manaStorage.magicAttackBonus;
        this.extractRatio = manaStorage.extractRatio;
        this.manaRegenerationBand = manaStorage.manaRegenerationBand;

        this.arcaneCrystalUsed = manaStorage.arcaneCrystalUsed;
    }
}
