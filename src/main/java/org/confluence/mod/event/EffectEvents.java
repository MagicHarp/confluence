package org.confluence.mod.event;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.beneficial.*;
import org.confluence.mod.effect.harmful.IchorEffect;
import org.confluence.mod.effect.harmful.WitheredArmorEffect;
import org.confluence.mod.item.curio.combat.EffectInvul;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class EffectEvents {
    @SubscribeEvent
    public static void effectApplicable(MobEffectEvent.Applicable event) {
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        LivingEntity living = event.getEntity();

        if (EffectInvul.isInvul(mobEffect, living)) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void effectAdded(MobEffectEvent.Added event) {
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        LivingEntity living = event.getEntity();
        AttributeMap attributeMap = living.getAttributes();

        BuilderEffect.onAdd(mobEffect, attributeMap);
        IronSkinEffect.onAdd(mobEffect, attributeMap);
        FishingEffect.onAdd(living);
        EnduranceEffect.onAdd(mobEffect, attributeMap);
        ExquisitelyStuffedEffect.onAdd(living, mobEffect, attributeMap);
        LifeForceEffect.onAdd(mobEffect, attributeMap);
        WrathEffect.onAdd(mobEffect, attributeMap);
        IchorEffect.onAdd(mobEffect, attributeMap);
        WitheredArmorEffect.onAdd(mobEffect, attributeMap);

    }

    @SubscribeEvent
    public static void effectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        LivingEntity living = event.getEntity();
        AttributeMap attributeMap = living.getAttributes();

        BuilderEffect.onRemove(mobEffect, attributeMap);
        IronSkinEffect.onRemove(mobEffect, attributeMap);
        EnduranceEffect.onRemove(mobEffect, attributeMap);
        ExquisitelyStuffedEffect.onRemove(living, mobEffect, attributeMap);
        LifeForceEffect.onRemove(mobEffect, attributeMap);
        WrathEffect.onRemove(mobEffect, attributeMap);
        IchorEffect.onRemove(mobEffect, attributeMap);
        WitheredArmorEffect.onRemove(mobEffect, attributeMap);
    }

    @SubscribeEvent
    public static void effectRemove(MobEffectEvent.Remove event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        LivingEntity living = event.getEntity();
        AttributeMap attributeMap = living.getAttributes();

        BuilderEffect.onRemove(mobEffect, attributeMap);
        IronSkinEffect.onRemove(mobEffect, attributeMap);
        FishingEffect.onRemove(living);
        EnduranceEffect.onRemove(mobEffect, attributeMap);
        ExquisitelyStuffedEffect.onRemove(living, mobEffect, attributeMap);
        LifeForceEffect.onRemove(mobEffect, attributeMap);
        WrathEffect.onRemove(mobEffect, attributeMap);
        IchorEffect.onRemove(mobEffect, attributeMap);
        WitheredArmorEffect.onRemove(mobEffect, attributeMap);
    }
}
