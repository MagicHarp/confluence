package org.confluence.mod.common.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.confluence.mod.Confluence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntSupplier;

public class ManaStorage implements INBTSerializable<CompoundTag> {
    public static final String KEY = Confluence.MODID + ":mana";
    public static final EntityCapability<ManaStorage, Void> CAP = EntityCapability.createVoid(ResourceLocation.parse(KEY), ManaStorage.class);
    private final Player player;

    private int stars;
    private int additionalMana;
    private int currentMana;
    private transient int regenerateDelay;
    private transient Integer maxMana;
    private float extractRatio;
    private boolean manaRegenerationBand;

    private boolean arcaneCrystalUsed;

    public ManaStorage(Player player) {
        this.player = player;

        if (player.getPersistentData().contains(KEY)) {
            deserializeNBT(player.level().registryAccess(), player.getPersistentData().getCompound(KEY));
        } else {
            this.stars = 1;
            this.additionalMana = 0;
            this.currentMana = 20;
            this.regenerateDelay = 0;
            this.extractRatio = 1.0F;
            this.manaRegenerationBand = false;

            this.arcaneCrystalUsed = false;
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("stars", stars);
        nbt.putInt("additionalMana", additionalMana);
        nbt.putInt("currentMana", currentMana);
        nbt.putFloat("extractRatio", extractRatio);
        nbt.putBoolean("manaRegenerationBand", manaRegenerationBand);
        nbt.putBoolean("arcaneCrystalUsed", arcaneCrystalUsed);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        this.stars = nbt.getInt("stars");
        this.additionalMana = nbt.getInt("additionalMana");
        this.currentMana = nbt.getInt("currentMana");
        this.extractRatio = nbt.getFloat("extractRatio");
        this.manaRegenerationBand = nbt.getBoolean("manaRegenerationBand");
        this.arcaneCrystalUsed = nbt.getBoolean("arcaneCrystalUsed");
    }

    public boolean receiveMana(IntSupplier sup) {
        if (!canReceive()) return false;
        int received = Math.min(sup.getAsInt(), getMaxMana() - currentMana);
        this.currentMana += received;
        serializeNBT(player.level().registryAccess());
        return true;
    }

    public boolean extractMana(IntSupplier sup) {
        if (!canExtract()) return false;
        int extract = (int) (sup.getAsInt() * extractRatio);
//        if (currentMana < extract) {
//            if (CuriosUtils.noSameCurio(serverPlayer, IAutoGetMana.class)) return false;
//            ItemStack toUse = null;
//            for (ItemStack itemStack : serverPlayer.getInventory().items) {
//                if (itemStack.getItem() instanceof ManaPotionItem manaPotion) {
//                    int amount = manaPotion.getAmount();
//                    if (currentMana + amount < extract) continue;
//                    if (toUse == null || amount < ((ManaPotionItem) toUse.getItem()).getAmount()) toUse = itemStack;
//                    if (amount == 50) break;
//                }
//            }
//            if (toUse == null) return false;
//            toUse.finishUsingItem(serverPlayer.level(), serverPlayer);
//        }
        this.currentMana -= extract;
        serializeNBT(player.level().registryAccess());
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

//    public void flushAbility(LivingEntity living) {
//        MutableFloat ratio = new MutableFloat(1.0);
//        AtomicBoolean band = new AtomicBoolean();
//        AtomicInteger mana = new AtomicInteger();
//        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
//            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
//            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
//                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
//                Item item = itemStack.getItem();
//                if (item instanceof IManaReduce iManaReduce) {
//                    ratio.addAndGet(-iManaReduce.getManaReduce());
//                }
//                if (item == CurioItems.MANA_REGENERATION_BAND.get()) {
//                    band.set(true);
//                }
//                PrefixProvider.getPrefix(itemStack)
//                        .ifPresent(itemPrefix -> mana.addAndGet(itemPrefix.additionalMana));
//            }
//        });
//        this.extractRatio = ratio.getValue();
//        this.manaRegenerationBand = band.get();
//        this.additionalMana = mana.get();
//    }

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
        this.extractRatio = manaStorage.extractRatio;
        this.manaRegenerationBand = manaStorage.manaRegenerationBand;

        this.arcaneCrystalUsed = manaStorage.arcaneCrystalUsed;
    }

    public static class Provider implements ICapabilityProvider<Player, Void, ManaStorage> {
        private ManaStorage manaStorage;

        @Override
        public @Nullable ManaStorage getCapability(@NotNull Player player, Void context) {
            return getOrCreateCapability(player);
        }

        private ManaStorage getOrCreateCapability(@NotNull Player player) {
            if (manaStorage == null) {
                this.manaStorage = new ManaStorage(player);
            }
            return manaStorage;
        }
    }
}
