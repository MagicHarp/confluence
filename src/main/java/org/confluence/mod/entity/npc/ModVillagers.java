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
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.bow.Bows;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.hammer.HammerAxeItem;
import org.confluence.mod.item.hammer.HammerItem;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.sword.Swords;
import org.confluence.mod.misc.ModSoundEvents;

import java.util.List;
import java.util.Random;

import static org.confluence.mod.Confluence.MODID;

public final class ModVillagers {
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MODID);

    // 村民的兴趣点
    public static final RegistryObject<PoiType> SKY_POI = POIS.register("sky", () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SKY_MILL.get().getStateDefinition().getPossibleStates()), 1, 1));
    public static final RegistryObject<PoiType> COIN_POI = POIS.register("coin", () -> new PoiType(ImmutableSet.copyOf(ModBlocks.GOLDEN_COIN_PILE.get().getStateDefinition().getPossibleStates()), 1, 1));

    // 村民的职业
    public static final RegistryObject<VillagerProfession> SKY_MILLER = PROFESSIONS.register("sky_miller", () -> new VillagerProfession("sky", holder -> holder.is(SKY_POI.getKey()), holder -> holder.is(SKY_POI.getKey()), ImmutableSet.of(Materials.FALLING_STAR.get()), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> BANKER = PROFESSIONS.register("banker", () -> new VillagerProfession("coin", holder -> holder.is(COIN_POI.getKey()), holder -> holder.is(COIN_POI.getKey()), ImmutableSet.of(ModItems.SILVER_COIN.get()), ImmutableSet.of(), ModSoundEvents.COINS.get()));

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
            trades.get(1).add(new BasicItemListing(new ItemStack(Materials.WEAVING_CLOUD_COTTON.get(), 5), new ItemStack(Items.EMERALD, 1), 7, 5, 0.05f));
            trades.get(2).add(new BasicItemListing(new ItemStack(Items.DIRT, getRandomQuantity(61, 64)),new ItemStack(Items.EMERALD, 1), 2, 10, 0.05f));
            trades.get(3).add(new BasicItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 1), new ItemStack(ModItems.CLOUDWEAVER_SEED.get(), 3), 25, 10, 0.05f));
            trades.get(3).add(new BasicItemListing(new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(ModBlocks.RAIN_CLOUD_BLOCK.get(), 1), 12, 20, 0.05f));
            trades.get(3).add(new BasicItemListing(new ItemStack(Materials.FALLING_STAR.get(), getRandomQuantity(20, 22)), new ItemStack(DecorativeBlocks.SUN_PLATE.get(), 10), 12, 20, 0.05f));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.POWDER_SNOW_BUCKET, 1), new ItemStack(Items.EMERALD, 1), 12, 30, 0.05f));
            trades.get(5).add(new BasicItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 2), new ItemStack(ModBlocks.SKY_MILL.get(), 1), 12, 30, 0.05f));
        } else if (type == VillagerProfession.FARMER) {
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(ModBlocks.FLOATING_WHEAT.get(), getRandomQuantity(4, 8)), new ItemStack(Items.EMERALD), 3, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Materials.WEAVING_CLOUD_COTTON.get(),getRandomQuantity(4, 8)), new ItemStack(Items.EMERALD), 3, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,3), new ItemStack(Foods.CLOUD_BREAD.get()), 3, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,2), new ItemStack(Foods.APRICOT.get()), 7, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,2), new ItemStack(Foods.PLUM.get()), 7, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,6), new ItemStack(Foods.HONEY_MOONCAKES.get(),1), 7, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Materials.FALLING_STAR.get(),getRandomQuantity(20, 22)), new ItemStack(Items.EMERALD,2), 7, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(ModItems.CLOUDWEAVER_SEED.get(),2), 7, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(ModItems.STELLAR_BLOSSOM_SEED.get(),2), 7, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1), new ItemStack(ModItems.FLOATING_WHEAT_SEED.get(),2), 7, 25, 0.05F));
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
        } else if (type == VillagerProfession.BUTCHER) {
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.BEEF, getRandomQuantity(6, 7)), new ItemStack(Items.EMERALD,1), 5, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.MUTTON,getRandomQuantity(6, 7)), new ItemStack(Items.EMERALD,1), 5, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.PORKCHOP,getRandomQuantity(6, 7)), new ItemStack(Items.EMERALD,1), 5, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5), new ItemStack(Foods.FISH_AND_MUSHROOM_SOUP.get()), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.RABBIT,2), new ItemStack(Items.EMERALD,1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.CHICKEN,10), new ItemStack(Items.EMERALD,2), 10, 25, 0.05F));
        } else if (type == VillagerProfession.CARTOGRAPHER) {
            //TODO 邪恶群系指南针，微光定位地图
        } else if (type == VillagerProfession.CLERIC) {
            // 这个有待思考，暂时没东西
        } else if (type == VillagerProfession.FISHERMAN) {
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Foods.DAMSEL_FISH.get(), getRandomQuantity(8, 10)), new ItemStack(Items.EMERALD,1), 5, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.PUFFERFISH, getRandomQuantity(8, 10)), new ItemStack(Items.EMERALD,1), 5, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Foods.GOLDEN_CARP.get(), 1), new ItemStack(Materials.EMERALD_COIN.get(), 1), 20, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 8), new ItemStack(FishingPoles.FIBERGLASS_FISHING_POLE.get(), 1), 10, 25, 0.05F));
        } else if (type == VillagerProfession.FLETCHER) {
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,2),new ItemStack(ModItems.FLAMING_ARROW.get(),15), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,2),new ItemStack(Bows.WOODEN_SHORT_BOW.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,12),new ItemStack(Bows.TUNGSTEN_SHORT_BOW.get(),1), 10, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,3),new ItemStack(ModItems.JESTERS_ARROW.get(),25), 10, 25, 0.05F));
        } else if (type == VillagerProfession.LEATHERWORKER) {
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.SNOW_CAPS.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.PINK_SNOW_CAPS.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.SNOW_SUITS.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.PINK_SNOW_SUITS.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.INSULATED_PANTS.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.PINK_INSULATED_PANTS.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.INSULATED_SHOES.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.PINK_INSULATED_SHOES.get(),1), 10, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Materials.EMERALD_COIN.get(),4),new ItemStack(CurioItems.HAND_WARMER.get(),1), 10, 25, 0.05F));
        } else if (type == VillagerProfession.LIBRARIAN) {
            // 这个有待思考，暂时没东西
        } else if (type == VillagerProfession.MASON) {
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1),new ItemStack(Blocks.MUD_BRICKS,9), 5, 25, 0.05F));
            trades.get(1).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,1),new ItemStack(Blocks.BRICKS,9), 5, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Blocks.STONE,20),new ItemStack(Items.EMERALD,2), 5, 25, 0.05F));
            trades.get(2).add(new SkyVillagerItemListing(new ItemStack(Items.DIRT, getRandomQuantity(61, 64)),new ItemStack(Items.EMERALD, 1), 2, 10, 0.05f));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(ModBlocks.DESERT_FOSSIL.get(),getRandomQuantity(7, 8)),new ItemStack(Items.EMERALD, 1), 5, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(ModBlocks.MARINE_GRAVEL.get(),getRandomQuantity(7, 8)),new ItemStack(Items.EMERALD, 1), 5, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(10, 12)),new ItemStack(Hammers.PLATINUM_HAMMER.get(), 1), 15, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Materials.EMERALD_COIN.get(),5),new ItemStack(ModBlocks.EXTRACTINATOR.get(), 1), 25, 25, 0.05F));
        } else if (type == VillagerProfession.SHEPHERD) {
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.SNOW_CAPS.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.PINK_SNOW_CAPS.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.SNOW_SUITS.get(),1), 10, 25, 0.05F));
            trades.get(3).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,4),new ItemStack(Armors.PINK_SNOW_SUITS.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.INSULATED_PANTS.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.PINK_INSULATED_PANTS.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.INSULATED_SHOES.get(),1), 10, 25, 0.05F));
            trades.get(4).add(new SkyVillagerItemListing(new ItemStack(Items.EMERALD,5),new ItemStack(Armors.PINK_INSULATED_SHOES.get(),1), 10, 25, 0.05F));
            trades.get(5).add(new SkyVillagerItemListing(new ItemStack(Materials.EMERALD_COIN.get(),4),new ItemStack(CurioItems.HAND_WARMER.get(),1), 10, 25, 0.05F));
        } else if (type == VillagerProfession.WEAPONSMITH) {
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD,5), new ItemStack(Swords.LEAD_SHORT_SWORD.get(), 1), 10, 25, 0.05F));
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD,7), new ItemStack(Swords.LEAD_BOARD_SWORD.get(), 1), 10, 25, 0.05F));
            trades.get(2).add(new BasicItemListing(new ItemStack(Materials.LEAD_INGOT.get(),5), new ItemStack(Items.EMERALD, 1), 10, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(8, 9)), new ItemStack(Swords.PLATINUM_SHORT_SWORD.get(), 1), 10, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(9, 11)), new ItemStack(Swords.PLATINUM_BOARD_SWORD.get(), 1), 10, 25, 0.05F));
            trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(9, 11)), new ItemStack(Bows.PLATINUM_SHORT_BOW.get(), 1), 10, 25, 0.05F));
            trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(11, 12)), new ItemStack(Bows.PLATINUM_BOW.get(), 1), 10, 25, 0.05F));
        } else if (type == VillagerProfession.TOOLSMITH) {
            trades.get(2).add(new BasicItemListing(new ItemStack(Materials.LEAD_INGOT.get(),5), new ItemStack(Items.EMERALD, 1), 10, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(9, 11)), new ItemStack(Axes.PLATINUM_AXE.get(), 1), 10, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(9, 11)), new ItemStack(Pickaxes.PLATINUM_PICKAXE.get(), 1), 10, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD,getRandomQuantity(7, 7)), new ItemStack(Hammers.PLATINUM_HAMMER.get(), 1), 10, 25, 0.05F));
        } else if (type == BANKER.get()) {
            trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 1), new ItemStack(Blocks.CHEST, 1), 3, 25, 0.05F));
            trades.get(1).add(new BasicItemListing(new ItemStack(ModItems.SILVER_COIN.get(),2), new ItemStack(Items.EMERALD, 1), 3, 15, 0.05F));
            trades.get(2).add(new BasicItemListing(new ItemStack(ModItems.GOLDEN_COIN.get(),3), new ItemStack(Materials.EMERALD_COIN.get(), 1), 3, 15, 0.05F));
            trades.get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD,20), new ItemStack(Materials.EMERALD_COIN.get(), 1),3, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 1), new ItemStack(Items.EMERALD,20),3, 25, 0.05F));
            trades.get(4).add(new BasicItemListing(new ItemStack(Materials.EMERALD_COIN.get(), 1), new ItemStack(ModItems.GOLDEN_COIN.get(),3),3, 25, 0.05F));
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
