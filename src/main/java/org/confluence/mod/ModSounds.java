package org.confluence.mod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Confluence.MODID);

    public static final RegistryObject<SoundEvent> TRANSMISSION = register("transmission");
    public static final RegistryObject<SoundEvent> WAVING = register("waving");
    public static final RegistryObject<SoundEvent> DOUBLE_JUMP = register("double_jump");

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Confluence.MODID, id)));
    }
}
