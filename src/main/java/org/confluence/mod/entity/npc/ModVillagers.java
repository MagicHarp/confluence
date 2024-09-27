package org.confluence.mod.entity.npc;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.DecorativeBlocks;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.food.Foods;

import java.util.List;
import java.util.Random;

import static org.confluence.mod.Confluence.MODID;

public final class ModVillagers {
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MODID);

    // 村民的兴趣点
    public static final RegistryObject<PoiType> SKY_POI = POIS.register("sky", () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SKY_MILL.get().getStateDefinition().getPossibleStates()), 1, 1));

    // 村民的职业
    public static final RegistryObject<VillagerProfession> SKY_MILLER = PROFESSIONS.register("sky_miller", () -> new VillagerProfession("sky", holder -> holder.is(SKY_POI.getKey()), holder -> holder.is(SKY_POI.getKey()), ImmutableSet.of(Materials.FALLING_STAR.get()), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));

    // 村民的群系
    public static final RegistryObject<VillagerType> SKY_TYPE = RegistryObject.create(Confluence.asResource("sky"), BuiltInRegistries.VILLAGER_TYPE.key(), MODID); // 天域村民

    public static void registerTypes(RegisterEvent event) {
        event.register(BuiltInRegistries.VILLAGER_TYPE.key(), helper -> {
            helper.register(SKY_TYPE.getId(), new VillagerType("sky"));
        });
    }

    public static void villagerTrades(VillagerTradesEvent event) {
        VillagerProfession type = event.getType();
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        Random random = new Random();
        if (type == SKY_MILLER.get()) {
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD), new ItemStack(ModBlocks.CLOUD_BLOCK.get(), getRandomQuantity(2, 3)),12, 5, 0.05f));
            trades.get(1).add(new BasicItemListing(new ItemStack(Materials.WEAVING_CLOUD_COTTON.get(), 5), new ItemStack(Items.EMERALD, 1), 12, 5, 0.05f));
            trades.get(2).add(new BasicItemListing(new ItemStack(Items.DIRT, getRandomQuantity(61, 64)),new ItemStack(Items.EMERALD, 1), 2, 10, 0.05f));
            trades.get(3).add(new BasicItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 1), new ItemStack(ModItems.CLOUDWEAVER_SEED.get(), 3), 25, 10, 0.05f));
            trades.get(3).add(new BasicItemListing(new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(ModBlocks.RAIN_CLOUD_BLOCK.get(), 1), 12, 20, 0.05f));
            trades.get(3).add(new BasicItemListing(new ItemStack(Materials.FALLING_STAR.get(), getRandomQuantity(20, 22)), new ItemStack(DecorativeBlocks.SUN_PLATE.get(), 10), 12, 20, 0.05f));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.POWDER_SNOW_BUCKET, 1), new ItemStack(Items.EMERALD, 1), 12, 30, 0.05f));
            trades.get(5).add(new BasicItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 2), new ItemStack(ModBlocks.SKY_MILL.get(), 1), 12, 30, 0.05f));
        } else if (type == VillagerProfession.FARMER) {
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(ModBlocks.FLOATING_WHEAT.get(), getRandomQuantity(4, 8)), new ItemStack(Items.EMERALD), 5, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Materials.WEAVING_CLOUD_COTTON.get(),getRandomQuantity(4, 8)), new ItemStack(Items.EMERALD), 5, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,3), new ItemStack(Foods.CLOUD_BREAD.get()), 5, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,2), new ItemStack(Foods.APRICOT.get()), 10, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,2), new ItemStack(Foods.PLUM.get()), 10, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,6), new ItemStack(Foods.HONEY_MOONCAKES.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.FALLING_STAR.get(),getRandomQuantity(20, 22)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(ModItems.CLOUDWEAVER_SEED.get(),2), 10, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(ModItems.STELLAR_BLOSSOM_SEED.get(),2), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(ModItems.FLOATING_WHEAT_SEED.get(),2), 10, 25, 0.05F));
        } else if (type == VillagerProfession.ARMORER) {
            // 故意替换原版的一部分(铅和铂金部分）
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, getRandomQuantity(6, 10)), new ItemStack(Armors.LEAD_HELMET.get()), 5, 25, 0.05F));
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(10, 14)), new ItemStack(Armors.LEAD_CHESTPLATE.get()), 5, 25, 0.05F));
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(8, 12)), new ItemStack(Armors.LEAD_LEGGINGS.get()), 5, 25, 0.05F));
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(6, 10)),  new ItemStack(Armors.LEAD_BOOTS.get()),5, 25, 0.05F));
            trades.get(2).add(new BasicItemListing(new ItemStack(Materials.LEAD_INGOT.get(),5), new ItemStack(Items.EMERALD, 1), 10, 25, 0.05F));
            trades.get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(Items.COPPER_INGOT,8), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.RUBY.get(),getRandomQuantity(1, 2)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.AMBER.get(),getRandomQuantity(1, 2)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.TOPAZ.get(),getRandomQuantity(1, 2)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.TR_EMERALD.get(),getRandomQuantity(1, 2)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.TR_AMETHYST.get(),getRandomQuantity(1, 2)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.SAPPHIRE.get(),getRandomQuantity(1, 2)), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(7, 9)), new ItemStack(Materials.PLATINUM_INGOT.get(),1), 10, 25, 0.05F));
            trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, getRandomQuantity(19, 20)), new ItemStack(Armors.PLATINUM_HELMET.get()), 10, 25, 0.05F));
            trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(22, 23)), new ItemStack(Armors.PLATINUM_CHESTPLATE.get()), 10, 25, 0.05F));
            trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(20, 21)), new ItemStack(Armors.PLATINUM_LEGGINGS.get()), 10, 25, 0.05F));
            trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(18, 19)),  new ItemStack(Armors.PLATINUM_BOOTS.get()),10, 25, 0.05F));
        }
    }
    private static int getRandomQuantity(int min, int max) {
        return min + new Random().nextInt(max - min + 1); // Generates a number between min and max inclusive
    }

    public static void setVillagerType(Villager villager) {
        if (villager.position().y >= 240.0) {
            villager.setVariant(SKY_TYPE.get());
        }
    }

    public static void register(IEventBus bus) {
        POIS.register(bus);
        PROFESSIONS.register(bus);
        MinecraftForge.EVENT_BUS.addListener(ModVillagers::villagerTrades);
    }
}
