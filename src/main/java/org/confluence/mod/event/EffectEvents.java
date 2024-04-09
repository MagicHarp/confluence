package org.confluence.mod.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.BeneficialEffect.*;
import org.confluence.mod.effect.HarmfulEffect.*;
import org.confluence.mod.item.curio.combat.EffectInvulnerable;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectEvents {
    @SubscribeEvent
    public static void effectApplicable(MobEffectEvent.Applicable event) {
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        LivingEntity living = event.getEntity();

        if (EffectInvulnerable.apply(mobEffect, living)) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void effectAdded(MobEffectEvent.Added event) {
        MobEffect mobEffect = event.getEffectInstance().getEffect();
        AttributeMap attributeMap = event.getEntity().getAttributes();

        IronSkinEffect.onAdd(mobEffect, attributeMap);
        FishingEffect.onAdd(event.getEntity());
        EnduranceEffect.onAdd(mobEffect,attributeMap);
        ExquisitelyStuffedEffect.onAdd(event.getEntity(),mobEffect,attributeMap);
        LifeForceEffect.onAdd(mobEffect,attributeMap);
        SpeedEffect.onAdd(mobEffect,attributeMap);
        WrathEffect.onAdd(mobEffect,attributeMap);
        AcidVenomEffect.onAdd(event.getEntity());
        ConfusedEffect.onAdd(event.getEntity());
        CursedInfernoEffect.onAdd(event.getEntity());
        FrostburnEffect.onAdd(event.getEntity());
        IchorEffect.onAdd(mobEffect,attributeMap);
        WitheredArmorEffect.onAdd(mobEffect,attributeMap);
    }

    @SubscribeEvent
    public static void effectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        AttributeMap attributeMap = event.getEntity().getAttributes();

        IronSkinEffect.onRemove(mobEffect, attributeMap);
        EnduranceEffect.onRemove(mobEffect,attributeMap);
        ExquisitelyStuffedEffect.onRemove(event.getEntity(),mobEffect,attributeMap);
        LifeForceEffect.onRemove(mobEffect,attributeMap);
        SpeedEffect.onRemove(mobEffect,attributeMap);
        WrathEffect.onRemove(mobEffect,attributeMap);
        AcidVenomEffect.onRemove(event.getEntity());
        ConfusedEffect.onRemove(event.getEntity());
        CursedInfernoEffect.onRemove(event.getEntity());
        FrostburnEffect.onRemove(event.getEntity());
        IchorEffect.onRemove(mobEffect,attributeMap);
        WitheredArmorEffect.onRemove(mobEffect,attributeMap);
    }

    @SubscribeEvent
    public static void effectRemove(MobEffectEvent.Remove event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        if (mobEffectInstance == null) return;
        MobEffect mobEffect = mobEffectInstance.getEffect();
        AttributeMap attributeMap = event.getEntity().getAttributes();

        IronSkinEffect.onRemove(mobEffect, attributeMap);
        FishingEffect.onRemove(event.getEntity());
        EnduranceEffect.onRemove(mobEffect,attributeMap);
        ExquisitelyStuffedEffect.onRemove(event.getEntity(),mobEffect,attributeMap);
        LifeForceEffect.onRemove(mobEffect,attributeMap);
        SpeedEffect.onRemove(mobEffect,attributeMap);
        WrathEffect.onRemove(mobEffect,attributeMap);
        AcidVenomEffect.onRemove(event.getEntity());
        ConfusedEffect.onRemove(event.getEntity());
        CursedInfernoEffect.onRemove(event.getEntity());
        FrostburnEffect.onRemove(event.getEntity());
        IchorEffect.onRemove(mobEffect,attributeMap);
        WitheredArmorEffect.onRemove(mobEffect,attributeMap);
    }

    @SubscribeEvent
    public static void noHeal(LivingHealEvent event) {
        BleedingEffect.onAdd(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void noRightClickItem(PlayerInteractEvent.RightClickItem event) {
        SilencedEffect.onAdd(event.getEntity(), event);
        CursedEffect.onRightClick(event.getEntity(),event);
    }

    @SubscribeEvent
    public static void noLeftClickItem(InputEvent.InteractionKeyMappingTriggered event){
        CursedEffect.onLeftClick(event);
    }
}
