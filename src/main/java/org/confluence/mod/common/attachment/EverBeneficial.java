package org.confluence.mod.common.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class EverBeneficial implements INBTSerializable<CompoundTag> {
    private boolean vitalCrystalUsed;
    private boolean aegisAppleUsed;
    private boolean ambrosiaUsed;
    private boolean gummyWormUsed;
    private boolean galaxyPearlUsed;

    public EverBeneficial() {
        this.vitalCrystalUsed = false;
        this.aegisAppleUsed = false;
        this.ambrosiaUsed = false;
        this.gummyWormUsed = false;
        this.galaxyPearlUsed = false;
    }

    public void setVitalCrystalUsed() {
        this.vitalCrystalUsed = true;
    }

    public boolean isVitalCrystalUsed() {
        return vitalCrystalUsed;
    }

    public void setAegisAppleUsed() {
        this.aegisAppleUsed = true;
    }

    public boolean isAegisAppleUsed() {
        return aegisAppleUsed;
    }

    public void setAmbrosiaUsed() {
        this.ambrosiaUsed = true;
    }

    public boolean isAmbrosiaUsed() {
        return ambrosiaUsed;
    }

    public void setGummyWormUsed() {
        this.gummyWormUsed = true;
    }

    public boolean isGummyWormUsed() {
        return gummyWormUsed;
    }

    public void setGalaxyPearlUsed() {
        this.galaxyPearlUsed = true;
    }

    public boolean isGalaxyPearlUsed() {
        return galaxyPearlUsed;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("vitalCrystalUsed", vitalCrystalUsed);
        nbt.putBoolean("aegisAppleUsed", aegisAppleUsed);
        nbt.putBoolean("ambrosiaUsed", ambrosiaUsed);
        nbt.putBoolean("gummyWormUsed", gummyWormUsed);
        nbt.putBoolean("galaxyPearlUsed", galaxyPearlUsed);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        this.vitalCrystalUsed = nbt.getBoolean("vitalCrystalUsed");
        this.aegisAppleUsed = nbt.getBoolean("aegisAppleUsed");
        this.ambrosiaUsed = nbt.getBoolean("ambrosiaUsed");
        this.gummyWormUsed = nbt.getBoolean("gummyWormUsed");
        this.galaxyPearlUsed = nbt.getBoolean("galaxyPearlUsed");
    }
}
