package org.confluence.mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.neutral.CerebralMindtrickEffect;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.recipe.AmountIngredient;

import static org.confluence.mod.Confluence.MODID;

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
    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
            CraftingHelper.register(new ResourceLocation(MODID, "amount"), AmountIngredient.Serializer.INSTANCE);
        });
    }

    @SubscribeEvent
    public static void modify(EntityAttributeModificationEvent event) {
        ModAttributes.readJsonConfig();
        ModAttributes.registerAttribute(ModAttributes.CRIT_CHANCE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_VELOCITY.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_DAMAGE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.DODGE_CHANCE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.MINING_SPEED.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.AGGRO.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.MAGIC_DAMAGE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.ARMOR_PASS.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.PICKUP_RANGE.get(), event::add);
    }
}
