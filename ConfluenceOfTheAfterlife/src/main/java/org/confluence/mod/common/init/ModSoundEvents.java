package org.confluence.mod.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;

public final class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, Confluence.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSMISSION = register("transmission");
    public static final DeferredHolder<SoundEvent, SoundEvent> WAVING = register("waving");
    public static final DeferredHolder<SoundEvent, SoundEvent> DOUBLE_JUMP = register("double_jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> LASER = register("laser");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_QUICK = register("lightsaber_quick");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_SLOW = register("lightsaber_slow");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_OPEN = register("lightsaber_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> REGULAR_STAFF_SHOOT = register("regular_staff_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOES_FLY = register("shoes_fly");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOES_FLY_JET = register("shoes_fly_jet");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOES_WALK = register("shoes_walk");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOOT = register("shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPARKLE_SHOOT = register("sparkle_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> FART_SOUND = register("fart_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIFE_CRYSTAL_USE = register("life_crystal_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> MANA_STAR_USE = register("mana_star_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> COINS = register("coins");
    public static final DeferredHolder<SoundEvent, SoundEvent> ALPHA = register("alpha");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROUTINE_HURT = register("routine_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROUTINE_DEATH = register("routine_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> COOLDOWN_RECOVERY = register("cooldown_recovery"); // CD冷却
    public static final DeferredHolder<SoundEvent, SoundEvent> FROZEN_ARROW = register("frozen_arrow"); // 冰雪射弹
    public static final DeferredHolder<SoundEvent, SoundEvent> FROZEN_BROKEN = register("frozen_broken");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHIMMER_DETACHMENT = register("shimmer_detachment"); // 脱离微光
    public static final DeferredHolder<SoundEvent, SoundEvent> SHIMMER_EVOLUTION = register("shimmer_evolution"); // 嬗变
    public static final DeferredHolder<SoundEvent, SoundEvent> SHIMMER_IMMERSION = register("shimmer_immersion"); // 生物入微光
    public static final DeferredHolder<SoundEvent, SoundEvent> SHIMMER_ITEM_INTERACTIONS = register("shimmer_item_interactions"); // 物品入微光
    public static final DeferredHolder<SoundEvent, SoundEvent> STAR = register("star"); // 坠星
    public static final DeferredHolder<SoundEvent, SoundEvent> STAR_LANDS = register("star_lands"); // 星星落地
    public static final DeferredHolder<SoundEvent, SoundEvent> TERRA_OPERATION = register("terra_operation"); // 操作音效
    public static final DeferredHolder<SoundEvent, SoundEvent> USE_MOUNTS = register("use_mounts"); // 召唤坐骑

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENT.register(name, () -> SoundEvent.createVariableRangeEvent(Confluence.asResource(name)));
    }

    public static class Types {
        public static final SoundType COIN = new DeferredSoundType(1.0F, 1.0F, COINS, COINS, COINS, COINS, COINS);
    }
}
