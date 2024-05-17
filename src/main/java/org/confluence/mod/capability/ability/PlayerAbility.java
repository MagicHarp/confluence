package org.confluence.mod.capability.ability;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.IRangePickup;
import org.confluence.mod.item.curio.combat.IAggroAttach;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.IInvulnerableTime;
import org.confluence.mod.item.curio.fishing.IFishingPower;
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
    private float fishingPower;
    private int crystals;
    private double starRange;
    private double coinRange;
    private double dropsRange;

    public PlayerAbility() {
        this.jumpBoost = 1.0;
        this.fallResistance = 0;
        this.invulnerableTime = 20;
        this.criticalChance = 0.0;
        this.fireImmune = false;
        this.maxLavaImmuneTicks = 0;
        this.remainLavaImmuneTicks = 0;

        this.aggro = 0;
        this.fishingPower = 0.0F;
        this.crystals = 0;
        this.starRange = 1.75;
        this.coinRange = 2.0;
        this.dropsRange = 0.0;
    }

    public void flushAbility(LivingEntity living) {
        AtomicDouble jump = new AtomicDouble(1.0);
        AtomicInteger fall = new AtomicInteger();
        AtomicInteger invul = new AtomicInteger(20);
        AtomicDouble chance = new AtomicDouble();
        AtomicBoolean fire = new AtomicBoolean();
        AtomicInteger lava = new AtomicInteger();
        AtomicInteger aggro = new AtomicInteger();
        AtomicDouble fishing = new AtomicDouble();
        AtomicBoolean star = new AtomicBoolean();
        AtomicBoolean coin = new AtomicBoolean();
        AtomicBoolean drops = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                if (itemStack.isEmpty()) continue;
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
                if (item instanceof IFishingPower iFishingPower) fishing.addAndGet(iFishingPower.getFishingBonus());
                if (item instanceof IRangePickup.Star) star.set(true);
                if (item instanceof IRangePickup.Coin) coin.set(true);
                if (item instanceof IRangePickup.Drops) drops.set(true);
            }
        });
        this.jumpBoost = jump.get();
        this.fallResistance = fall.get();
        this.invulnerableTime = invul.get();
        this.criticalChance = chance.get();
        this.fireImmune = fire.get();
        this.maxLavaImmuneTicks = lava.get();
        this.aggro = aggro.get();
        this.fishingPower = fishing.floatValue();
        this.starRange = star.get() ? 14.25 : 1.75;
        this.coinRange = coin.get() ? 16.67 : 2.0;
        this.dropsRange = drops.get() ? 6.25 : 0.0;
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

    public float getFishingPower(Player player) {
        float base = fishingPower;
        Level level = player.level();
        long dayTime = level.dayTime() % 24000; // [0, 24000]
        if (level.isRaining()) base *= 1.1F;
        else if (level.isThundering()) base *= 1.2F;
        if (dayTime >= 22500 || dayTime == 0) base *= 1.3F; // 04:30 -> 06:00
        else if (dayTime >= 3000 && dayTime <= 9000) base *= 0.8F; // 09:00 -> 15:00
        else if (dayTime >= 12000 && dayTime <= 13500) base *= 1.3F; // 18:00 -> 19:30
        else if (dayTime >= 15300 && dayTime <= 20200) base *= 0.8F; // 21:18 -> 02:12
        base *= switch (level.getMoonPhase()) {
            case 0 -> 1.1F; // 满月
            case 1, 7 -> 1.05F; // 凸月
            case 5 -> 0.95F; // 眉月
            case 4 -> 0.9F; // 新月
            default -> 1.0F;
        };
        if (player.isLocalPlayer()) { // 血月
            if (ClientPacketHandler.getMoonSpecific() == 11) base *= 1.1F;
        } else if (level instanceof ServerLevel serverLevel) {
            if (ConfluenceData.get(serverLevel).getMoonSpecific() == 11) base *= 1.1F;
        }
        if (player.hasEffect(ModEffects.FISHING.get())) base += 1.0F;
        return base + player.getLuck();
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

    public double getStarRange() {
        return starRange;
    }

    public double getCoinRange() {
        return coinRange;
    }

    public double getDropsRange() {
        return dropsRange;
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
        nbt.putFloat("fishingPower", fishingPower);
        nbt.putInt("crystals", crystals);
        nbt.putDouble("starRange", starRange);
        nbt.putDouble("coinRange", coinRange);
        nbt.putDouble("dropsRange", dropsRange);
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
        this.fishingPower = nbt.getFloat("fishingPower");
        this.crystals = nbt.getInt("crystals");
        this.starRange = nbt.getDouble("starRange");
        this.coinRange = nbt.getDouble("coinRange");
        this.dropsRange = nbt.getDouble("dropsRange");
    }

    public void copyFrom(PlayerAbility playerAbility) {
        this.jumpBoost = playerAbility.jumpBoost;
        this.fallResistance = playerAbility.fallResistance;
        this.invulnerableTime = playerAbility.invulnerableTime;
        this.criticalChance = playerAbility.criticalChance;
        this.fireImmune = playerAbility.fireImmune;
        this.maxLavaImmuneTicks = playerAbility.maxLavaImmuneTicks;

        this.aggro = playerAbility.aggro;
        this.fishingPower = playerAbility.fishingPower;
        this.crystals = playerAbility.crystals;
        this.starRange = playerAbility.starRange;
        this.coinRange = playerAbility.coinRange;
        this.dropsRange = playerAbility.dropsRange;
    }
}
