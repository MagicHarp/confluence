package org.confluence.mod.common.attachment;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessoriesAbility implements INBTSerializable<CompoundTag> {
//    public static final Codec<AccessoriesAbility> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Codec.DOUBLE.fieldOf("jumpBoost").orElse(1.0).forGetter(AccessoriesAbility::getJumpBoost),
//            Codec.INT.fieldOf("fallResistance").orElse(0).forGetter(AccessoriesAbility::getFallResistance),
//            Codec.INT.fieldOf("invulnerableTime").orElse(10).forGetter(AccessoriesAbility::getInvulnerableTime),
//            Codec.BOOL.fieldOf("fireImmune").orElse(false).forGetter(AccessoriesAbility::isFireImmune),
//            Codec.INT.fieldOf("maxLavaImmuneTicks").orElse(0).forGetter(AccessoriesAbility::getMaxLavaImmuneTicks),
//            Codec.INT.fieldOf("airSupplyBonus").orElse(0).forGetter(AccessoriesAbility::getAirSupplyBonus)
//    ).apply(instance, AccessoriesAbility::new));

    private double jumpBoost;
    private int fallResistance;
    private int invulnerableTime;
    private boolean fireImmune;
    private int maxLavaImmuneTicks;
    private transient int remainLavaImmuneTicks;
    private int airSupplyBonus;

//    public AccessoriesAbility(double jumpBoost, int fallResistance, int invulnerableTime, boolean fireImmune, int maxLavaImmuneTicks, int airSupplyBonus) {
//        this.jumpBoost = jumpBoost;
//        this.fallResistance = fallResistance;
//        this.invulnerableTime = invulnerableTime;
//        this.fireImmune = fireImmune;
//        this.maxLavaImmuneTicks = maxLavaImmuneTicks;
//        this.remainLavaImmuneTicks = 0;
//        this.airSupplyBonus = airSupplyBonus;
//    }

    public AccessoriesAbility() {
        this.jumpBoost = 1.0;
        this.fallResistance = 0;
        this.invulnerableTime = 10;
        this.fireImmune = false;
        this.maxLavaImmuneTicks = 0;
        this.remainLavaImmuneTicks = 0;
        this.airSupplyBonus = 0;
    }

    public double getJumpBoost() {
        return jumpBoost;
    }

    public int getFallResistance() {
        return fallResistance;
    }

    public int getInvulnerableTime() {
        return invulnerableTime;
    }

    public boolean isFireImmune() {
        return fireImmune;
    }

    public int getMaxLavaImmuneTicks() {
        return maxLavaImmuneTicks;
    }

    public int getRemainLavaImmuneTicks() {
        return remainLavaImmuneTicks;
    }

    public int getAirSupplyBonus() {
        return airSupplyBonus;
    }

    public void setAirSupplyBonus(int airSupplyBonus) {
        this.airSupplyBonus = airSupplyBonus;
    }

    public void increaseLavaImmuneTicks() {
        if (remainLavaImmuneTicks < maxLavaImmuneTicks) {
            remainLavaImmuneTicks++;
        }
    }

    public boolean decreaseLavaImmuneTicks() {
        if (remainLavaImmuneTicks > 0) {
            remainLavaImmuneTicks--;
            return true;
        }
        return false;
    }

    // todo
    public void flushAbility(LivingEntity living) {
        AtomicDouble jump = new AtomicDouble(1.0);
        AtomicInteger fall = new AtomicInteger();
        AtomicInteger invul = new AtomicInteger(10);
        AtomicBoolean fire = new AtomicBoolean();
        AtomicInteger lava = new AtomicInteger();
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
//            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
//                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
//                if (itemStack.isEmpty()) continue;
//                Item item = itemStack.getItem();
//                if (item instanceof IJumpBoost iJumpBoost) jump.set(Math.max(iJumpBoost.getBoost(), jump.get()));
//                if (item instanceof IFallResistance iFallResistance && fall.get() != -1) {
//                    int distance = iFallResistance.getFallResistance();
//                    fall.set(distance < 0 ? -1 : Math.max(distance, fall.get()));
//                }
//                if (item instanceof IInvulnerableTime iInvulnerableTime) {
//                    invul.set(Math.max(iInvulnerableTime.getTime(), invulnerableTime));
//                }
//                if (item instanceof IFireImmune) fire.set(true);
//                if (item instanceof ILavaImmune iLavaImmune) {
//                    lava.set(Math.max(iLavaImmune.getLavaImmuneTicks(), lava.get()));
//                }
//                if (item instanceof IFishingPower iFishingPower) fishing.addAndGet(iFishingPower.getFishingBonus());
//                if (item instanceof IRangePickup.Star) star.set(true);
//                if (item instanceof IRangePickup.Coin) coin.set(true);
//            }
//        });
        this.jumpBoost = jump.get();
        this.fallResistance = fall.get();
        this.invulnerableTime = invul.get();
        this.fireImmune = fire.get();
        this.maxLavaImmuneTicks = lava.get();
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("jumpBoost", jumpBoost);
        nbt.putInt("fallResistance", fallResistance);
        nbt.putInt("invulnerableTime", invulnerableTime);
        nbt.putBoolean("fireImmune", fireImmune);
        nbt.putInt("maxLavaImmuneTicks", maxLavaImmuneTicks);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        this.jumpBoost = nbt.getDouble("jumpBoost");
        this.fallResistance = nbt.getInt("fallResistance");
        this.invulnerableTime = nbt.getInt("invulnerableTime");
        this.fireImmune = nbt.getBoolean("fireImmune");
        this.maxLavaImmuneTicks = nbt.getInt("maxLavaImmuneTicks");
    }
}
