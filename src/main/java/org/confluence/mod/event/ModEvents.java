package org.confluence.mod.event;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ConfluenceEntities;
import org.confluence.mod.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {
        event.put(ConfluenceEntities.BLUE_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.GREEN_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.PINK_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.CORRUPTED_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.DESERT_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.EVIL_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.ICE_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.LAVA_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.LUMINOUS_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.CRIMSON_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.PURPLE_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.RED_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.TROPIC_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.YELLOW_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(ConfluenceEntities.BLACK_SLIME.get(), Monster.createMonsterAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        Confluence.GAME_PHASE = GameRules.register("terraGamePhase", GameRules.Category.UPDATES, GameRules.IntegerValue.create(6));

        NetworkHandler.register();
    }
}
