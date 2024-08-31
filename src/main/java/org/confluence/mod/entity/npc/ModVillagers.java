package org.confluence.mod.entity.npc;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.common.Materials;

import static org.confluence.mod.Confluence.MODID;

public final class ModVillagers {
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MODID);

    // 村民的兴趣点
    public static final RegistryObject<PoiType> SKY_POI = POIS.register("sky", () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SKY_MILL.get().getStateDefinition().getPossibleStates()), 1, 1));

    // 村民的职业
    public static final RegistryObject<VillagerProfession> SKY_MILLER = PROFESSIONS.register("sky_miller", () -> new VillagerProfession("sky", holder -> holder.is(SKY_POI.getKey()), holder -> holder.is(SKY_POI.getKey()), ImmutableSet.of(Materials.FALLING_STAR.get()), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));

    // 村民的群系
    public static final RegistryObject<VillagerType> SKY_TYPE = RegistryObject.create(new ResourceLocation(MODID, "sky"), BuiltInRegistries.VILLAGER_TYPE.key(), MODID); // 天域村民

    public static void registerTypes(RegisterEvent event) {
        event.register(BuiltInRegistries.VILLAGER_TYPE.key(), helper -> {
            helper.register(SKY_TYPE.getId(), new VillagerType("sky"));
        });
    }

    public static void villagerTrades(VillagerTradesEvent event) {
        if (event.getType() == SKY_MILLER.get()) { // todo 交易

        }
    }

    public static void setVillagerType(Villager villager) {
        if (villager.position().y >= 240.0) {
            villager.setVariant(SKY_TYPE.get());
        }
    }

    public static void register(IEventBus bus) {
        POIS.register(bus);
        PROFESSIONS.register(bus);
        bus.addListener(ModVillagers::registerTypes);
        MinecraftForge.EVENT_BUS.addListener(ModVillagers::villagerTrades);
    }
}
