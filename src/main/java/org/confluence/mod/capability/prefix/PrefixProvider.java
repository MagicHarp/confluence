package org.confluence.mod.capability.prefix;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrefixProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<ItemPrefix> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private ItemPrefix itemPrefix;
    private final LazyOptional<ItemPrefix> itemPrefixLazyOptional = LazyOptional.of(this::getOrCreateStorage);
    private transient final PrefixType type;

    public PrefixProvider(PrefixType type) {
        this.type = type;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, itemPrefixLazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateStorage().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateStorage().deserializeNBT(nbt);
    }

    private ItemPrefix getOrCreateStorage() {
        if (itemPrefix == null) {
            this.itemPrefix = new ItemPrefix(type);
        }
        return itemPrefix;
    }
}
