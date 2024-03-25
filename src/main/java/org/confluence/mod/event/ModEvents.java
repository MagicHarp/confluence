package org.confluence.mod.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.mixin.RangedAttributeAccessor;
import org.confluence.mod.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
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
        Confluence.GAME_PHASE = GameRules.register("terraGamePhase", GameRules.Category.UPDATES, GameRules.IntegerValue.create(6));
        NetworkHandler.register();

        Attribute armor = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor"));
        if (armor instanceof RangedAttribute rangedAttribute) {
            ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0D);
        }
        Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor_toughness"));
        if (attribute instanceof RangedAttribute rangedAttribute) {
            ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0D);
        }
    }

    @SubscribeEvent
    public static void loadedComplete(FMLLoadCompleteEvent event) {
        //((StepRevealingBlock) Ores.DEEPSLATE_COBALT_ORE.get()).createRevelation("");
    }
}
