package org.confluence.mod.mana;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class ManaProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<ManaStorage> MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private ManaStorage manaStorage;
    private final LazyOptional<ManaStorage> manaStorageLazyOptional = LazyOptional.of(this::getOrCreateStorage);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        return MANA_CAPABILITY.orEmpty(cap, manaStorageLazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateStorage().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateStorage().deserializeNBT(nbt);
    }

    private ManaStorage getOrCreateStorage() {
        return manaStorage == null ? new ManaStorage() : manaStorage;
    }
}
