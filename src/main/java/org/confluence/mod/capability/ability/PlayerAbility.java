package org.confluence.mod.capability.ability;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IAggroAttach;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.IInvulnerableTime;
import org.confluence.mod.item.curio.construction.IBreakSpeedBonus;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class PlayerAbility implements INBTSerializable<CompoundTag> {
    private double jumpBoost;
    private int fallResistance;
    private int invulnerableTime;
    private double criticalChance;
    private boolean fireImmune;
    private int maxLavaImmuneTicks;
    private transient int remainLavaImmuneTicks;

    private int aggro;
    private double dropsRange;
    private float breakSpeedBonus;

    public PlayerAbility() {
        this.jumpBoost = 1.0;
        this.fallResistance = 0;
        this.invulnerableTime = 20;
        this.criticalChance = 0.0;
        this.fireImmune = false;
        this.maxLavaImmuneTicks = 0;
        this.remainLavaImmuneTicks = 0;

        this.aggro = 0;
        this.dropsRange = 0.0;
        this.breakSpeedBonus = 0.0F;
    }

    public void freshAbility(LivingEntity living) {
        AtomicDouble jump = new AtomicDouble(1.0);
        AtomicInteger fall = new AtomicInteger();
        AtomicInteger invul = new AtomicInteger(20);
        AtomicDouble chance = new AtomicDouble();
        AtomicBoolean fire = new AtomicBoolean();
        AtomicInteger lava = new AtomicInteger();
        AtomicInteger aggro = new AtomicInteger();
        AtomicBoolean drops = new AtomicBoolean();
        MutableFloat speed = new MutableFloat();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                Item item = itemStack.getItem();
                if (item instanceof IJumpBoost iJumpBoost) jump.set(Math.max(iJumpBoost.getBoost(), jump.get()));
                if (item instanceof IFallResistance iFallResistance && fall.get() != -1) {
                    int distance = iFallResistance.getFallResistance();
                    fall.set(distance < 0 ? -1 : Math.max(distance, fall.get()));
                }
                if (item instanceof IInvulnerableTime iInvulnerableTime) {
                    invul.set(Math.max(iInvulnerableTime.getTime(), invulnerableTime));
                }
                if (item instanceof ICriticalHit iCriticalHit) chance.addAndGet(iCriticalHit.getChance());
                if (item instanceof IFireImmune) fire.set(true);
                if (item instanceof ILavaImmune iLavaImmune) {
                    lava.set(Math.max(iLavaImmune.getLavaImmuneTicks(), lava.get()));
                }
                if (item instanceof IAggroAttach iAggroAttach) aggro.addAndGet(iAggroAttach.getAggro());
                if (item instanceof IRangePickup.Drops) drops.set(true);
                if (item instanceof IBreakSpeedBonus speedBonus) speed.add(speedBonus.getBreakBonus());
            }
        });
        this.jumpBoost = jump.get();
        this.fallResistance = fall.get();
        this.invulnerableTime = invul.get();
        this.criticalChance = chance.get();
        this.fireImmune = fire.get();
        this.maxLavaImmuneTicks = lava.get();
        this.aggro = aggro.get();
        this.dropsRange = drops.get() ? 6.25 : 0.0;
        this.breakSpeedBonus = speed.floatValue();
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

    public double getCriticalChance() {
        return criticalChance;
    }

    public boolean isFireImmune() {
        return fireImmune;
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

    public int getAggro() {
        return aggro;
    }

    public double getDropsRange() {
        return dropsRange;
    }

    public float getBreakSpeedBonus() {
        return breakSpeedBonus;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("jumpBoost", jumpBoost);
        nbt.putInt("fallResistance", fallResistance);
        nbt.putInt("invulnerableTime", invulnerableTime);
        nbt.putDouble("criticalChance", criticalChance);
        nbt.putBoolean("fireImmune", fireImmune);
        nbt.putInt("maxLavaImmuneTicks", maxLavaImmuneTicks);

        nbt.putInt("aggro", aggro);
        nbt.putFloat("breakSpeedBonus", breakSpeedBonus);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.jumpBoost = nbt.getDouble("jumpBoost");
        this.fallResistance = nbt.getInt("fallResistance");
        this.invulnerableTime = nbt.getInt("invulnerableTime");
        this.criticalChance = nbt.getDouble("criticalChance");
        this.fireImmune = nbt.getBoolean("fireImmune");
        this.maxLavaImmuneTicks = nbt.getInt("maxLavaImmuneTicks");

        this.aggro = nbt.getInt("aggro");
        this.breakSpeedBonus = nbt.getFloat("breakSpeedBonus");
    }

    public void copyFrom(PlayerAbility playerAbility) {
        this.jumpBoost = playerAbility.jumpBoost;
        this.fallResistance = playerAbility.fallResistance;
        this.invulnerableTime = playerAbility.invulnerableTime;
        this.criticalChance = playerAbility.criticalChance;
        this.fireImmune = playerAbility.fireImmune;
        this.maxLavaImmuneTicks = playerAbility.maxLavaImmuneTicks;

        this.aggro = playerAbility.aggro;
        this.breakSpeedBonus = playerAbility.breakSpeedBonus;
    }
}
