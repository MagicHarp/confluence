package org.confluence.mod.common.attachment;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.confluence.mod.common.init.ModAttachments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.concurrent.atomic.AtomicBoolean;

public class GamePlay implements INBTSerializable<CompoundTag> {
    private float fishingPower;
    private int crystals;
    private int fruits;
    private double starRange;
    private double coinRange;

    public GamePlay() {
        this.fishingPower = 0.0F;
        this.crystals = 0;
        this.fruits = 0;
        this.starRange = 1.75;
        this.coinRange = 2.0;
    }

    // todo
    public void flushAbility(LivingEntity living) {
        AtomicDouble fishing = new AtomicDouble();
        AtomicBoolean star = new AtomicBoolean();
        AtomicBoolean coin = new AtomicBoolean();

        this.fishingPower = fishing.floatValue();
        this.starRange = star.get() ? 14.25 : 1.75;
        this.coinRange = coin.get() ? 16.67 : 2.0;
    }

    // todo
    public float getFishingPower(Player player) {
        float base = fishingPower;
        if (player.getData(ModAttachments.EVER_BENEFICIAL.get()).isGummyWormUsed()) base += 3.0F;
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
//        if (player.isLocalPlayer()) { // 血月
//            if (ClientPacketHandler.isBloodyMoon()) base *= 1.1F;
//        } else if (level instanceof ServerLevel serverLevel) {
//            if (ConfluenceData.get(serverLevel).getSpecificMoon().isBloodyMoon()) base *= 1.1F;
//        }
//        if (player.hasEffect(ModEffects.FISHING.get())) base += 15.0F;
        return base + player.getLuck();
    }

    public boolean increaseCrystals() {
        if (crystals < 15) {
            this.crystals++;
            return true;
        }
        return false;
    }

    public int getCrystals() {
        return crystals;
    }

    public boolean increaseFruits() {
        if (fruits < 20) {
            this.fruits++;
            return true;
        }
        return false;
    }

    public int getFruits() {
        return fruits;
    }

    public double getStarRange() {
        return starRange;
    }

    public double getCoinRange() {
        return coinRange;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("fishingPower", fishingPower);
        nbt.putInt("crystals", crystals);
        nbt.putInt("fruits", fruits);
        nbt.putDouble("starRange", starRange);
        nbt.putDouble("coinRange", coinRange);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        this.fishingPower = nbt.getFloat("fishingPower");
        this.crystals = nbt.getInt("crystals");
        this.fruits = nbt.getInt("fruits");
        this.starRange = nbt.getDouble("starRange");
        this.coinRange = nbt.getDouble("coinRange");
    }
}
