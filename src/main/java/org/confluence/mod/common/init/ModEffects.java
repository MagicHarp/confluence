package org.confluence.mod.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.effect.PublicMobEffect;
import org.confluence.mod.common.effect.beneficial.*;
import org.confluence.mod.common.effect.harmful.*;
import org.confluence.mod.common.effect.neutral.CerebralMindtrickEffect;
import org.confluence.mod.common.effect.neutral.LoveEffect;
import org.confluence.mod.common.effect.neutral.ShimmerEffect;

public final class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Confluence.MODID);

    public static final DeferredHolder<MobEffect, ExquisitelyStuffedEffect> EXQUISITELY_STUFFED = EFFECTS.register("exquisitely_stuffed", ExquisitelyStuffedEffect::new);
    public static final DeferredHolder<MobEffect, IronSkinEffect> IRON_SKIN = EFFECTS.register("iron_skin", IronSkinEffect::new);
    public static final DeferredHolder<MobEffect, EnduranceEffect> ENDURANCE = EFFECTS.register("endurance", EnduranceEffect::new);
    public static final DeferredHolder<MobEffect, InfernoEffect> INFERNO = EFFECTS.register("inferno", InfernoEffect::new);
    public static final DeferredHolder<MobEffect, LifeForceEffect> LIFE_FORCE = EFFECTS.register("life_force", LifeForceEffect::new);
    public static final DeferredHolder<MobEffect, RageEffect> RAGE = EFFECTS.register("rage", RageEffect::new);
    public static final DeferredHolder<MobEffect, WrathEffect> WRATH = EFFECTS.register("wrath", WrathEffect::new);
    public static final DeferredHolder<MobEffect, ThornsEffect> THORNS = EFFECTS.register("thorns", ThornsEffect::new);
    public static final DeferredHolder<MobEffect, HoneyEffect> HONEY = EFFECTS.register("honey", HoneyEffect::new);
    public static final DeferredHolder<MobEffect, ManaRegenerationEffect> MANA_REGENERATION = EFFECTS.register("mana_regeneration", ManaRegenerationEffect::new);
    public static final DeferredHolder<MobEffect, PaladinsShieldEffect> PALADINS_SHIELD = EFFECTS.register("paladins_shield", PaladinsShieldEffect::new);
    public static final DeferredHolder<MobEffect, TitanEffect> TITAN = EFFECTS.register("titan", TitanEffect::new);
    public static final DeferredHolder<MobEffect, GravitationEffect> GRAVITATION = EFFECTS.register("gravitation", GravitationEffect::new);
    public static final DeferredHolder<MobEffect, BuilderEffect> BUILDER = EFFECTS.register("builder", BuilderEffect::new);
    public static final DeferredHolder<MobEffect, FishingEffect> FISHING = EFFECTS.register("fishing", FishingEffect::new);
    public static final DeferredHolder<MobEffect, MagicPowerEffect> MAGIC_POWER = EFFECTS.register("magic_power", MagicPowerEffect::new);
    public static final DeferredHolder<MobEffect, ObsidianSkinEffect> OBSIDIAN_SKIN = EFFECTS.register("obsidian_skin", ObsidianSkinEffect::new);
    public static final DeferredHolder<MobEffect, LuckEffect> LUCK_EFFECT = EFFECTS.register("luck", LuckEffect::new);
    public static final DeferredHolder<MobEffect, WaterWalkingEffect> WATER_WALKING = EFFECTS.register("water_walking", WaterWalkingEffect::new);
    public static final DeferredHolder<MobEffect, HeartReachEffect> HEART_REACH = EFFECTS.register("heart_reach", HeartReachEffect::new);
    public static final DeferredHolder<MobEffect, ArcheryEffect> ARCHERY = EFFECTS.register("archery", ArcheryEffect::new);
    public static final DeferredHolder<MobEffect, FlipperEffect> FLIPPER = EFFECTS.register("flipper", FlipperEffect::new);
    public static final DeferredHolder<MobEffect, ShineEffect> SHINE = EFFECTS.register("shine", ShineEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SPELUNKER = EFFECTS.register("spelunker", () -> new PublicMobEffect(MobEffectCategory.BENEFICIAL, 0xFFFF00));
    public static final DeferredHolder<MobEffect, MobEffect> HUNTER = EFFECTS.register("hunter", () -> new PublicMobEffect(MobEffectCategory.BENEFICIAL, 0xFFC800));
    public static final DeferredHolder<MobEffect, MobEffect> DANGER_SENSE = EFFECTS.register("danger_sense", () -> new PublicMobEffect(MobEffectCategory.BENEFICIAL, 0xFFAFAF));
    public static final DeferredHolder<MobEffect, ManaSicknessEffect> MANA_SICKNESS = EFFECTS.register("mana_sickness", ManaSicknessEffect::new);
    public static final DeferredHolder<MobEffect, BleedingEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final DeferredHolder<MobEffect, AcidVenomEffect> ACID_VENOM = EFFECTS.register("acid_venom", AcidVenomEffect::new);
    public static final DeferredHolder<MobEffect, FrostburnEffect> FROST_BURN = EFFECTS.register("frost_burn", FrostburnEffect::new);
    public static final DeferredHolder<MobEffect, CursedInfernoEffect> CURSED_INFERNO = EFFECTS.register("cursed_inferno", CursedInfernoEffect::new);
    public static final DeferredHolder<MobEffect, SilencedEffect> SILENCED = EFFECTS.register("silenced", SilencedEffect::new);
    public static final DeferredHolder<MobEffect, CursedEffect> CURSED = EFFECTS.register("cursed", CursedEffect::new);
    public static final DeferredHolder<MobEffect, ConfusedEffect> CONFUSED = EFFECTS.register("confused", ConfusedEffect::new);
    public static final DeferredHolder<MobEffect, WitheredArmorEffect> WITHERED_ARMOR = EFFECTS.register("withered_armor", WitheredArmorEffect::new);
    public static final DeferredHolder<MobEffect, IchorEffect> ICHOR = EFFECTS.register("ichor", IchorEffect::new);
    public static final DeferredHolder<MobEffect, PotionSicknessEffect> POTION_SICKNESS = EFFECTS.register("potion_sickness", PotionSicknessEffect::new);
    public static final DeferredHolder<MobEffect, BrokenArmorEffect> BROKEN_ARMOR = EFFECTS.register("broken_armor", BrokenArmorEffect::new);
    public static final DeferredHolder<MobEffect, StonedEffect> STONED = EFFECTS.register("stoned", StonedEffect::new);
    public static final DeferredHolder<MobEffect, BloodButcheredEffect> BLOOD_BUTCHERED = EFFECTS.register("blood_butchered", BloodButcheredEffect::new);
    public static final DeferredHolder<MobEffect, TentacleSpikesEffect> TENTACLE_SPIKES = EFFECTS.register("tentacle_spikes", TentacleSpikesEffect::new);

    public static final DeferredHolder<MobEffect, CerebralMindtrickEffect> CEREBRAL_MINDTRICK = EFFECTS.register("cerebral_mindtrick", CerebralMindtrickEffect::new);
    public static final DeferredHolder<MobEffect, LoveEffect> LOVE = EFFECTS.register("love", LoveEffect::new);
    public static final DeferredHolder<MobEffect, ShimmerEffect> SHIMMER = EFFECTS.register("shimmer", ShimmerEffect::new);

    public static void healPerSecond(LivingEntity living, float amount) {
        if (living.level().getGameTime() % 20 == 0) {
            if (living.hasEffect(HONEY)) amount += 1;
            living.heal(amount);
        }
    }
}
