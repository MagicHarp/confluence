package org.confluence.mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.BeneficialEffect.*;
import org.confluence.mod.effect.HarmfulEffect.*;
import org.confluence.mod.effect.NeutralEffect.GravitationEffect;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Confluence.MODID);

    public static final RegistryObject<ManaIssueEffect> MANA_ISSUE = MOB_EFFECTS.register("mana_issue", ManaIssueEffect::new);
    public static final RegistryObject<ExquisitelyStuffedEffect> EXQUISITELY_STUFFED = MOB_EFFECTS.register("exquisitely_stuffed", ExquisitelyStuffedEffect::new);
    public static final RegistryObject<IronSkinEffect> IRON_SKIN = MOB_EFFECTS.register("iron_skin", IronSkinEffect::new);
    public static final RegistryObject<RegenerationEffect> REGENERATION = MOB_EFFECTS.register("regeneration", RegenerationEffect::new);
    public static final RegistryObject<EnduranceEffect> ENDURANCE = MOB_EFFECTS.register("endurance", EnduranceEffect::new);
    public static final RegistryObject<InfernoEffect> INFERNO = MOB_EFFECTS.register("inferno", InfernoEffect::new);
    public static final RegistryObject<LifeForceEffect> LIFE_FORCE = MOB_EFFECTS.register("life_force", LifeForceEffect::new);
    public static final RegistryObject<RageEffect> RAGE = MOB_EFFECTS.register("rage", RageEffect::new);
    public static final RegistryObject<WrathEffect> WRATH = MOB_EFFECTS.register("wrath", WrathEffect::new);
    public static final RegistryObject<ThornsEffect> THORNS = MOB_EFFECTS.register("thorns", ThornsEffect::new);
    public static final RegistryObject<HoneyEffect> HONEY = MOB_EFFECTS.register("honey", HoneyEffect::new);
    public static final RegistryObject<PaladinsShieldEffect> PALADINS_SHIELD = MOB_EFFECTS.register("paladins_shield", PaladinsShieldEffect::new);
    public static final RegistryObject<TitanEffect> TITAN = MOB_EFFECTS.register("titan", TitanEffect::new);
    public static final RegistryObject<FeatherFallEffect> FEATHER_FALL = MOB_EFFECTS.register("feather_fall", FeatherFallEffect::new);
    public static final RegistryObject<GravitationEffect> GRAVITATION = MOB_EFFECTS.register("gravitation", GravitationEffect::new);
    public static final RegistryObject<BuilderEffect> BUILDER = MOB_EFFECTS.register("builder", BuilderEffect::new);
    public static final RegistryObject<FishingEffect> FISHING = MOB_EFFECTS.register("fishing", FishingEffect::new);
    public static final RegistryObject<BleedingEffect> BLEEDING = MOB_EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<AcidVenomEffect> ACDID_VENOM = MOB_EFFECTS.register("acid_venom", AcidVenomEffect::new);
    public static final RegistryObject<FrostburnEffect> FROSTBURN = MOB_EFFECTS.register("frostburn", FrostburnEffect::new);
    public static final RegistryObject<CursedInfernoEffect> CURSED_INFERNO = MOB_EFFECTS.register("cursed_inferno", CursedInfernoEffect::new);
    public static final RegistryObject<DarknessEffect> DARKNESS = MOB_EFFECTS.register("darkness", DarknessEffect::new);
    public static final RegistryObject<SilencedEffect> SILENCED = MOB_EFFECTS.register("silenced", SilencedEffect::new);
    public static final RegistryObject<CursedEffect> CURSED = MOB_EFFECTS.register("cursed", CursedEffect::new);
    public static final RegistryObject<ConfusedEffect> CONFUSED = MOB_EFFECTS.register("confused", ConfusedEffect::new);
    public static final RegistryObject<WitheredArmorEffect> WITHERED_ARMOR = MOB_EFFECTS.register("withered_armor", WitheredArmorEffect::new);
    public static final RegistryObject<IchorEffect> ICHOR = MOB_EFFECTS.register("ichor", IchorEffect::new);
    public static final RegistryObject<SpeedEffect> SPEED = MOB_EFFECTS.register("speed", SpeedEffect::new);
}
