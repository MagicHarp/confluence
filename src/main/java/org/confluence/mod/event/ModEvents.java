package org.confluence.mod.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.Ores;
import org.confluence.mod.block.reveal.StepRevealingBlock;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.mixin.RangedAttributeAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.worldgen.biome.SurfaceRuleData;
import org.confluence.mod.worldgen.biome.TestRegion1;
import org.confluence.mod.worldgen.biome.TestRegion2;
import org.confluence.mod.worldgen.biome.TestRegion3;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLUE_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.GREEN_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.PINK_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.CORRUPTED_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.DESERT_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.EVIL_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.ICE_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.LAVA_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.LUMINOUS_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.CRIMSON_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.PURPLE_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.RED_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.TROPIC_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.YELLOW_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ModEntities.BLACK_SLIME.get(), Monster.createMonsterAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();

        Attribute armor = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor"));
        if (armor instanceof RangedAttribute rangedAttribute) {
            ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0D);
        }
        Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor_toughness"));
        if (attribute instanceof RangedAttribute rangedAttribute) {
            ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0D);
        }

        event.enqueueWork(() -> {
            // Weights are kept intentionally low as we add minimal biomes
            Regions.register(new TestRegion1(new ResourceLocation(Confluence.MODID, "cold_blue"), 1));
            Regions.register(new TestRegion2(new ResourceLocation(Confluence.MODID, "hot_red"), 1));
            Regions.register(new TestRegion3(new ResourceLocation(Confluence.MODID, "the_corruption"), 2));

            // Register our surface rules
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Confluence.MODID, SurfaceRuleData.makeRules());
        });
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        int step = 0;
        for (int state = 0; state < 3; state++) {
            StepRevealingBlock.create(state, step++, Ores.DEEPSLATE_COBALT_ORE.get(), Ores.DEEPSLATE_PALLADIUM_ORE.get());
            StepRevealingBlock.create(state, step++, Ores.DEEPSLATE_MITHRIL_ORE.get(), Ores.DEEPSLATE_ORICHALCUM_ORE.get());
            StepRevealingBlock.create(state, step++, Ores.DEEPSLATE_ADAMANTITE_ORE.get(), Ores.DEEPSLATE_TITANIUM_ORE.get());
        }
    }
}
