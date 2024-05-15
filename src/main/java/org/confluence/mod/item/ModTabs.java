package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.DecorativeBlocks;
import org.confluence.mod.block.LogBlocks;
import org.confluence.mod.block.Ores;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.common.Gels;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.common.SpawnEggs;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.fishing.FishingPoles;
import org.confluence.mod.item.fishing.QuestedFishes;
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.hammer.HammerAxes;
import org.confluence.mod.item.hammer.Hammers;
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
                output.accept(BIG_ANOTHER_AMETHYST_BLOCK.get());
                output.accept(ANOTHER_POLISHED_GRANITE.get());
                output.accept(POLISHED_MARBLE.get());

            })
            .build());
    // 自然方块
    public static final RegistryObject<CreativeModeTab> NATURAL_BLOCKS = TABS.register("natural_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.NATURE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.natural_blocks"))
            .displayItems((parameters, output) -> {
                for (Ores ores : Ores.values()) output.accept(ores.get());

                output.accept(PALM_LOG_BLOCKS.LOG.get());
                output.accept(PALM_LOG_BLOCKS.LEAVES.get());
                output.accept(CORRUPT_GRASS_BLOCK.get());
                output.accept(EBONY_SAND.get());
                output.accept(EBONY_STONE.get());
                output.accept(EBONY_LOG_BLOCKS.LOG.get());
                output.accept(EBONY_LOG_BLOCKS.LEAVES.get());
                output.accept(ANOTHER_CRIMSON_GRASS_BLOCK.get());
                output.accept(ANOTHER_CRIMSON_SAND.get());
                output.accept(ANOTHER_CRIMSON_STONE.get());
                output.accept(SHADOW_LOG_BLOCKS.LOG.get());
                output.accept(SHADOW_LOG_BLOCKS.LEAVES.get());
                output.accept(HALLOW_GRASS_BLOCK.get());
                output.accept(PEARL_SAND.get());
                output.accept(PEARL_STONE.get());
                output.accept(PEARL_LOG_BLOCKS.LOG.get());
                output.accept(PEARL_LOG_BLOCKS.LEAVES.get());
                output.accept(ASH_BLOCK.get());
                output.accept(ASH_LOG_BLOCKS.LOG.get());
                output.accept(ASH_LOG_BLOCKS.LEAVES.get());

                output.accept(THIN_ICE_BLOCK.get());
                output.accept(CRISPY_HONEY_BLOCK.get());

                output.accept(FOREST_JARS.get());
                output.accept(SNOW_JARS.get());
                output.accept(DESERT_JARS.get());
                output.accept(CORRUPT_JARS.get());
            })
            .build());
    // 材料
    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.ITEM_ICON.get()))
            .title(Component.translatable("creativetab.confluence.materials"))
            .displayItems((parameters, output) -> {
                for (Materials materials : Materials.values()) output.accept(materials.get());
                for (Gels gels : Gels.values()) output.accept(gels.get());
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
                output.accept(ModItems.ALPHA.get());
                for (SpawnEggs spawnEggs : SpawnEggs.values()) output.accept(spawnEggs.get());
                for (QuestedFishes questedFishes : QuestedFishes.values()) output.accept(questedFishes.get());
                output.accept(ModItems.CLAM.get());
                output.accept(ECHO_BLOCK.get());
                output.accept(ACTUATORS.get());
                output.accept(ALTAR_BLOCK.get());
            })
            .build());
    // 工具
    public static final RegistryObject<CreativeModeTab> TOOLS = TABS.register("tools",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.TOOLS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.tools"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.MAGIC_MIRROR.get());
                output.accept(ModItems.ICE_MIRROR.get());
                output.accept(ModItems.HONEY_BUCKET.get());
                for (Pickaxes pickaxes : Pickaxes.values()) output.accept(pickaxes.get());
                for (Axes axes : Axes.values()) output.accept(axes.get());
                for (Hammers hammers : Hammers.values()) output.accept(hammers.get());
                for (HammerAxes hammerAxes : HammerAxes.values()) output.accept(hammerAxes.get());
                for (FishingPoles fishingPoles : FishingPoles.values()) output.accept(fishingPoles.get());
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
            .title(Component.translatable("creativetab.confluence.shooters"))
            .displayItems((parameters, output) -> {

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
    public static final RegistryObject<CreativeModeTab> JEWELRY = TABS.register("curios",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.ACCESSORIES_ICON.get()))
            .title(Component.translatable("creativetab.confluence.curios"))
            .displayItems((parameters, output) -> {
                for (CurioItems curioItems : CurioItems.values()) output.accept(curioItems.get());
            })
            .build());
}