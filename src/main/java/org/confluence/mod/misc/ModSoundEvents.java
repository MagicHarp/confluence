package org.confluence.mod.misc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModSoundEvents {
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
    public static final RegistryObject<SoundEvent> COOLDOWN_RECOVERY = register("cooldown_recovery"); // CD冷却
    public static final RegistryObject<SoundEvent> FROZEN_ARROW = register("frozen_arrow"); // 冰雪射弹
    public static final RegistryObject<SoundEvent> FROZEN_BROKEN = register("frozen_broken");
    public static final RegistryObject<SoundEvent> SHIMMER_DETACHMENT = register("shimmer_detachment"); // 脱离微光
    public static final RegistryObject<SoundEvent> SHIMMER_EVOLUTION = register("shimmer_evolution"); // 嬗变
    public static final RegistryObject<SoundEvent> SHIMMER_IMMERSION = register("shimmer_immersion"); // 生物入微光
    public static final RegistryObject<SoundEvent> SHIMMER_ITEM_INTERACTIONS = register("shimmer_item_interactions"); // 物品入微光
    public static final RegistryObject<SoundEvent> STAR = register("star"); // 坠星
    public static final RegistryObject<SoundEvent> STAR_LANDS = register("star_lands"); // 星星落地
    public static final RegistryObject<SoundEvent> TERRA_OPERATION = register("terra_operation"); // 操作音效
    public static final RegistryObject<SoundEvent> USE_MOUNTS = register("use_mounts"); // 召唤坐骑
    public static final RegistryObject<SoundEvent> ACHIEVEMENTS = register("achievements"); // 成就音效
    public static final RegistryObject<SoundEvent> REGULAR_STAFF_SHOOT_2 = register("regular_staff_shoot_2"); // 射弹2
    public static final RegistryObject<SoundEvent> ROAR = register("roar"); // boss吼叫
    public static final RegistryObject<SoundEvent> HURRIED_ROARING = register("hurried_roaring"); //疯狗冲刺


    private static RegistryObject<SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(Confluence.asResource(id)));
    }

    public static class Types {
        public static final SoundType COIN = new SoundType(1.0F, 1.0F, COINS.get(), COINS.get(), COINS.get(), COINS.get(), COINS.get());
    }
}
