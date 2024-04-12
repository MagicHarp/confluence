package org.confluence.mod.capability.ability;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerAbility implements INBTSerializable<CompoundTag> {
    private double jumpBoost;
    private int fallResistance;
    private int invulnerableTime;
    private double criticalChance;

    private boolean fireImmune;
    private boolean lavaHurtReduce;
    private int maxLavaImmuneTicks;
    private transient int remainLavaImmuneTicks;
    private int aggro; // 仇恨
    private float fishingPower;

    private int crystals;

    public PlayerAbility() {
        this.jumpBoost = 1.0;
        this.fallResistance = 0;
        this.invulnerableTime = 20;
        this.criticalChance = 0.0;

        this.fireImmune = false;
        this.lavaHurtReduce = false;
        this.maxLavaImmuneTicks = 0;
        this.remainLavaImmuneTicks = 0;
        this.aggro = 0;
        this.fishingPower = 0.0F;

        this.crystals = 0;
    }

    public void freshJumpBoost(LivingEntity living) {
        AtomicDouble maxBoost = new AtomicDouble(1.0);
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IJumpBoost iJumpBoost) {
                    maxBoost.set(Math.max(iJumpBoost.getBoost(), maxBoost.get()));
                }
            }
        });
        this.jumpBoost = maxBoost.get();
    }

    public void freshFallResistance(LivingEntity living) {
        AtomicInteger fallResistance = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IFallResistance iFallResistance) {
                    int distance = iFallResistance.getFallResistance();
                    if (distance < 0) {
                        fallResistance.set(-1);
                        return;
                    } else {
                        fallResistance.set(Math.max(distance, fallResistance.get()));
                    }
                }
            }
        });
        this.fallResistance = fallResistance.get();
    }

    public void freshInvulnerableTime(LivingEntity living) {
        AtomicInteger atomic = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IInvulnerableTime iInvulnerableTime) {
                    atomic.set(Math.max(iInvulnerableTime.getTime(), invulnerableTime));
                }
            }
        });
        this.invulnerableTime = atomic.get();
    }

    public void freshCriticalChance(LivingEntity living) {
        AtomicDouble maxChance = new AtomicDouble();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ICriticalHit iCriticalHit) {
                    maxChance.addAndGet(iCriticalHit.getChance());
                }
            }
        });
        this.criticalChance = maxChance.get();
    }

    public void freshFireImmune(LivingEntity living) {
        AtomicBoolean fireImmune = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IFireImmune) {
                    fireImmune.set(true);
                    return;
                }
            }
        });
        this.fireImmune = fireImmune.get();
    }

    public void freshLavaHurtReduce(LivingEntity living) {
        AtomicBoolean reduce = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ILavaHurtReduce) {
                    reduce.set(true);
                    return;
                }
            }
        });
        this.lavaHurtReduce = reduce.get();
    }

    public void freshLavaImmuneTicks(LivingEntity living) {
        AtomicInteger lavaImmuneTicks = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ILavaImmune iLavaImmune) {
                    lavaImmuneTicks.set(Math.max(iLavaImmune.getLavaImmuneTicks(), lavaImmuneTicks.get()));
                }
            }
        });
        this.maxLavaImmuneTicks = lavaImmuneTicks.get();
    }

    public void freshAggro(LivingEntity living) {
        AtomicInteger atomic = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IAggroAttach iAggroAttach) {
                    atomic.addAndGet(iAggroAttach.getAggro());
                }
            }
        });
        this.aggro = atomic.get();
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

    public boolean isLavaHurtReduce() {
        return lavaHurtReduce;
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

    public boolean increaseCrystals() {
        if (crystals < 15) {
            crystals++;
            return true;
        }
        return false;
    }

    public int getCrystals() {
        return crystals;
    }

    public void increaseFishingPower(float fishingPower) {
        this.fishingPower += fishingPower;
    }

    public void decreaseFishingPower(float fishingPower) {
        this.fishingPower -= fishingPower;
    }

    public float getFishingPower() {
        return fishingPower;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("jumpBoost", jumpBoost);
        nbt.putInt("fallResistance", fallResistance);
        nbt.putInt("invulnerableTime", invulnerableTime);
        nbt.putDouble("criticalChance", criticalChance);

        nbt.putBoolean("fireImmune", fireImmune);
        nbt.putBoolean("lavaHurtReduce", lavaHurtReduce);
        nbt.putInt("maxLavaImmuneTicks", maxLavaImmuneTicks);
        nbt.putInt("aggro", aggro);
        nbt.putFloat("fishingPower", fishingPower);

        nbt.putInt("crystals", crystals);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.jumpBoost = nbt.getDouble("jumpBoost");
        this.fallResistance = nbt.getInt("fallResistance");
        this.invulnerableTime = nbt.getInt("invulnerableTime");
        this.criticalChance = nbt.getDouble("criticalChance");

        this.fireImmune = nbt.getBoolean("fireImmune");
        this.lavaHurtReduce = nbt.getBoolean("lavaHurtReduce");
        this.maxLavaImmuneTicks = nbt.getInt("maxLavaImmuneTicks");
        this.aggro = nbt.getInt("aggro");
        this.fishingPower = nbt.getFloat("fishingPower");

        this.crystals = nbt.getInt("crystals");
    }

    public void copyFrom(PlayerAbility playerAbility) {
        this.jumpBoost = playerAbility.jumpBoost;
        this.fallResistance = playerAbility.fallResistance;
        this.invulnerableTime = playerAbility.invulnerableTime;
        this.criticalChance = playerAbility.criticalChance;

        this.fireImmune = playerAbility.fireImmune;
        this.lavaHurtReduce = playerAbility.lavaHurtReduce;
        this.maxLavaImmuneTicks = playerAbility.maxLavaImmuneTicks;
        this.aggro = playerAbility.aggro;
        this.fishingPower = playerAbility.fishingPower;

        this.crystals = playerAbility.crystals;
    }
}
