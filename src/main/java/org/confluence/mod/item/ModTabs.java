package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.*;
import org.confluence.mod.block.functional.BoulderBlock;
import org.confluence.mod.block.functional.DeathChestBlock;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.block.natural.Ores;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.bow.Bows;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.common.SpawnEggs;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.Baits;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.item.fishing.QuestedFishes;
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.gun.AmmoItems;
import org.confluence.mod.item.gun.GunItems;
import org.confluence.mod.item.hammer.HammerAxes;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.item.mana.ManaWeapons;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.sword.Swords;

import static org.confluence.mod.block.ModBlocks.*;

@SuppressWarnings("unused")
public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);
    // 建筑方块
    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = TABS.register("building_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.BLOCKS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.building_blocks"))
            .displayItems((parameters, output) -> {
                LogBlocks.acceptBuilding(output);
                for (DecorativeBlocks blocks : DecorativeBlocks.values()) output.accept(blocks.get());
                output.accept(BIG_RUBY_BLOCK.get());
                output.accept(BIG_AMBER_BLOCK.get());
                output.accept(BIG_TOPAZ_BLOCK.get());
                output.accept(BIG_SAPPHIRE_BLOCK.get());
                output.accept(BIG_TR_AMETHYST_BLOCK.get());

                output.accept(RUBY_CHAIN.get());
                output.accept(AMBER_CHAIN.get());
                output.accept(TOPAZ_CHAIN.get());
                output.accept(EMERALD_CHAIN.get());
                output.accept(SAPPHIRE_CHAIN.get());
                output.accept(DIAMOND_CHAIN.get());
                output.accept(AMETHYST_CHAIN.get());
                output.accept(SILK_CHAIN.get());
                output.accept(BONE_CHAIN.get());

                output.accept(WHITE_PLASTIC_CHAIR_BLOCK.get());

                // 雕像
                output.accept(STATUE_A.get());
                for (Torches torches : Torches.values()) output.accept(torches.item.get());
            })
            .build());
    // 自然方块
    public static final RegistryObject<CreativeModeTab> NATURAL_BLOCKS = TABS.register("natural_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.NATURE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.natural_blocks"))
            .displayItems((parameters, output) -> {
                for (Ores ores : Ores.values()) output.accept(ores.get());
                output.accept(SANCTIFICATION_REDSTONE_ORE.get());
                output.accept(CORRUPTION_REDSTONE_ORE.get());
                output.accept(FLESHIFICATION_REDSTONE_ORE.get());
                output.accept(LIFE_CRYSTAL_BLOCK.get());
                output.accept(SWORD_IN_STONE.get());
                output.accept(STONY_LOGS.get());
                output.accept(RUBY_BRANCHES.get());
                output.accept(AMBER_BRANCHES.get());
                output.accept(TOPAZ_BRANCHES.get());
                output.accept(EMERALD_BRANCHES.get());
                output.accept(DIAMOND_BRANCHES.get());
                output.accept(SAPPHIRE_BRANCHES.get());
                output.accept(TR_AMETHYST_BRANCHES.get());
                output.accept(ASH_BRANCHES.get());
                output.accept(AETHERIUM_BLOCK.get());
                output.accept(DARK_AETHERIUM_BLOCK.get());


                output.accept(PALM_LOG_BLOCKS.LOG.get());
                output.accept(PALM_LOG_BLOCKS.LEAVES.get());
                output.accept(HARDENED_SAND_BLOCK.get());
                output.accept(RED_HARDENED_SAND_BLOCK.get());
                output.accept(DIATOMACEOUS.get());
                output.accept(DESERT_FOSSIL.get());
                output.accept(SLUSH.get());
                output.accept(MARINE_GRAVEL.get());

                output.accept(CORRUPT_GRASS_BLOCK.get());
                output.accept(EBONY_SAND.get());
                output.accept(EBONY_STONE.get());
                output.accept(EBONY_COBBLESTONE.get());
                output.accept(EBONY_HARDENED_SAND_BLOCK.get());
                output.accept(EBONY_SANDSTONE.get());
                output.accept(PURPLE_ICE.get());
                output.accept(PURPLE_PACKED_ICE.get());
                output.accept(EBONY_LOG_BLOCKS.LOG.get());
                output.accept(EBONY_LOG_BLOCKS.LEAVES.get());

                output.accept(TR_CRIMSON_GRASS_BLOCK.get());
                output.accept(TR_CRIMSON_SAND.get());
                output.accept(TR_CRIMSON_STONE.get());
                output.accept(TR_CRIMSON_COBBLESTONE.get());
                output.accept(TR_CRIMSON_HARDENED_SAND_BLOCK.get());
                output.accept(TR_CRIMSON_SANDSTONE.get());
                output.accept(RED_ICE.get());
                output.accept(RED_PACKED_ICE.get());
                output.accept(SHADOW_LOG_BLOCKS.LOG.get());
                output.accept(SHADOW_LOG_BLOCKS.LEAVES.get());
                output.accept(OCULAR_BLOCKS.get());

                output.accept(HALLOW_GRASS_BLOCK.get());
                output.accept(PEARL_SAND.get());
                output.accept(PEARL_STONE.get());
                output.accept(PEARL_COBBLESTONE.get());
                output.accept(PEARL_HARDENED_SAND_BLOCK.get());
                output.accept(PEARL_SANDSTONE.get());
                output.accept(PINK_ICE.get());
                output.accept(PINK_PACKED_ICE.get());
                output.accept(PEARL_LOG_BLOCKS.LOG.get());
                output.accept(PEARL_LOG_BLOCKS.LEAVES.get());
                output.accept(ASH_BLOCK.get());
                output.accept(ASH_GRASS_BLOCK.get());
                output.accept(ASH_LOG_BLOCKS.LOG.get());
                output.accept(ASH_LOG_BLOCKS.LEAVES.get());
                // 树苗
                output.accept(SHADOW_SAPLING.get());
                output.accept(EBONY_SAPLING.get());
                output.accept(PALM_SAPLING.get());
                output.accept(PEARL_SAPLING.get());
                output.accept(RUBY_SAPLING.get());
                output.accept(AMBER_SAPLING.get());
                output.accept(TOPAZ_SAPLING.get());
                output.accept(EMERALD_SAPLING.get());
                output.accept(DIAMOND_SAPLING.get());
                output.accept(SAPPHIRE_SAPLING.get());
                output.accept(TR_AMETHYST_SAPLING.get());
                output.accept(ASH_SAPLING.get());
                output.accept(MUSHROOM_GRASS_BLOCK.get());
                output.accept(TR_POLISHED_GRANITE.get());
                output.accept(POLISHED_MARBLE.get());

                output.accept(CLOUD_BLOCK.get());
                output.accept(RAIN_CLOUD_BLOCK.get());
                output.accept(SNOW_CLOUD_BLOCK.get());
                output.accept(EVAPORATIVE_CLOUD_BLOCK.get());

                output.accept(JUNGLE_HIVE_BLOCK.get());
                output.accept(LOOSE_HONEY_BLOCK.get());
                output.accept(THIN_HONEY_BLOCK.get());

                output.accept(CRACKED_BLUE_BRICK.get());
                output.accept(CRACKED_GREEN_BRICK.get());
                output.accept(CRACKED_PINK_BRICK.get());
                // 草药 种子
                output.accept(ModItems.WATERLEAF.get());
                output.accept(ModItems.MOONSHINE_GRASS.get());
                output.accept(ModItems.SHINE_ROOT.get());
                output.accept(ModItems.SHIVERINGTHORNS.get());
                output.accept(ModItems.SUNFLOWERS.get());
                output.accept(ModItems.DEATHWEED.get());
                output.accept(ModItems.FLAMEFLOWERS.get());

                output.accept(ModBlocks.STELLAR_BLOSSOM.get());
                output.accept(CLOUDWEAVER.get());
                output.accept(FLOATING_WHEAT.get());

                output.accept(ModItems.WATERLEAF_SEED.get());
                output.accept(ModItems.MOONSHINE_GRASS_SEED.get());
                output.accept(ModItems.SHINE_ROOT_SEED.get());
                output.accept(ModItems.SHIVERINGTHORNS_SEED.get());
                output.accept(ModItems.SUNFLOWERS_SEED.get());
                output.accept(ModItems.DEATHWEED_SEED.get());
                output.accept(ModItems.FLAMEFLOWERS_SEED.get());

                output.accept(ModItems.STELLAR_BLOSSOM_SEED.get());
                output.accept(ModItems.CLOUDWEAVER_SEED.get());
                output.accept(ModItems.FLOATING_WHEAT_SEED.get());

                // 蘑菇 草
                output.accept(ModItems.TR_CRIMSON_MUSHROOM.get());
                output.accept(ModItems.EBONY_MUSHROOM.get());
                output.accept(ModItems.GLOWING_MUSHROOM.get());
                output.accept(ModItems.LIFE_MUSHROOM.get());
                output.accept(CORRUPT_GRASS.get());
                output.accept(ASH_GRASS.get());
                output.accept(TR_CRIMSON_GRASS.get());
                output.accept(HALLOW_GRASS.get());

                output.accept(THIN_ICE_BLOCK.get());
                output.accept(CRISPY_HONEY_BLOCK.get());
                output.accept(DEMON_ALTAR.get());
                output.accept(CRIMSON_ALTAR.get());
                // 荆棘
                output.accept(CRIMSON_THORN.get());
                output.accept(CORRUPTION_THORN.get());
                output.accept(JUNGLE_THORN.get());
                output.accept(PLANTERA_THORN.get());

                output.accept(JUNGLE_ROSE.get());

                // 片
                output.accept(EBONY_SAND_LAYER_BLOCK.get());
                output.accept(PEARL_SAND_LAYER_BLOCK.get());
                output.accept(TR_CRIMSON_SAND_LAYER_BLOCK.get());
                output.accept(SAND_LAYER_BLOCK.get());
                output.accept(RED_SAND_LAYER_BLOCK.get());

                for (Pots pots : Pots.values()) output.accept(pots.get());
            })
            .build());
    // 材料
    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.ITEM_ICON.get()))
            .title(Component.translatable("creativetab.confluence.materials"))
            .displayItems((parameters, output) -> {
                for (Materials materials : Materials.values()) output.accept(materials.get());
                output.accept(ModItems.WHOOPIE_CUSHION.get());
                output.accept(ModItems.JUNGLE_SPORE.get());
            })
            .build());
    // 杂项
    public static final RegistryObject<CreativeModeTab> CREATIVES = TABS.register("misc",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.PRECIOUS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.misc"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.COPPER_COIN.get());
                output.accept(ModItems.SILVER_COIN.get());
                output.accept(ModItems.GOLDEN_COIN.get());
                output.accept(ModItems.PLATINUM_COIN.get());
                output.accept(ModItems.EXPERT_TEST_ITEM.get());
                output.accept(ModItems.MASTER_TEST_ITEM.get());
                output.accept(ModItems.STAR.get());
                output.accept(ModItems.SOUL_CAKE.get());
                output.accept(ModItems.SUGAR_PLUM.get());
                output.accept(ModItems.HEART.get());
                output.accept(ModItems.CANDY_APPLE.get());
                output.accept(ModItems.CANDY_CANE.get());
                output.accept(ModItems.ALPHA.get());
                output.accept(ModItems.VITAL_CRYSTAL.get());
                output.accept(ModItems.ARCANE_CRYSTAL.get());
                output.accept(ModItems.AEGIS_APPLE.get());
                output.accept(ModItems.AMBROSIA.get());
                output.accept(ModItems.GUMMY_WORM.get());
                output.accept(ModItems.GALAXY_PEARL.get());
                output.accept(ModItems.CLAM.get());
                output.accept(ModItems.HERB_BAG.get());
                output.accept(ModItems.CAN_OF_WORMS.get());
                output.accept(ModItems.CHRISTMAS_GIFT.get());
                output.accept(ModItems.RED_ENVELOPE.get());

                output.accept(ModItems.GOLDEN_KEY.get());
                output.accept(ModItems.SHADOW_KEY.get());

                output.accept(ModItems.SUSPICIOUS_LOOKING_EYE.get());
                output.accept(ModItems.SLIME_CROWN.get());

                for (BaseChestBlock.Variant variant : BaseChestBlock.Variant.values()) {
                    output.accept(BaseChestBlock.setData(new ItemStack(BASE_CHEST_BLOCK.get().asItem()), variant));
                    if (variant.getSerializedName().startsWith("unlocked")) { // 只放解锁的
                        output.accept(DeathChestBlock.setData(new ItemStack(DEATH_CHEST_BLOCK.get().asItem()), variant));
                    }
                }

                for (SpawnEggs spawnEggs : SpawnEggs.values()) output.accept(spawnEggs.get());
                for (QuestedFishes questedFishes : QuestedFishes.values()) output.accept(questedFishes.get());
                for (Boxes boxes : Boxes.values()) output.accept(boxes.get());
                for (Baits baits : Baits.values()) output.accept(baits.get());

                output.accept(ModItems.GOLD_CROWN.get());
                output.accept(ModItems.PLATINUM_CROWN.get());
            })
            .build());
    // 工具
    public static final RegistryObject<CreativeModeTab> TOOLS = TABS.register("tools",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.TOOLS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.tools"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.MAGIC_MIRROR.get());
                output.accept(ModItems.ICE_MIRROR.get());
                output.accept(ModItems.MAGIC_CONCH.get());
                output.accept(ModItems.DEMON_CONCH.get());
                output.accept(ModItems.CELL_PHONE.get());
                output.accept(ModItems.HONEY_BUCKET.get());
                output.accept(ModItems.BOTTOMLESS_WATER_BUCKET.get());
                output.accept(ModItems.BOTTOMLESS_LAVA_BUCKET.get());
                output.accept(ModItems.BOTTOMLESS_HONEY_BUCKET.get());
                output.accept(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get());
                output.accept(ModItems.BOMB.get());
                output.accept(ModItems.STICKY_BOMB.get());
                output.accept(ModItems.BOUNCY_BOMB.get());
                output.accept(ModItems.SCARAB_BOMB.get());
                output.accept(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get());
                output.accept(ModItems.RED_WRENCH.get());
                output.accept(ModItems.GREEN_WRENCH.get());
                output.accept(ModItems.BLUE_WRENCH.get());
                output.accept(ModItems.YELLOW_WRENCH.get());
                output.accept(ModItems.WIRE_CUTTER.get());
                for (Pickaxes pickaxes : Pickaxes.values()) output.accept(pickaxes.get());
                for (Axes axes : Axes.values()) output.accept(axes.get());
                for (Hammers hammers : Hammers.values()) output.accept(hammers.get());
                for (HammerAxes hammerAxes : HammerAxes.values()) output.accept(hammerAxes.get());
                for (FishingPoles fishingPoles : FishingPoles.values()) output.accept(fishingPoles.get());
                for (Hooks hooks : Hooks.values()) output.accept(hooks.get());
            })
            .build());

    // 战士武器
    public static final RegistryObject<CreativeModeTab> WARRIORS = TABS.register("warriors",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.MELEE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.warriors"))
            .displayItems((parameters, output) -> {
                for (Swords swords : Swords.values()) output.accept(swords.get());
            })
            .build());
    // 射手武器
    public static final RegistryObject<CreativeModeTab> SHOOTERS = TABS.register("shooters",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.REMOTE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.rangers"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.THROWING_KNIVES.get());
                output.accept(ModItems.SHURIKEN.get());
                output.accept(Bows.WOODEN_SHORT_BOW.get());
                output.accept(Bows.COPPER_SHORT_BOW.get());
                output.accept(Bows.TIN_SHORT_BOW.get());
                output.accept(Bows.IRON_SHORT_BOW.get());
                output.accept(Bows.LEAD_SHORT_BOW.get());
                output.accept(Bows.SILVER_SHORT_BOW.get());
                output.accept(Bows.TUNGSTEN_SHORT_BOW.get());
                output.accept(Bows.GOLDEN_SHORT_BOW.get());
                output.accept(Bows.PLATINUM_SHORT_BOW.get());
                output.accept(Bows.COPPER_BOW.get());
                output.accept(Bows.TIN_BOW.get());
                output.accept(Bows.IRON_BOW.get());
                output.accept(Bows.LEAD_BOW.get());
                output.accept(Bows.SILVER_BOW.get());
                output.accept(Bows.TUNGSTEN_BOW.get());
                output.accept(Bows.GOLDEN_BOW.get());
                output.accept(Bows.PLATINUM_BOW.get());
                output.accept(GunItems.HANDGUN.get());
                output.accept(GunItems.MUSKET.get());
                output.accept(GunItems.BOOMSTICK.get());
                output.accept(GunItems.FLINTLOCKPISTOL.get());
                output.accept(GunItems.FLAREGUN.get());
                output.accept(GunItems.MINISHARK.get());
                output.accept(GunItems.SHOTGUN.get());
                output.accept(GunItems.TACTICALSHOTGUN.get());
                output.accept(GunItems.UZI.get());
                output.accept(GunItems.REVOLVER.get());
                output.accept(GunItems.THEUNDERTAKER.get());
                output.accept(GunItems.PHOENIXBLASTER.get());
                output.accept(GunItems.ONYXBLASTER.get());
                output.accept(GunItems.SNIPERRIFLE.get());
                output.accept(GunItems.SLIMEGUN.get());
                output.accept(GunItems.SNOWBALLCANNON.get());
                output.accept(GunItems.STARCANNON.get());
                output.accept(GunItems.BLOWPIPE.get());
                output.accept(GunItems.BLOWGUN.get());
                for (AmmoItems ammoItems : AmmoItems.values()) output.accept(ammoItems.get());
            })
            .build());
    // 法师武器
    public static final RegistryObject<CreativeModeTab> MAGES = TABS.register("mages",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.MAGIC_ICON.get()))
            .title(Component.translatable("creativetab.confluence.mages"))
            .displayItems((parameters, output) -> {
                for (ManaWeapons manaWeapons : ManaWeapons.values()) output.accept(manaWeapons.get());
            })
            .build());
    // 召唤武器
    public static final RegistryObject<CreativeModeTab> SUMMONERS = TABS.register("summoners",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.SUMMON_ICON.get()))
            .title(Component.translatable("creativetab.confluence.summoners"))
            .displayItems((parameters, output) -> {
            })
            .build());
    // 食物与药水
    public static final RegistryObject<CreativeModeTab> FOOD_AND_POTIONS = TABS.register("food_and_potions",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.POTION_ICON.get()))
            .title(Component.translatable("creativetab.confluence.food_and_potions"))
            .displayItems((parameters, output) -> {
                for (Foods foods : Foods.values()) output.accept(foods.get());
                for (TerraPotions terraPotions : TerraPotions.values()) output.accept(terraPotions.get());
            })
            .build());
    // 盔甲
    public static final RegistryObject<CreativeModeTab> ARMORS = TABS.register("armors",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.ARMOR_ICON.get()))
            .title(Component.translatable("creativetab.confluence.armors"))
            .displayItems((parameters, output) -> {
                for (Armors armors : Armors.values()) output.accept(armors.get());
            })
            .build());
    // 饰品
    public static final RegistryObject<CreativeModeTab> JEWELRY = TABS.register("accessories",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.ACCESSORIES_ICON.get()))
            .title(Component.translatable("creativetab.confluence.accessories"))
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.WORKSHOP.get());
                output.accept(ModItems.DEMON_HEART.get());
                for (CurioItems curioItems : CurioItems.values()) output.accept(curioItems.get());
            })
            .build());
    // 器械
    public static final RegistryObject<CreativeModeTab> MECHANICAL = TABS.register("mechanical",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.MECHANICAL_ICON.get()))
            .title(Component.translatable("creativetab.confluence.mechanical"))
            .displayItems((parameters, output) -> {
                output.accept(CurioItems.MECHANICAL_LENS.get());
                output.accept(CurioItems.SPECTRE_GOGGLES.get());
                output.accept(ANDESITE_CASING.get());
                output.accept(ModItems.RED_WRENCH.get());
                output.accept(ModItems.GREEN_WRENCH.get());
                output.accept(ModItems.BLUE_WRENCH.get());
                output.accept(ModItems.YELLOW_WRENCH.get());
                output.accept(ModItems.WIRE_CUTTER.get());
                output.accept(CurioItems.SPECTRE_GOGGLES.get());
                output.accept(ECHO_BLOCK.get());
                for (BoulderBlock.Variant variant : BoulderBlock.Variant.values()) output.accept(variant.get());
                output.accept(INSTANTANEOUS_EXPLOSION_TNT.get());
                output.accept(SWITCH.get());
                output.accept(SIGNAL_ADAPTER.get());
                output.accept(DART_TRAP.get());
                output.accept(TIMERS_BLOCK_1_1.get());
                output.accept(TIMERS_BLOCK_3_1.get());
                output.accept(TIMERS_BLOCK_5_1.get());
                output.accept(TIMERS_BLOCK_1_2.get());
                output.accept(TIMERS_BLOCK_1_4.get());
                output.accept(GEYSER_BLOCK.get());
                output.accept(PLAYER_PRESSURE_PLATE.get());
                output.accept(STONE_PRESSURE_PLATE.get());
                output.accept(DEEPSLATE_PRESSURE_PLATE.get());
                output.accept(EXTRACTINATOR.get());
                output.accept(DEMON_ALTAR.get());
                output.accept(CRIMSON_ALTAR.get());
                output.accept(SKY_MILL.get());
                output.accept(WORKSHOP.get());
            })
            .build());
}