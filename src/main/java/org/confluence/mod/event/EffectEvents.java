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
import org.confluence.mod.effect.beneficial.LuckEffect;
import org.confluence.mod.effect.harmful.IchorEffect;
import org.confluence.mod.effect.neutral.LoveEffect;
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
        MobEffectInstance effectInstance = event.getEffectInstance();
        MobEffect mobEffect = effectInstance.getEffect();
        LivingEntity living = event.getEntity();
        AttributeMap attributeMap = living.getAttributes();

        LoveEffect.onAdd(mobEffect, living, event.getEffectSource());
        IchorEffect.onAdd(mobEffect, attributeMap);
        LuckEffect.onAdd(mobEffect, attributeMap, effectInstance.getAmplifier());
    }

    @SubscribeEvent
    public static void effectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        LivingEntity living = event.getEntity();
        AttributeMap attributeMap = living.getAttributes();

        IchorEffect.onRemove(mobEffect, attributeMap);
        LuckEffect.onRemove(living, mobEffect, attributeMap, mobEffectInstance.getAmplifier());
    }

    @SubscribeEvent
    public static void effectRemove(MobEffectEvent.Remove event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        LivingEntity living = event.getEntity();
        AttributeMap attributeMap = living.getAttributes();

        IchorEffect.onRemove(mobEffect, attributeMap);
        LuckEffect.onRemove(living, mobEffect, attributeMap, mobEffectInstance.getAmplifier());
    }
}
