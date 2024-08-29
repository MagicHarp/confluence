package org.confluence.mod.event;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.neutral.CerebralMindtrickEffect;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkHandler.register();
            ModConfigs.onLoadCommon();
        });
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            ModEffects.CEREBRAL_MINDTRICK.get().addAttributeModifier(ModAttributes.getCriticalChance(), CerebralMindtrickEffect.CRIT_UUID, 0.04, AttributeModifier.Operation.ADDITION);
        });
    }

    @SubscribeEvent
    public static void modify(EntityAttributeModificationEvent event) {
        ModAttributes.readJsonConfig();

        if (!ModAttributes.hasCustomAttribute(ModAttributes.CRIT_CHANCE.get())) {
            event.add(EntityType.PLAYER, ModAttributes.CRIT_CHANCE.get());
        }
        if (!ModAttributes.hasCustomAttribute(ModAttributes.RANGED_VELOCITY.get())) {
            event.add(EntityType.PLAYER, ModAttributes.RANGED_VELOCITY.get());
        }
        if (!ModAttributes.hasCustomAttribute(ModAttributes.RANGED_DAMAGE.get())) {
            event.add(EntityType.PLAYER, ModAttributes.RANGED_DAMAGE.get());
        }
        if (!ModAttributes.hasCustomAttribute(ModAttributes.DODGE_CHANCE.get())) {
            event.add(EntityType.PLAYER, ModAttributes.DODGE_CHANCE.get());
        }
        if (!ModAttributes.hasCustomAttribute(ModAttributes.MINING_SPEED.get())) {
            event.add(EntityType.PLAYER, ModAttributes.MINING_SPEED.get());
        }
    }
}
