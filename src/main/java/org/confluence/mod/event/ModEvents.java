package org.confluence.mod.event;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.confluence.mod.block.natural.Ores;
import org.confluence.mod.block.reveal.StepRevealingBlock;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.fluid.FluidBuilder;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.mixin.accessor.RangedAttributeAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.recipe.AmountIngredient;
import org.confluence.mod.util.ModResources;
import org.confluence.mod.worldgen.biome.*;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        event.put(ModEntities.DEMON_EYE.get(), Monster.createMonsterAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkHandler.register();
            ModFluids.registerInteraction();
            ModFluids.registerShimmerTransform();

            if (!ModList.get().isLoaded("attributefix")) {
                Attribute armor = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor"));
                if (armor instanceof RangedAttribute rangedAttribute) {
                    ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0);
                }
                Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor_toughness"));
                if (attribute instanceof RangedAttribute rangedAttribute) {
                    ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0);
                }
            }

            ItemProperties.register(CurioItems.SPECTRE_GOGGLES.get(), new ResourceLocation(MODID, "enable"), (itemStack, level, living, speed) ->
                itemStack.getTag() != null && itemStack.getTag().getBoolean("enable") ? 1.0F : 0.0F);
            ItemProperties.register(CurioItems.MECHANICAL_LENS.get(), new ResourceLocation(MODID, "enable"), (itemStack, level, living, speed) ->
                itemStack.getTag() != null && itemStack.getTag().getBoolean("enable") ? 1.0F : 0.0F);
            FishingPoles.registerCast();

            Regions.register(new AnotherCrimsonRegion(new ResourceLocation(MODID, "another_crimson"), 1));
            Regions.register(new TheHallowRegion(new ResourceLocation(MODID, "the_hallow"), 1));
            Regions.register(new TheCorruptionRegion(new ResourceLocation(MODID, "the_corruption"), 1));
            Regions.register(new AshForestRegion(new ResourceLocation(MODID, "ash_forest"), 0));
            Regions.register(new AshWastelandRegion(new ResourceLocation(MODID, "ash_wasteland"), 0));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, SurfaceRuleData.makeRules());
        });
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            int step = 0;
            for (int state = 0; state < 3; state++) {
                StepRevealingBlock.create(state, step++, Ores.DEEPSLATE_COBALT_ORE.get(), Ores.DEEPSLATE_PALLADIUM_ORE.get());
                StepRevealingBlock.create(state, step++, Ores.DEEPSLATE_MITHRIL_ORE.get(), Ores.DEEPSLATE_ORICHALCUM_ORE.get());
                StepRevealingBlock.create(state, step++, Ores.DEEPSLATE_ADAMANTITE_ORE.get(), Ores.DEEPSLATE_TITANIUM_ORE.get());
            }
        });
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo modFileInfo = ModList.get().getModFileById(MODID);
            IModFile modFile = modFileInfo.getFile();
            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                    "confluence:terraria_art", Component.literal("Terraria Art"), false,
                    id -> new ModResources(id, modFile, "resourcepacks/terraria_art"),
                    PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN
                );
                if (pack != null) {
                    consumer.accept(pack);
                }
            });
        }
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
            CraftingHelper.register(new ResourceLocation(MODID, "amount"), AmountIngredient.Serializer.INSTANCE);
        });
        FluidBuilder.register(event);
    }
}
