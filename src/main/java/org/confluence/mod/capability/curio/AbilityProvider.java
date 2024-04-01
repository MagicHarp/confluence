package org.confluence.mod.capability.curio;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<PlayerAbility> ABILITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private PlayerAbility playerAbility;
    private final LazyOptional<PlayerAbility> abilityLazyOptional = LazyOptional.of(this::getOrCreateStorage);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ABILITY_CAPABILITY.orEmpty(cap, abilityLazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return playerAbility.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerAbility.deserializeNBT(nbt);
    }

    private PlayerAbility getOrCreateStorage() {
        if (playerAbility == null) {
            this.playerAbility = new PlayerAbility();
        }
        return playerAbility;
    }
}
