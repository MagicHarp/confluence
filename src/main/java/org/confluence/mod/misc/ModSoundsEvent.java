package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModSoundsEvent {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Confluence.MODID);

    public static final RegistryObject<SoundEvent> TRANSMISSION = register("transmission");
    public static final RegistryObject<SoundEvent> WAVING = register("waving");
    public static final RegistryObject<SoundEvent> DOUBLE_JUMP = register("double_jump");
    public static final RegistryObject<SoundEvent> LASER = register("laser");
    public static final RegistryObject<SoundEvent> LIGHTSABER_QUICK = register("lightsaber_quick");
    public static final RegistryObject<SoundEvent> LIGHTSABER_SLOW = register("lightsaber_slow");
    public static final RegistryObject<SoundEvent> LIGHTSABER_OPEN = register("lightsaber_open");
    public static final RegistryObject<SoundEvent> REGULAR_STAFF_SHOOT = register("regular_staff_shoot");
    public static final RegistryObject<SoundEvent> SHOES_FLY = register("shoes_fly");
    public static final RegistryObject<SoundEvent> SHOES_FLY_JET = register("shoes_fly_jet");
    public static final RegistryObject<SoundEvent> SHOES_WALK = register("shoes_walk");
    public static final RegistryObject<SoundEvent> SHOOT = register("shoot");
    public static final RegistryObject<SoundEvent> SPARKLE_SHOOT = register("sparkle_shoot");
    public static final RegistryObject<SoundEvent> FART_SOUND = register("fart_sound");
    public static final RegistryObject<SoundEvent> LIFE_CRYSTAL_USE = register("life_crystal_use");
    public static final RegistryObject<SoundEvent> MANA_STAR_USE = register("mana_star_use");
    public static final RegistryObject<SoundEvent> COINS = register("coins");
    public static final RegistryObject<SoundEvent> ALPHA = register("alpha");
    public static final RegistryObject<SoundEvent> ROUTINE_HURT = register("routine_hurt");
    public static final RegistryObject<SoundEvent> ROUTINE_DEATH = register("routine_death");

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Confluence.MODID, id)));
    }
}
