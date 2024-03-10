package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.BaseBlock;

import static org.confluence.mod.block.ConfluenceBlocks.*;
import static org.confluence.mod.item.ConfluenceItems.Materials;

public class ConfluenceTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);
    //建筑方块
    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = TABS.register("building_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.BLOCKS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.building_blocks"))
            .displayItems((parameters, output) -> {
                output.accept(ANOTHER_COPPER_BRICKS.get());
                output.accept(ANOTHER_COPPER_PLATE.get());
                output.accept(TIN_BRICKS.get());
                output.accept(TIN_PLATE.get());
                output.accept(LEAD_BRICKS.get());
                output.accept(ANOTHER_IRON_BRICKS.get());
                output.accept(ANOTHER_GOLD_BRICKS.get());
                output.accept(SILVER_BRICKS.get());
                output.accept(WOLFRAM_BRICKS.get());
                output.accept(PLATINUM_BRICKS.get());
                output.accept(EVIL_ORE_BRICKS.get());
                output.accept(EVIL_ROCK_BRICKS.get());
                output.accept(ANOTHER_STONE_BRICKS.get());
                output.accept(METEORITE_BRICKS.get());
                output.accept(BIG_RUBY_BLOCK.get());
                output.accept(BIG_AMBER_BLOCK.get());
                output.accept(BIG_TOPAZ_BLOCK.get());
                output.accept(BIG_ANOTHER_EMERALD_BLOCK.get());
                output.accept(BIG_SAPPHIRE_BLOCK.get());
                output.accept(BIG_ANOTHER_AMETHYST_BLOCK.get());
                output.accept(ANOTHER_CRIMSON_ORE_BRICKS.get());
                output.accept(ANOTHER_CRIMSON_ROCK_BRICKS.get());
                output.accept(ANOTHER_CRIMSON_STONE_BRICKS.get());
                output.accept(PEARL_ROCK_BRICKS.get());
                output.accept(EVIL_ORE_BRICKS.get());
                output.accept(SNOW_BRICKS.get());
                output.accept(ICE_BRICKS.get());
                output.accept(ANOTHER_POLISHED_GRANITE.get());
                output.accept(POLISHED_MARBLE.get());
                output.accept(BLUE_GEL_BLOCK.get());
                output.accept(ICED_GEL_BLOCK.get());
                output.accept(PINK_GEL_BLOCK.get());
                output.accept(SUN_PLATE.get());
                output.accept(ANOTHER_LAVA_BEAM.get());
                output.accept(ANOTHER_LAVA_BRICKS.get());
                output.accept(ANOTHER_OBSIDIAN_BEAM.get());
                output.accept(ANOTHER_OBSIDIAN_BRICKS.get());
                output.accept(ANOTHER_OBSIDIAN_PLATE.get());
                output.accept(ANOTHER_OBSIDIAN_SMALL_BRICKS.get());
                output.accept(ANOTHER_SMOOTH_OBSIDIAN_SLAB.get());
                output.accept(ANOTHER_LAVA_BRICKS.get());
                output.accept(ANOTHER_SMOOTH_OBSIDIAN.get());
                output.accept(CHISELED_ANOTHER_OBSIDIAN_BRICKS.get());
                output.accept(ANOTHER_OAK_BEAM.get());
            })
            .build());
    //自然方块
    public static final RegistryObject<CreativeModeTab> NATURAL_BLOCKS = TABS.register("natural_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.NATURE_ICON.get()))
            .title(Component.translatable("creativetab.confluence.natural_blocks"))
            .displayItems((parameters, output) -> {
                for (Ores ores : Ores.values()) {
                    output.accept(ores.get());
                }
            })
            .build());
    //材料
    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.ITEM_ICON.get()))
            .title(Component.translatable("creativetab.confluence.materials"))
            .displayItems((parameters, output) -> {
                for (Materials materials : Materials.values()) {
                    output.accept(materials.get());
                }
            })
            .build());
    //创造者物品栏
    public static final RegistryObject<CreativeModeTab> CREATOR_ITEMS = TABS.register("creator_items",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.CREATIVE_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.creator_items"))
                    .displayItems((parameters, output) -> {

                    })
                    .build());
    //生物
    public static final RegistryObject<CreativeModeTab> CREATURE = TABS.register("creature",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.ENEMY_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.creature"))
                    .displayItems((parameters, output) -> {

                    })
                    .build());
    //战士武器
    public static final RegistryObject<CreativeModeTab> WARRIOR = TABS.register("warrior",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.MELEE_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.warrior"))
                    .displayItems((parameters, output) -> {

                    })
                    .build());
    //射手武器
    public static final RegistryObject<CreativeModeTab> SHOOTER = TABS.register("shooter",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.REMOTE_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.shooter"))
                    .displayItems((parameters, output) -> {

                    })
                    .build());
    //法师武器
    public static final RegistryObject<CreativeModeTab> MAGE = TABS.register("mage",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.MAGIC_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.mage"))
                    .displayItems((parameters, output) -> {

                    })
                    .build());
    //召唤武器
    public static final RegistryObject<CreativeModeTab> SUMMONER = TABS.register("summoner",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.Icons.SUMMON_ICON.get()))
                    .title(Component.translatable("creativetab.confluence.summoner"))
                    .displayItems((parameters, output) -> {

                    })
                    .build());
}
